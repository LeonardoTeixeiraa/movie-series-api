package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodioRepository extends JpaRepository<Episodio, Long> {

    List<Episodio> findBySerieId(Long serieId);

    List<Episodio> findBySerieIdAndTemporada(Long serieId, Integer temporada);

    boolean existsBySerieIdAndTemporada(Long serieId, Long temporada);
}
