package postech.g105.hubens.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import postech.g105.hubens.model.Video;
import postech.g105.hubens.model.enums.VideoCategoria;
import reactor.core.publisher.Flux;

@Repository
public interface VideoRepository extends ReactiveMongoRepository<Video, String> {

    @Query("{ $and: [ "
    + "?#{ [0] != null ? { 'titulo': { $regex: [0], $options: 'i' } } : { $where: 'true' } }, "
    + "?#{ [1] != null ? { 'categoria': { $eq: [1] } } : { $where: 'true' } }, "
    + "?#{ [2] != null ? { 'dataPublicacao': { $gte: [2], $lte: [3] } } : { $where: 'true' } }"
    + "] }")
    Flux<Video> findFilteredAndPaged(String titulo, VideoCategoria categoria, LocalDate dataPublicacaoInicio, LocalDate dataPublicacaoFim, Pageable pageable);

}
