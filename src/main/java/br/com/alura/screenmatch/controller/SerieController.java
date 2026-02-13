package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.EpisodioDTO;
import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @PostMapping("/buscar")
    public ResponseEntity<Serie> buscarSerie(@RequestParam String titulo) {
        Serie serie = service.buscarSerieNaOmdb(titulo);
        return ResponseEntity.ok(serie);
    }

    @GetMapping
    public List<SerieDTO> obterSeries(){
        return service.obterSeries();
    }

    @GetMapping("/buscar-por-titulo")
    public ResponseEntity<Serie> buscarPorTitulo(@RequestParam String titulo){
        return service.buscarPorTitulo(titulo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series(){
        return service.obterTop5series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos(){
        return service.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id){
        return service.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
        return service.obterTodasTemporadas(id);
    }
    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numero){
        return service.obterTemporadasPorNumero(id, numero);
    }

    @GetMapping("/categoria/{genero}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String genero){
        return service.obterSeriesPorCategoria(genero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterTopEpisodios(@PathVariable Long id){
        return service.obterTopEpisodiosPorSerie(id);
    }
}
