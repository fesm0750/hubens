package postech.g105.hubens.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import postech.g105.hubens.model.favorito.Favorito;
import postech.g105.hubens.model.favorito.FavoritoId;

@Repository
public interface FavoritoRepository extends ReactiveMongoRepository<Favorito, FavoritoId>{
    
}
