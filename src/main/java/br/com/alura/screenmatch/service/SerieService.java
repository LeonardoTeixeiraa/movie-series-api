package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.config.OmdbConfig;
import br.com.alura.screenmatch.dto.*;
import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.EpisodioRepository;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    private final SerieRepository serieRepository;
    private final EpisodioRepository episodioRepository;
    private final ConsumoApi consumoApi;
    private final ConverteDados converteDados;
    private final OmdbConfig config;

    public SerieService(SerieRepository serieRepository,
                        EpisodioRepository episodioRepository,
                        ConsumoApi consumoApi,
                        ConverteDados conversor,
                        OmdbConfig config) {
        this.serieRepository = serieRepository;
        this.episodioRepository = episodioRepository;
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
        return serieRepository.save(serie);
    }

    public void buscarEpisodiosPorTemporadaOmdb (String titulo, int temporada, Serie serie){
        String url = STR."https://www.omdbapi.com/?t=\{titulo.replace(" ", "+")}&Season=\{temporada}&apikey=\{config.getApikey()}";

        String json = consumoApi.obterDados(url);
        OmdbTemporadaDTO temporadaDTO = converteDados.obterDados(json, OmdbTemporadaDTO.class);

        List<Episodio> episodios = temporadaDTO.episodios()
                .stream()
                .map(e -> {Episodio ep = new Episodio(temporada, e);
                    ep.setSerie(serie);
                return ep;})
                .toList();

        episodioRepository.saveAll(episodios);
    }

    public Optional<Serie> buscarPorTitulo(String titulo) {
        return serieRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<SerieDTO> obterSeries(){
        return converteDados(serieRepository.findAll());
    }

    public List<SerieDTO> obterTop5series() {
        return converteDados(serieRepository.findTop5ByOrderByAvaliacaoDesc());

    }

    private List<SerieDTO> converteDados(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getSinopse(), s.getPoster()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(serieRepository.encontrarEpisodiosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(), s.getGenero(), s.getAtores(), s.getSinopse(), s.getPoster());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long serieId) {
      if(!serieRepository.existsById(serieId)){
          throw new RuntimeException("Série não encontrada");
      }

      return episodioRepository.findBySerieId(serieId)
              .stream()
              .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
              .toList();
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        if (!episodioRepository.existsBySerieIdAndTemporada(id, numero)){
            Serie serie = serieRepository.findById(id).get();
            buscarEpisodiosPorTemporadaOmdb(serie.getTitulo(), numero.intValue(), serie);
        }

        return serieRepository.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .toList();
    }

    public List<SerieDTO> obterSeriesPorCategoria(String genero) {
        Categoria categoria = Categoria.fromPortugues(genero);
        return converteDados(serieRepository.findByGenero(categoria));
    }

    public List<EpisodioDTO> obterTopEpisodiosPorSerie(Long id){
        var serie = serieRepository.findById(id).get();
        return serieRepository.topEpisodiosPorSerie(serie)
                .stream()
                .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .toList();
    }
}
