package postech.g105.hubens.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import postech.g105.hubens.model.enums.VideoCategoria;

public class VideoTest {

    final String id = "1";
    final String titulo = "Video teste";
    final String descricao = "descrição teste";
    final LocalDate dataPublicacao = LocalDate.of(2024, 1, 27);
    final VideoCategoria categoria = VideoCategoria.COMO_FAZER;

    @Test
    public void criarVideo_TestaConstrutor() {
        var video = new Video(id, titulo, descricao, dataPublicacao, categoria);

        assertEquals(id, video.getId());
        assertEquals(dataPublicacao, video.getDataPublicacao());
        assertEquals(titulo, video.getTitulo());
        assertEquals(descricao, video.getDescricao());
        assertEquals(categoria, video.getCategoria());
    }

    @Test
    public void settersVideo_TestaSetters() {
        var video = new Video();
        video.setId(id);
        video.setTitulo(titulo);
        video.setDescricao(descricao);
        video.setDataPublicacao(dataPublicacao);
        video.setCategoria(categoria);

        assertEquals(id, video.getId());
        assertEquals(dataPublicacao, video.getDataPublicacao());
        assertEquals(titulo, video.getTitulo());
        assertEquals(descricao, video.getDescricao());
        assertEquals(categoria, video.getCategoria());
    }

    @Test
    public void urlVideo() {
        final String url = "http://localhost:8080/video_stream/" + id;

        var video = new Video(id, null, null, null, null);

        assertEquals(url, video.url());
    }
}
