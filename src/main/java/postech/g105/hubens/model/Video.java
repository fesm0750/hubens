package postech.g105.hubens.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "videos")
public class Video {

    @Id
    private String id;

    private String titulo;
    private String descrição;
    private String url;

    @CreatedDate
    private Date dataPublicação;

    public Video() {
    }

    public Video(String id, String titulo, String descrição, String url, Date dataPublicação) {
        this.id = id;
        this.titulo = titulo;
        this.descrição = descrição;
        this.url = url;
        this.dataPublicação = dataPublicação;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDataPublicação() {
        return dataPublicação;
    }

    public void setDataPublicação(Date dataPublicação) {
        this.dataPublicação = dataPublicação;
    }
}
