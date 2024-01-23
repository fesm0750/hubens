package postech.g105.hubens.controller;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import postech.g105.hubens.controller.request.VideoRequest;
import postech.g105.hubens.model.Video;
import postech.g105.hubens.repository.VideoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/video")
public class VideoController {
    private final VideoRepository videoRepository;

    public VideoController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    // Pode receber os parãmetros `page` e `size`, se não enviados, valores padrão
    // são utilizados
    // `page` inicia a contagem em zero
    // `size` deve ser maior ou igual a 1
    @GetMapping
    public Flux<Video> buscarTodos(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return videoRepository.findAllPaged(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Mono<Video> buscarPorId(@PathVariable String id) {
        return videoRepository.findById(id);
    }

    @PostMapping
    public Mono<Video> cadastrar(@RequestBody @Valid VideoRequest req) {
        return videoRepository.save(req.toEntity());
    }

    // Atualiza a entrada se a mesma já se encontra presente no banco de dados.
    // Caso contrário, lança exceção.
    @PutMapping("/{id}")
    public Mono<Video> atualizar(@PathVariable String id, @RequestBody @Valid VideoRequest req) {
        return videoRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .map(fetchedVideo -> {
                    var video = req.toEntity();
                    video.setId(id);
                    video.setDataPublicação(fetchedVideo.getDataPublicação());
                    return video;
                }).flatMap(videoRepository::save);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> excluir(@PathVariable String id) {
        return videoRepository.deleteById(id);
    }
}
