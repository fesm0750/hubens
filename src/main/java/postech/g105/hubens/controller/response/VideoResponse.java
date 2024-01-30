package postech.g105.hubens.controller.response;

import java.time.LocalDate;

import postech.g105.hubens.config.ApplicationConstants;
import postech.g105.hubens.model.Video;
import postech.g105.hubens.model.enums.VideoCategoria;

public record VideoResponse(String id, String titulo, String descricao, LocalDate dataPublicacao,
        VideoCategoria categoria, String url) {

    public VideoResponse(String id, String titulo, String descricao, LocalDate dataPublicacao,
            VideoCategoria categoria) {
        this(id, titulo, descricao, dataPublicacao, categoria, url(id));
    }

    public VideoResponse(Video video) {
        this(video.getId(), video.getTitulo(), video.getDescricao(), video.getDataPublicacao(), video.getCategoria());
    }

    private static String url(String id) {
        return ApplicationConstants.VIDEO_STREAM_URL + id;
    }
}