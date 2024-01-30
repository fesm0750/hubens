package postech.g105.hubens.model.video;

public enum VideoCategoria {

    ACAO("Ação"),
    AVENTURA("Aventura"),
    ANIMACAO("Animação"),
    COMEDIA("Comédia"),
    COMO_FAZER("Como Fazer"),
    CRIME("Crime"),
    CULINARIA("Culinária"),
    DOCUMENTARIO("Documentário"),
    DRAMA("Drama"),
    EDUCACAO("Educação"),
    ESPORTES("Esportes"),
    FAMILIA("Família"),
    FANTASIA("Fantasia"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    FITNESS("Fitness"),
    JOGOS("Jogos"),
    MUSICA("Música"),
    NATUREZA("Natureza"),
    NOTICIAS("Notícias"),
    ROMANCE("Romance"),
    SUSPENSE("Suspense"),
    TECNOLOGIA("Tecnologia"),
    TERROR("Terror"),
    VIAGENS("Viagens"),
    VLOG("Vlog");

    private final String categoria;

    VideoCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }
}
