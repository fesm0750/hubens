package postech.g105.hubens.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import postech.g105.hubens.config.ApplicationConstants;
import postech.g105.hubens.controller.request.VideoRequest;
import postech.g105.hubens.controller.response.VideoResponse;
import postech.g105.hubens.exceptions.video.VideoNotFoundException;
import postech.g105.hubens.model.Video;
import postech.g105.hubens.repository.VideoRepository;
import postech.g105.hubens.service.VideoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/video")
public class VideoController {

    // Constants
    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String ORDER_DESCENDING = "DESC";
    private static final String EMPTY_STRING = "";

    // Injections
    private final VideoRepository videoRepository;
    private final VideoService videoService;

    public VideoController(VideoRepository videoRepository, VideoService videoService) {
        this.videoRepository = videoRepository;
        this.videoService = videoService;
    }

    /**
     * Realiza uma consulta paginada no banco de dados, podendo ter os resultados
     * filtrados por Título e por período de publicação.
     * 
     * @param page        número da página, inicia em zero. Opcional, valor padrãos
     *                    {@value DEFAULT_PAGE}
     * @param size        quantidade de itens por página, deve ser maior que zero.
     *                    Opcional, valor padrão {@value DEFAULT_PAGE_SIZE}
     * @param orderByDate ordenação por data de publicação, pode ser crescente (ASC)
     *                    ou decrescente (DESC). Opcional, valor padrão
     *                    {@value ORDER_DESCENDING}
     * @param titulo      trecho do título do vídeo a ser buscado. Opcional, se não
     *                    fornecido, busca por todos.
     * @param dataInicio  início do período publicação. Opcional.
     * @param dataFim     fim do periodo de publicação. Opcional.
     */
    @GetMapping
    public Flux<VideoResponse> buscar(
            @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(defaultValue = ORDER_DESCENDING) Sort.Direction orderByDate,
            @RequestParam(defaultValue = EMPTY_STRING) String titulo,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim) {

        return videoService.buscar(page, size, orderByDate, titulo, dataInicio, dataFim)
                .map(video -> new VideoResponse(video));
    }

    @GetMapping("/{id}")
    public Mono<Video> buscarPorId(@PathVariable String id) {
        return videoRepository.findById(id);
    }

    @PostMapping
    public Mono<?> cadastrar(
            @RequestPart(value = "body") @Valid VideoRequest req,
            @RequestPart(value = "file", required = true) Mono<FilePart> file) {
        // save video entity to database
        var videoEntry = videoRepository.save(req.toEntity());

        // save video to storage and
        return videoEntry
                .flatMap(video -> file
                        .flatMap(fp -> fp.transferTo(ApplicationConstants.VIDEO_STORAGE_PATH.resolve(video.getId())))
                        .then(Mono.just(new VideoResponse(video))));
    }

    /**
     * Atualiza o registro se o mesmo já se encontra presente no DB, lança exceção
     * caso contrário.
     * 
     * @param id
     * @param req deve conter os campos `Título` e `Descrição`.
     * @return Mono com a entidade video atualizada.
     */
    @PutMapping("/{id}")
    public Mono<Video> atualizar(@PathVariable String id, @RequestBody @Valid VideoRequest req) {
        return videoRepository.findById(id)
                .switchIfEmpty(Mono.error(new VideoNotFoundException()))
                .map(video -> {
                    video.setTitulo(req.titulo());
                    video.setDescricao(req.descricao());
                    return video;
                }).flatMap(videoRepository::save);
    }

    /**
     * Exclui item do armazenamento e do banco de dados. A exclusão no BD ocorre
     * mesmo se o arquivo não existir no armazenamento. No entanto, caso não seja
     * possível excluir o arquivo no armazenamento, uma exceção é lançada e a
     * exclusão no BD não ocorre.
     * 
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> excluir(@PathVariable String id) throws IOException {
        return videoService.excluir(id).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
