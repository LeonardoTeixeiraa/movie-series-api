package br.com.alura.screenmatch.model;


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
    private Double avaliação;
    private String poster;

}
