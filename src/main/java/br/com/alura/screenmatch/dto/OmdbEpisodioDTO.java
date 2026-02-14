package br.com.alura.screenmatch.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record OmdbEpisodioDTO(@JsonAlias("Title") String titulo,
                              @JsonAlias("Episode") Integer numero,
                              @JsonAlias("imdbRating") String avaliacao,
                              @JsonAlias("Released") String dataLancamento) {
}
