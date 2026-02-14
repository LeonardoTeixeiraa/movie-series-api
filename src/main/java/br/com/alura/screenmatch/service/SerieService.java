package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.config.OmdbConfig;
import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.OmdbEpisodioDTO;
import br.com.alura.screenmatch.dto.OmdbSerieDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    private final SerieRepository repository;
    private final ConsumoApi consumoApi;
    private final ConverteDados converteDados;
    private final OmdbConfig config;

    public SerieService(SerieRepository repository,
                        ConsumoApi consumoApi,
                        ConverteDados conversor,
                        OmdbConfig config) {
        this.repository = repository;
        this.consumoApi = consumoApi;
        this.converteDados = conversor;
        this.config = config;
    }

    public Serie buscarSerieNaOmdb (String titulo){
        String url = "https://www.omdbapi.com/?t="
                + titulo.replace(" ", "+")
                + "&apikey=" + config.getApikey();

        String json = consumoApi.obterDados(url);
        OmdbSerieDTO serieDTO = converteDados.obterDados(json, OmdbSerieDTO.class);

        Serie serie = new Serie(serieDTO);
        return repository.save(serie);
    }

    public Episodio buscarEpisodioPorTemporadaOmdb (String titulo, int temporada){
        String url = "https://www.omdbapi.com/?t="
                + titulo.replace(" ", "+") +"&Season=" + temporada
                + "&apikey=" + config.getApikey();

        String json = consumoApi.obterDados(url);
        OmdbEpisodioDTO episodioDTO = converteDados.obterDados(json, OmdbEpisodioDTO.class);

        Episodio episodio = new Episodio(temporada, episodioDTO);
        return repository.save(episodio);
    }

    public Optional<Serie> buscarPorTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<SerieDTO> obterSeries(){
        return converteDados(repository.findAll());
    }

    public List<SerieDTO> obterTop5series() {
        return converteDados(repository.findTop5ByOrderByAvaliacaoDesc());

    }

    private List<SerieDTO> converteDados(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getSinopse(), s.getPoster()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repository.encontrarEpisodiosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getSinopse(), s.getPoster());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .toList();
        }
        return null;
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return repository.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .toList();
    }

    public List<SerieDTO> obterSeriesPorCategoria(String genero) {
        Categoria categoria = Categoria.fromPortugues(genero);
        return converteDados(repository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obterTopEpisodiosPorSerie(Long id){
        var serie = repository.findById(id).get();
        return repository.topEpisodiosPorSerie(serie)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .toList();
    }
}
