package postech.g105.hubens.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import jakarta.validation.constraints.NotNull;
import postech.g105.hubens.model.enums.VideoCategoria;
import postech.g105.hubens.model.id.FavoritoId;

@Document(collection = "favoritos")
public class Favorito {

    @Id
    FavoritoId id;

    @Indexed
    @NotNull
    @Field(targetType = FieldType.DATE_TIME)
    LocalDateTime timestamp;

    @Indexed
    @NotNull
    VideoCategoria categoria;

    public Favorito() {
    }

    public Favorito(FavoritoId id, LocalDateTime timestamp, VideoCategoria categoria) {
        this.id = id;
        this.timestamp = timestamp;
        this.categoria = categoria;
    }

    public FavoritoId getId() {
        return id;
    }

    public void setId(FavoritoId id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public VideoCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(VideoCategoria categoria) {
        this.categoria = categoria;
    }

}
