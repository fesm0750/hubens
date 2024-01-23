package postech.g105.hubens.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import postech.g105.hubens.model.Video;
import reactor.core.publisher.Flux;

@Repository
public interface VideoRepository extends ReactiveMongoRepository<Video, String>{
    @Query("{}")
    Flux<Video> findAllPaged(Pageable pageable);
}
