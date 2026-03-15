package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilmeRepository extends JpaRepository<Filme, Long > {

    List<Filme> findTop5ByOrderByAvaliacaoDesc();


    Optional<List<Filme>> findByTituloContainingIgnoreCase(String titulo);

    Optional<List<Filme>> findByGeneroIgnoreCase(String genero);
}
