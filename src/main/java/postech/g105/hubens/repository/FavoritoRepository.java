package postech.g105.hubens.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import postech.g105.hubens.model.favorito.Favorito;
import postech.g105.hubens.model.favorito.FavoritoId;
import postech.g105.hubens.model.video.VideoCategoria;
import postech.g105.hubens.model.video.VideoCategoriaDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface FavoritoRepository extends ReactiveMongoRepository<Favorito, FavoritoId> {

    // Contagem do total de vídeos favoritados
    @Aggregation({
            "{$group: { _id: '$compositeKey.videoId', count: { $sum: 1 } }}",
            "{$project: { count: 1 }}"
    })
    Mono<Long> countVideoFavoritados();

    // Retorna as categorias favoritas pelo usuário ordenada a partir da mais
    // frequente.
    @Aggregation(pipeline = {
            "{$match: {'id.usuarioId': ?0}}",
            "{$group: {_id: $categoria, total: {$sum: 1}}}",
            "{$project: {categoria: '$_id', total: 1}}",
            "{$sort: {total: -1}}",
            "{$limit: ?1}"
    })
    Flux<VideoCategoriaDTO> findCategoriasFavoritasByUsuario(String usuarioId, Integer limite);

    // Retorna as categorias favoritas pelo usuário em um dado período, ordenada a
    // partir da mais frequente.
    @Aggregation(pipeline = {
            "{$match: {'id.usuarioId': ?0, 'timestamp': {$gte: ?1, $lte: ?2}}}",
            "{$group: {_id: $categoria, total: {$sum: 1}}}",
            "{$project: {categoria: '$_id', total: 1}}",
            "{$sort: {total: -1}}",
            "{$limit: ?3}"
    })
    Flux<VideoCategoriaDTO> findCategoriasFavoritasByUsuarioAndData(String usuarioId, LocalDateTime startTimestamp,
            LocalDateTime endTimestamp, Integer limite);

    // Retorna os ids vídeos mais favoritados das categorias solicitadas.
    @Aggregation(pipeline = {
            "{$match: {'categoria': {$in: ?0}}}",
            "{$group: {_id: '$_id.videoId', total: {$sum: 1}}}",
            "{$project: {_id: 1}}",
            "{$sort: {total: -1}}",
            "{$limit: ?1}"
    })
    Flux<String> findTrendindVideosByCategoria(List<VideoCategoria> categorias, Integer limite);

    // Retorna os ids vídeos mais favoritados das categorias solicitadas dentro do
    // período de tempo.
    @Aggregation(pipeline = {
            "{$match: {'categoria': {$in: ?0}, 'timestamp': {$gte: ?1, $lte: ?2}}}",
            "{$group: {_id: '$_id.videoId', total: {$sum: 1}}}",
            "{$project: {_id: 1}}",
            "{$sort: {total: -1}}",
            "{$limit: ?3}"
    })
    Flux<String> findTrendindVideosByCategoriaAndData(List<VideoCategoria> categorias, LocalDateTime startTimestamp,
            LocalDateTime endTimestamp, Integer limite);

}
