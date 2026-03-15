package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long > {

    List<Filme> findTop5ByAvaliacaoDesc();

    List<Filme> findByTituloContainingIgnoreCase(String titulo);
}
