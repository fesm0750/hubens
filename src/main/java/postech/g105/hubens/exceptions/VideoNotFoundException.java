package postech.g105.hubens.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VideoNotFoundException extends RuntimeException {
    public VideoNotFoundException() {
        super("Video não encontrado.");
    }
}
