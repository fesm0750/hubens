package postech.g105.hubens.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import postech.g105.hubens.model.Acesso;

@Repository
public interface AcessoRepository extends ReactiveMongoRepository<Acesso, String> {

}
