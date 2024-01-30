package postech.g105.hubens.exceptions.video;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class VideoDataBufferException extends RuntimeException{
    public VideoDataBufferException() {
        super("Não foi possível reproduzir o vídeo.");
    }
}
