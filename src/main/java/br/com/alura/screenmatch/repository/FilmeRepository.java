package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long > {
}
