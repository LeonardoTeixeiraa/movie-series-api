package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.config.OmdbConfig;
import br.com.alura.screenmatch.dto.FilmeResponseDTO;
import br.com.alura.screenmatch.dto.OmdbFilmeDTO;
import br.com.alura.screenmatch.model.Filme;
import br.com.alura.screenmatch.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

    private final OmdbConfig config;
    private final ConsumoApi consumoApi;
    private final ConverteDados conversorDadosJson;
    private final FilmeRepository repository;

    public FilmeService(OmdbConfig config, ConsumoApi consumoApi, ConverteDados converteDados, FilmeRepository repository) {
        this.config = config;
        this.consumoApi = consumoApi;
        this.conversorDadosJson = converteDados;
        this.repository = repository;
    }

    public Filme importarFilmeOmdb(String titulo){
        String url = STR."https://www.omdbapi.com/?t=\{titulo.replace(" ", "+")}&apikey=\{config.getApikey()}";

        String json = consumoApi.obterDados(url);

        OmdbFilmeDTO filmeDTO = conversorDadosJson.obterDados(json, OmdbFilmeDTO.class);
        Filme filme = new Filme(filmeDTO);

        return repository.save(filme);
    }

    public List<FilmeResponseDTO> converteDados(List<Filme> filmes){
        return repository.findAll().stream()
                .map(f -> new FilmeResponseDTO(f.getId(), f.getTitulo(), f.getAtores(), f.getAvaliacao(), f.getSinopse(), f.getPoster()))
                .toList();
    }

    public List<FilmeResponseDTO> obterTodosOsFilmes (){
        return converteDados(repository.findAll());
    }


}
