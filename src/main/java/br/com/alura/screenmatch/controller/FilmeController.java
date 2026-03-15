package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.FilmeResponseDTO;
import br.com.alura.screenmatch.model.Filme;
import br.com.alura.screenmatch.service.FilmeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeService service;

    public FilmeController(FilmeService service) {
        this.service = service;
    }

    // Importar filme da OMDb
    @PostMapping("/importar")
    public ResponseEntity<Filme> importarFilme(@RequestParam String titulo) {
        Filme filme = service.importarFilmeOmdb(titulo);
        return ResponseEntity.status(HttpStatus.CREATED).body(filme);
    }

    // Listar todos os filmes
    @GetMapping
    public ResponseEntity<List<FilmeResponseDTO>> listarFilmes() {
        return ResponseEntity.ok(service.obterTodosOsFilmes());
    }

    // Top 5 filmes por avaliação
    @GetMapping("/top5")
    public ResponseEntity<List<FilmeResponseDTO>> top5Filmes() {
        return ResponseEntity.ok(service.obterTop5filmes());
    }

    // Buscar filme por id
    @GetMapping("/{id}")
    public ResponseEntity<FilmeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findFilmeById(id));
    }

    // Buscar filmes por título (parte do título)
    @GetMapping("/buscar")
    public ResponseEntity<List<FilmeResponseDTO>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(service.findFilmeByTituloContains(titulo));
    }

    // Buscar filmes por gênero
    @GetMapping("/genero")
    public ResponseEntity<List<FilmeResponseDTO>> buscarPorGenero(@RequestParam String genero) {
        return ResponseEntity.ok(service.findByGenero(genero));
    }
}
