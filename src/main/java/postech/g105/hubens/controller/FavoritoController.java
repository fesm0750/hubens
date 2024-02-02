package postech.g105.hubens.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import postech.g105.hubens.model.Favorito;
import postech.g105.hubens.model.dto.VideoCategoriaDTO;
import postech.g105.hubens.model.id.FavoritoId;
import postech.g105.hubens.repository.FavoritoRepository;
import postech.g105.hubens.repository.VideoRepository;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/favorito")
public class FavoritoController {

    private final FavoritoRepository favoritoRepository;
    private final VideoRepository videoRepository;

    public FavoritoController(FavoritoRepository favoritoRepository, VideoRepository videoRepository) {
        this.favoritoRepository = favoritoRepository;
        this.videoRepository = videoRepository;
    }

    @GetMapping("/{usuarioId}/{videoId}")
    public Mono<Boolean> verificar(@PathVariable String usuarioId, @PathVariable String videoId) {
        FavoritoId id = new FavoritoId(videoId, usuarioId);
        return favoritoRepository.findById(id).hasElement();
    }

    @PostMapping("/{usuarioId}/{videoId}")
    public Mono<ResponseEntity<?>> adicionar(@PathVariable String usuarioId, @PathVariable String videoId) {
        FavoritoId id = new FavoritoId(videoId, usuarioId);
        return favoritoRepository.existsById(id).flatMap(exist -> {
            if (!exist) {
                Mono<VideoCategoriaDTO> categoria = videoRepository.findCategoriaById(videoId);
                return categoria.flatMap(cat -> {
                    Favorito fav = new Favorito(id, LocalDateTime.now(), cat.categoria());
                    return favoritoRepository.save(fav);
                });
            }
            return Mono.empty();
        }).then(Mono.just(ResponseEntity.ok().build()));
    }

    @DeleteMapping("/{usuarioId}/{videoId}")
    public Mono<ResponseEntity<?>> excluir(@PathVariable String usuarioId, @PathVariable String videoId) {
        return favoritoRepository.deleteById(new FavoritoId(videoId, usuarioId))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
