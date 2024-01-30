package postech.g105.hubens.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "acessos")
public class Acesso {
    
    @Id
    String id;

    String video_id;

    @Field(targetType = FieldType.DATE_TIME)
    LocalDateTime timestamp;
    
    public Acesso() {
    }

    public Acesso(String id, String video_id, LocalDateTime timestamp) {
        this.id = id;
        this.video_id = video_id;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    
    
}
