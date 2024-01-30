package postech.g105.hubens.controller.request;

import jakarta.validation.constraints.NotBlank;
import postech.g105.hubens.model.Usuario;

public record UsuarioRequest(@NotBlank String username) {
    public Usuario toEntity() {
        return new Usuario(null, username);
    }
}
