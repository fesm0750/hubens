package postech.g105.hubens.controller.response;

import java.time.LocalDate;

import postech.g105.hubens.model.Video;
import postech.g105.hubens.model.enums.VideoCategoria;

public record VideoResponse(String id, String titulo, String descricao, LocalDate dataPublicacao,
        VideoCategoria categoria, String url) {

    public VideoResponse(Video video) {
        this(video.getId(), video.getTitulo(), video.getDescricao(), video.getDataPublicacao(), video.getCategoria(),
                video.url());
    }

}