package br.com.alura.screenmatch.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OmdbFilmeDTO(@JsonAlias("Title") String titulo,
                           @JsonAlias("Actors") String atores,
                           @JsonAlias("Imdb") Double avaliacao,
                           @JsonAlias("Plot") String sinopse,
                           @JsonAlias("Poster") String poster,
                           @JsonAlias("Genre") String genero) {
}

