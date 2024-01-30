package postech.g105.hubens.exceptions.video;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class VideoErroAoExcluirException extends RuntimeException{
    public VideoErroAoExcluirException() {
        super("Video não pode ser excluído no momento. Favor tente mais tarde ou contate o suporte.");
    }
}
