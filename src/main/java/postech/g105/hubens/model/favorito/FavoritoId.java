package postech.g105.hubens.model.favorito;

import java.io.Serializable;
import java.util.Objects;

// NÃO ALTERAR A ORDEM DOS CAMPOS, ordem diferentes geram ids diferentes
public class FavoritoId implements Serializable{
    
    String videoId;
    String usuarioId;

    public FavoritoId(String videoId, String usuarioId) {
        this.videoId = videoId;
        this.usuarioId = usuarioId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String userId) {
        this.usuarioId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, usuarioId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritoId that = (FavoritoId) o;
        return Objects.equals(videoId, that.videoId) &&
                Objects.equals(usuarioId, that.usuarioId);
    }
}
