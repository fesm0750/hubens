package postech.g105.hubens.model.video;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import postech.g105.hubens.config.ApplicationConstants;

@Document(collection = "videos")
public class Video {

    @Id
    private String id;

    @CreatedDate
    private LocalDate dataPublicacao;

    private String titulo;
    private String descricao;
    private VideoCategoria categoria;

    // Constants, schema, field names
    @Transient
    public static final String CAMPO_DATA_PUBLICACAO = "dataPublicacao";

    public Video() {
    }

    public Video(String id, String titulo, String descricao, LocalDate dataPublicacao, VideoCategoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataPublicacao = dataPublicacao;
        this.categoria = categoria;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public VideoCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(VideoCategoria categoria) {
        this.categoria = categoria;
    }

    public String url() {
        return ApplicationConstants.VIDEO_STREAM_URL + this.getId();
    }

}
