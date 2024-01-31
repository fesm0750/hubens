package postech.g105.hubens.controller.response;

import java.time.LocalDate;

import postech.g105.hubens.model.video.Video;
import postech.g105.hubens.model.video.VideoCategoria;

public record VideoResponse(String id, String titulo, String descricao, LocalDate dataPublicacao,
        VideoCategoria categoria, String url) {

    public VideoResponse(Video video) {
        this(video.getId(), video.getTitulo(), video.getDescricao(), video.getDataPublicacao(), video.getCategoria(),
                video.url());
    }

}