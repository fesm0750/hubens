package postech.g105.hubens.controller.request;

// import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import postech.g105.hubens.model.Video;

public record VideoRequest(
    @NotBlank String titulo,
    @NotBlank String descricao,
    @NotBlank String url
    ) {

    public Video toEntity() {
        return new Video(null, titulo, descricao, url, null);
    }
    
}
