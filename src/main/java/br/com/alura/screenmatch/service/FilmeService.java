package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.config.OmdbConfig;
import br.com.alura.screenmatch.dto.OmdbFilmeDTO;
import br.com.alura.screenmatch.model.Filme;
import br.com.alura.screenmatch.repository.FilmeRepository;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {

    private OmdbConfig config;
    private ConsumoApi consumoApi;
    private ConverteDados converteDados;
    private FilmeRepository repository;

    public FilmeService(OmdbConfig config, ConsumoApi consumoApi, ConverteDados converteDados, FilmeRepository repository) {
        this.config = config;
        this.consumoApi = consumoApi;
        this.converteDados = converteDados;
        this.repository = repository;
    }

    public Filme importarFilmeOmdb(String titulo){
        String url = STR."https://www.omdbapi.com/?t=\{titulo.replace(" ", "+")}&apikey=\{config.getApikey()}";

        String json = consumoApi.obterDados(url);

        OmdbFilmeDTO filmeDTO = converteDados.obterDados(json, OmdbFilmeDTO.class);
        Filme filme = new Filme(filmeDTO);

        return repository.save(filme);
    }



}
