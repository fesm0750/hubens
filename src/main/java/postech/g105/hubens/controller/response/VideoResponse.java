package postech.g105.hubens.controller.response;

import java.time.LocalDate;

import postech.g105.hubens.config.ApplicationConstants;
import postech.g105.hubens.model.Video;

public record VideoResponse(String id, String titulo, String descricao, LocalDate dataPublicacao, String url) {
    
    public VideoResponse(String id, String titulo, String descricao, LocalDate dataPublicacao) {
        this(id, titulo, descricao, dataPublicacao, url(id));
    }

    public VideoResponse(Video video) {
        this(video.getId(), video.getTitulo(), video.getDescricao(), video.getDataPublicacao());
    }

    private static String url(String id) {
        return ApplicationConstants.BASE_URL + ApplicationConstants.VIDEO_STORAGE_STRING + id;
    }
}