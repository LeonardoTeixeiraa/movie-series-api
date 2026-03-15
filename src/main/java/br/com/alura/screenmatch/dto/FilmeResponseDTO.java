package br.com.alura.screenmatch.dto;

public record FilmeResponseDTO(
        Long id,
        String titulo,
        String atores,
        Double avaliacao,
        String sinopse,
        String poster
) {
}
