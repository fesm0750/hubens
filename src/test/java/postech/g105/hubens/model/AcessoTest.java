package postech.g105.hubens.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class AcessoTest {

    final String id = "1";
    final String videoId = "vd1";
    LocalDateTime timestamp = LocalDateTime.of(2024, 1, 27, 0, 0, 0);

    @Test
    public void criaAcesso_TestaConstrutor() {
        var acesso = new Acesso(id, videoId, timestamp);

        assertEquals(id, acesso.getId());
        assertEquals(videoId, acesso.getVideo_id());
        assertEquals(timestamp, acesso.getTimestamp());
    }

    @Test
    public void settersAcesso_TestaSetters() {
        var acesso = new Acesso();
        acesso.setId(id);
        acesso.setVideo_id(videoId);
        acesso.setTimestamp(timestamp);

        assertEquals(id, acesso.getId());
        assertEquals(videoId, acesso.getVideo_id());
        assertEquals(timestamp, acesso.getTimestamp());
    }
}
