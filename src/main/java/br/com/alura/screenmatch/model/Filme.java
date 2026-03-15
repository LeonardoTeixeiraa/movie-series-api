package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.dto.OmdbFilmeDTO;
import br.com.alura.screenmatch.service.ConsultaGemini;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "filme")
@Getter
@NoArgsConstructor
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String sinopse;
    private String atores;
    private Double avaliacao;
    private String poster;
    private String genero;

    public Filme(OmdbFilmeDTO filmeDTO) {
        this.atores = filmeDTO.atores();
        this.avaliacao = filmeDTO.avaliacao();
        this.poster = filmeDTO.poster();;
        this.sinopse = ConsultaGemini.obterTraducao(filmeDTO.sinopse());
        this.titulo = filmeDTO.titulo();
        this.genero = filmeDTO.genero();
    }
}
