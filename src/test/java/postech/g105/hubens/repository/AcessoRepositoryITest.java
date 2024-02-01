package postech.g105.hubens.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import postech.g105.hubens.model.Acesso;
import reactor.test.StepVerifier;

@Testcontainers
@DataMongoTest
public class AcessoRepositoryITest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"))
            .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    AcessoRepository acessoRepository;

    @Test
    public void devePermitirContarRegistrosPorData() {
        LocalDateTime inicio = LocalDateTime.of(2024, 1, 27, 0, 0, 0);
        LocalDateTime fim = LocalDateTime.of(2024, 1, 28, 0, 0, 0);
        List<Acesso> acessos = List.of(
                new Acesso(null, "vd1", inicio), // inválido, intervalo de início aberto
                new Acesso(null, "vd1", inicio.plusSeconds(1)), // válido
                new Acesso(null, "vd2", fim.minusNanos(1)), // válido
                new Acesso(null, "vd2", fim) // inválido, intervalo de final aberto
        );
        
        var countAcessos = acessoRepository.saveAll(acessos).then(acessoRepository.countByTimestampBetween(inicio, fim));

        final Long REGISTROS_VALIDOS = 2L;
        StepVerifier.create(countAcessos).expectNext(REGISTROS_VALIDOS).verifyComplete();
    }
}
