package postech.g105.hubens.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import postech.g105.hubens.model.video.Video;
import postech.g105.hubens.model.video.VideoCategoria;
import reactor.test.StepVerifier;

@Testcontainers
@DataMongoTest
@Execution(ExecutionMode.SAME_THREAD)
public class VideoRepositoryITest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"))
            .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    VideoRepository videoRepository;

    ArrayList<Video> videos = new ArrayList<Video>();

    @BeforeEach
    public void setup() {
        videos.addAll(List.of(
                new Video(null, "video 1 comédia", "desc 1", null, VideoCategoria.COMEDIA),
                new Video(null, "video 2", "desc 2", null, VideoCategoria.ACAO),
                new Video(null, "video 3", "desc 2", null, VideoCategoria.COMO_FAZER),
                new Video(null, "video 4 teste", "desc 2", null, VideoCategoria.ACAO),
                new Video(null, "replay 1", "desc 2", null, VideoCategoria.ACAO),
                new Video(null, "replay 2", "desc 2", null, VideoCategoria.ACAO)));

        videoRepository.saveAll(videos).blockLast();
    }

    @AfterEach
    public void tearDown() {
        videos.clear();
        videoRepository.deleteAll().block();
    }

    @Test
    public void findFilteredAndPaged_RetornarTodosPaginado() {
        final Long PAGE_SIZE = 3L;

        StepVerifier.create(videoRepository.count()).expectNext(6L).verifyComplete();

        var pageable0 = PageRequest.of(0, 3);
        var page0 = videoRepository.findFilteredAndPaged(null, null, null, null, pageable0);
        StepVerifier.create(page0).expectNextCount(PAGE_SIZE).verifyComplete();

        var pageable1 = pageable0.next();
        var page1 = videoRepository.findFilteredAndPaged(null, null, null, null, pageable1);
        StepVerifier.create(page1).expectNextCount(PAGE_SIZE).verifyComplete();

        var pageable2 = pageable1.next();
        var page2 = videoRepository.findFilteredAndPaged(null, null, null, null, pageable2);
        StepVerifier.create(page2).verifyComplete();
    }

    @Test
    public void findFilteredAndPaged_RetornarPorCategoria() {
        assertEquals(6L, videos.size());
        var categoria = videoRepository.findFilteredAndPaged(null, VideoCategoria.COMEDIA, null, null, null);
        StepVerifier.create(categoria)
                .expectNextMatches(registro -> registro.getCategoria() == videos.get(0).getCategoria())
                .verifyComplete();
    }

    @Test
    public void findFilteredAndPaged_RetornarPorTitulo() {
        assertEquals(6L, videos.size());

        var titulo = videoRepository.findFilteredAndPaged("video", null, null, null, null);
        StepVerifier.create(titulo)
                .expectNextCount(4L)
                .verifyComplete();

        var titulo2 = videoRepository.findFilteredAndPaged("video 4 teste", null, null, null, null);
        StepVerifier.create(titulo2)
                .expectNextMatches(registro -> registro.getTitulo().equals(videos.get(3).getTitulo()))
                .verifyComplete();
    }

    @Test
    public void findFilteredAndPaged_RetornaPorData() {
        assertEquals(6L, videos.size());

        var data = videoRepository.findFilteredAndPaged(null, null, LocalDate.now(), LocalDate.now().plusDays(1), null);

        StepVerifier.create(data)
                .expectNextMatches(registro -> registro.getDataPublicacao().equals(videos.get(1).getDataPublicacao()))
                .expectNextCount(5L)
                .verifyComplete();
    }

    @Test
    public void findCategoriaById_RetornaCategoria() {
        var categoria = videoRepository.findAll()
                .skip(2)
                .take(1)
                .map(v -> v.getId())
                .flatMap(id -> videoRepository.findCategoriaById(id));

        StepVerifier.create(categoria)
                .expectNextMatches(cat -> cat.categoria() == videos.get(2).getCategoria())
                .verifyComplete();
    }

    @Test
    public void retornaListaVideosPorId() {
        var ids = videoRepository.findAll().skip(2).take(3).map(v -> v.getId()).collectList().block();

        var videos = videoRepository.findAllByIdIn(ids);

        StepVerifier.create(videos)
                .expectNextMatches(v -> ids.contains(v.getId()))
                .expectNextCount(2)
                .verifyComplete();
    }

}
