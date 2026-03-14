package br.com.alura.screenmatch.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public record OmdbFilmeDTO(@JsonAlias("Title") String titulo,
                           @JsonAlias("Actors") String atores,
                           @JsonAlias("Rated") Double avaliacao,
                           @JsonAlias("Plot") String sinopse,
                           @JsonAlias("Poster") String poster) {
}
