package postech.g105.hubens.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import postech.g105.hubens.model.Video;
import postech.g105.hubens.model.dto.VideoCategoriaDTO;
import postech.g105.hubens.model.enums.VideoCategoria;
import postech.g105.hubens.repository.FavoritoRepository;
import postech.g105.hubens.repository.VideoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RecommenderService {

    private FavoritoRepository favoritoRepository;
    private VideoRepository videoRepository;

    public RecommenderService(FavoritoRepository favoritoRepository, VideoRepository videoRepository) {
        this.favoritoRepository = favoritoRepository;
        this.videoRepository = videoRepository;
    }

    public Flux<Video> recomendar(String usuarioId) {
        // obter as 5 categorias dos videos mais favoritados do usuario nos últimos 30
        // dias
        final Integer limiteCategorias = 5;
        final Integer limiteRecomendacoes = 15;
        Flux<VideoCategoriaDTO> categorias = favoritoRepository.findCategoriasFavoritasByUsuarioAndData(usuarioId,
                LocalDateTime.now().minusDays(30), LocalDateTime.now(), limiteCategorias);

        // verifica o histórico de favoritos se resulado dos últimos 30 dias é vazio
        categorias = categorias
                .switchIfEmpty(favoritoRepository.findCategoriasFavoritasByUsuario(usuarioId, limiteCategorias));

        // obtém ids de vídeos recomendados
        Mono<List<VideoCategoria>> monoListCategoria = categorias.map(cat -> cat.categoria()).collectList();
        Flux<String> videosIds = monoListCategoria.flatMapMany(list -> favoritoRepository.findTrendindVideosByCategoriaAndData(list, LocalDateTime.now().minusHours(1), LocalDateTime.now(), limiteRecomendacoes)
                    .switchIfEmpty(favoritoRepository.findTrendindVideosByCategoria(list, limiteRecomendacoes)));

        return videosIds.collectList().flatMapMany(ids -> videoRepository.findAllByIdIn(ids));
    }
}
