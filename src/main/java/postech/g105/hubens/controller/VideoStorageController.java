package postech.g105.hubens.controller;

import java.nio.file.Path;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import postech.g105.hubens.config.ApplicationConstants;
import postech.g105.hubens.exceptions.video.VideoDataBufferException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController

public class VideoStorageController {
    @GetMapping(value = "/video_storage/{id}")
    public Flux<DataBuffer> fetch(@PathVariable String id) {
        Path path = ApplicationConstants.VIDEO_STORAGE_PATH.resolve(id);
        return DataBufferUtils.read(path, new DefaultDataBufferFactory(), 4096)
                .onErrorResume(__ -> Mono.error(new VideoDataBufferException()));
    }
}
