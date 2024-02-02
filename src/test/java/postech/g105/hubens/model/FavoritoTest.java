package postech.g105.hubens.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import postech.g105.hubens.model.enums.VideoCategoria;
import postech.g105.hubens.model.id.FavoritoId;

public class FavoritoTest {
    
    final String videoId = "vd1";
    final String usuarioId = "user1";
    FavoritoId id = new FavoritoId(videoId, usuarioId);
    LocalDateTime timestamp = LocalDateTime.of(2024, 1, 27, 0, 0, 0);
    VideoCategoria categoria = VideoCategoria.ANIMACAO;

    @Test
    public void criaFavorito_TestaConstrutor() {
        var id = new FavoritoId(videoId, usuarioId);
        var favorito = new Favorito(id, timestamp, categoria);

        assertEquals(videoId, favorito.getId().getVideoId());
        assertEquals(usuarioId, favorito.getId().getUsuarioId());
        assertEquals(id, favorito.getId());
        assertEquals(timestamp, favorito.getTimestamp());
        assertEquals(categoria, favorito.getCategoria());
    }

    @Test
    public void settersFavorito_TestaSetters() {
        var favorito = new Favorito();
        var id = new FavoritoId("vd0", "user0");
        id.setVideoId(videoId);
        id.setUsuarioId(usuarioId);
        favorito.setId(id);
        favorito.setTimestamp(timestamp);
        favorito.setCategoria(categoria);

        assertEquals(videoId, favorito.getId().getVideoId());
        assertEquals(usuarioId, favorito.getId().getUsuarioId());
        assertEquals(id, favorito.getId());
        assertEquals(timestamp, favorito.getTimestamp());
        assertEquals(categoria, favorito.getCategoria());
    }
}
