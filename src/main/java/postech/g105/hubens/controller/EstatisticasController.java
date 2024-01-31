package postech.g105.hubens.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import postech.g105.hubens.repository.AcessoRepository;
import postech.g105.hubens.repository.FavoritoRepository;
import postech.g105.hubens.repository.VideoRepository;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    private final VideoRepository videoRepository;
    private final FavoritoRepository favoritoRepository;
    private final AcessoRepository acessoRepository;

    public EstatisticasController(VideoRepository videoRepository, FavoritoRepository favoritoRepository,
            AcessoRepository acessoRepository) {
        this.videoRepository = videoRepository;
        this.favoritoRepository = favoritoRepository;
        this.acessoRepository = acessoRepository;
    }

    @GetMapping("/total_videos")
    public Mono<Long> totalVideos() {
        return videoRepository.count();
    }

    @GetMapping("/total_favoritados")
    public Mono<Long> totalFavoritados() {
        return favoritoRepository.countVideoFavoritados();
    }

    @GetMapping("/acesso_hora")
    public Mono<Long> totalVisualizacoesHora() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before = LocalDateTime.now().minusHours(1);
        return acessoRepository.countByTimestampBetween(before, now);
    }

}
