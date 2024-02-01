package postech.g105.hubens.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import postech.g105.hubens.model.favorito.Favorito;
import postech.g105.hubens.model.favorito.FavoritoId;
import postech.g105.hubens.model.video.VideoCategoria;
import reactor.test.StepVerifier;

@Testcontainers
@DataMongoTest
@Execution(ExecutionMode.SAME_THREAD)
public class FavoritoRepositoryITest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"))
            .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    FavoritoRepository favoritoRepository;

    ArrayList<Favorito> favoritos = new ArrayList<Favorito>();

    @BeforeEach
    public void setup() {
        favoritos.addAll(List.of(
                new Favorito(new FavoritoId("video1", "user1"), LocalDateTime.of(2024, 1, 20, 0, 0, 0),
                        VideoCategoria.COMEDIA),
                new Favorito(new FavoritoId("video2", "user2"), LocalDateTime.of(2024, 1, 20, 0, 0, 0),
                        VideoCategoria.ACAO),
                new Favorito(new FavoritoId("video3", "user1"), LocalDateTime.of(2024, 1, 20, 0, 0, 0),
                        VideoCategoria.COMO_FAZER),
                new Favorito(new FavoritoId("video4", "user2"), LocalDateTime.of(2024, 1, 20, 0, 0, 0),
                        VideoCategoria.ACAO),
                new Favorito(new FavoritoId("video2", "user3"), LocalDateTime.of(2024, 1, 20, 0, 0, 0),
                        VideoCategoria.ACAO),
                new Favorito(new FavoritoId("video2", "user1"), LocalDateTime.of(2024, 1, 25, 0, 0, 0),
                        VideoCategoria.ACAO),
                new Favorito(new FavoritoId("video4", "user1"), LocalDateTime.of(2024, 1, 25, 0, 0, 0),
                        VideoCategoria.ACAO),
                new Favorito(new FavoritoId("video5", "user1"), LocalDateTime.of(2024, 1, 21, 0, 0, 0),
                        VideoCategoria.COMO_FAZER),
                new Favorito(new FavoritoId("video5", "user2"), LocalDateTime.of(2024, 1, 22, 0, 0, 0),
                        VideoCategoria.ACAO)));

        favoritoRepository.saveAll(favoritos).blockLast();
    }

    @AfterEach
    public void tearDown() {
        favoritos.clear();
        favoritoRepository.deleteAll().block();
    }

    @Test
    public void testCountVideoFavoritados() {
        long size = (long) favoritos.size();
        StepVerifier.create(favoritoRepository.count()).expectNext(size).verifyComplete();
    }

    @Test
    public void testFindCategoriasFavoritasByUsuario() {
        var categorias = favoritoRepository.findCategoriasFavoritasByUsuario("user1", 2)
                .map(dto -> dto.categoria());

        StepVerifier.create(categorias)
                .expectNext(VideoCategoria.ACAO)
                .expectNext(VideoCategoria.COMO_FAZER)
                .verifyComplete();
    }

    @Test
    public void testFindCategoriasFavoritasByUsuarioAndData() {
        var categorias = favoritoRepository
                .findCategoriasFavoritasByUsuarioAndData("user1", LocalDateTime.of(2024, 1, 20, 0, 0, 0),
                        LocalDateTime.of(2024, 1, 22, 0, 0, 0), 2)
                .map(dto -> dto.categoria());

        StepVerifier.create(categorias)
                .expectNext(VideoCategoria.COMO_FAZER)
                .expectNext(VideoCategoria.COMEDIA)
                .verifyComplete();
    }

    @Test
    public void testFindTrendindVideosByCategoria() {
        var videoIds = favoritoRepository
                .findTrendindVideosByCategoria(List.of(VideoCategoria.ACAO), 1);

        StepVerifier.create(videoIds)
                .expectNext("video2")
                .verifyComplete();
    }

    @Test
    public void testFindTrendindVideosByCategoriaAndData() {
        var videoIds = favoritoRepository
                .findTrendindVideosByCategoriaAndData(List.of(VideoCategoria.ACAO), LocalDateTime.of(2024, 1, 20, 0, 0, 0), LocalDateTime.of(2024, 1, 21, 0, 0, 0), 1);

        StepVerifier.create(videoIds)
                .expectNext("video4")
                .verifyComplete();
    }
}
