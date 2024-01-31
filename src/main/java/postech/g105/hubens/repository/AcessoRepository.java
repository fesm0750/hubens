package postech.g105.hubens.repository;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import postech.g105.hubens.model.Acesso;
import reactor.core.publisher.Mono;

@Repository
public interface AcessoRepository extends ReactiveMongoRepository<Acesso, String> {

    // contagem do total de acessos dentro de um período
    Mono<Long> countByTimestampBetween(LocalDateTime inicio, LocalDateTime fim);
    
}
