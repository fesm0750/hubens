package postech.g105.hubens.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import postech.g105.hubens.model.video.Video;
import postech.g105.hubens.model.video.VideoCategoria;

public record VideoRequest(
        @NotBlank String titulo,
        @NotBlank String descricao,
        @NotNull VideoCategoria categoria) {

    public Video toEntity() {
        return new Video(null, titulo, descricao, null, categoria);
    }

}
