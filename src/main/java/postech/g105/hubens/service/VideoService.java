package postech.g105.hubens.service;

import java.nio.file.Files;
import java.time.LocalDate;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import postech.g105.hubens.config.ApplicationConstants;
import postech.g105.hubens.exceptions.video.VideoErroAoExcluirException;
import postech.g105.hubens.model.Video;
import postech.g105.hubens.model.enums.VideoCategoria;
import postech.g105.hubens.repository.VideoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Flux<Video> buscar(Integer page, Integer size, @NonNull Sort.Direction orderByDate, String titulo,
            VideoCategoria categoria, LocalDate dataInicio, LocalDate dataFim) {
        if (titulo.isEmpty() && dataInicio == null && categoria == null) {
            return videoRepository.findAll().skip(page * size).take(size);
        }

        // data range looks to be open ended in query, need a plus 1
        if (dataInicio != null) {
            dataFim = dataFim == null ? dataInicio.plusDays(1) : dataFim.plusDays(1);
        }

        Pageable pageable = PageRequest.of(page, size, orderByDate, Video.CAMPO_DATA_PUBLICACAO);
        return videoRepository.findFilteredAndPaged(titulo, categoria, dataInicio, dataFim, pageable);
    }

    public Mono<Void> excluir(@NonNull String id) {
        return Mono.fromCallable(() -> Files.deleteIfExists(ApplicationConstants.VIDEO_STORAGE_PATH.resolve(id)))
                .onErrorResume(__ -> Mono.error(new VideoErroAoExcluirException()))
                .flatMap(__ -> videoRepository.deleteById(id));
    }
}
