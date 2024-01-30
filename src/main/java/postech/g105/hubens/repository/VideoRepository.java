package postech.g105.hubens.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import postech.g105.hubens.model.Video;
import reactor.core.publisher.Flux;

@Repository
public interface VideoRepository extends ReactiveMongoRepository<Video, String> {

    Flux<Video> findByTituloContains(String titulo, Pageable pageable);

    Flux<Video> findByTituloContainsAndDataPublicacaoBetween(String titulo, LocalDate dataInicio, LocalDate dataFim,
            Pageable pageable);

}
