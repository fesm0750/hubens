package postech.g105.hubens.controller;

import java.nio.file.Path;
import java.time.LocalDateTime;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import postech.g105.hubens.config.ApplicationConstants;
import postech.g105.hubens.exceptions.video.VideoDataBufferException;
import postech.g105.hubens.model.Acesso;
import postech.g105.hubens.repository.AcessoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VideoStreamController {

    private final AcessoRepository acessoRepository;

    public VideoStreamController(AcessoRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }

    @GetMapping(value = "/video_stream/{id}")
    public Flux<DataBuffer> fetch(@PathVariable String id) {
        Path path = ApplicationConstants.VIDEO_STORAGE_PATH.resolve(id);
        return DataBufferUtils.read(path, new DefaultDataBufferFactory(), 4096)
                .onErrorResume(__ -> Mono.error(new VideoDataBufferException()))
                .doFirst(() -> acessoRepository.save(new Acesso(null, id, LocalDateTime.now())).subscribe());
    }
}
