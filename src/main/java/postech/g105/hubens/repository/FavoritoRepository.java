package postech.g105.hubens.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import postech.g105.hubens.model.favorito.Favorito;
import postech.g105.hubens.model.favorito.FavoritoId;
import reactor.core.publisher.Mono;

@Repository
public interface FavoritoRepository extends ReactiveMongoRepository<Favorito, FavoritoId> {

    // Contagem do total de vídeos favoritados
    @Aggregation({
            "{$group: { _id: '$compositeKey.videoId', count: { $sum: 1 } }}",
            "{$project: { count: 1 }}"
    })
    Mono<Long> countVideoFavoritados();
    
}
