package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.dto.OmdbSerieDTO;
import br.com.alura.screenmatch.service.ConsultaGemini;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name="series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atores;
    private String poster;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String sinopse;

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Episodio> episodios = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public Serie(OmdbSerieDTO omdbSerieDTO){
        this.titulo = omdbSerieDTO.titulo();
        this.totalTemporadas = omdbSerieDTO.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(omdbSerieDTO.avaliacao())).orElse(0);
        this.genero = Categoria.fromString(omdbSerieDTO.genero().split(",")[0].trim());
        this.atores = omdbSerieDTO.atores();
        this.poster = omdbSerieDTO.poster();
        this.sinopse = ConsultaGemini.obterTraducao(omdbSerieDTO.sinopse().trim()) ;
    }

    public Serie() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return
                "genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", atores='" + atores + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\'';
    }
}
