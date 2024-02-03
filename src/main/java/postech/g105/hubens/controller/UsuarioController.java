package postech.g105.hubens.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import postech.g105.hubens.controller.request.UsuarioRequest;
import postech.g105.hubens.exceptions.usuario.UsuarioNotFoundException;
import postech.g105.hubens.model.Usuario;
import postech.g105.hubens.repository.UsuarioRepository;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/{id}")
    public Mono<Usuario> buscarPorId(@PathVariable @NonNull String id) {
        return usuarioRepository.findById(id).switchIfEmpty(Mono.error(new UsuarioNotFoundException()));
    }

    @PostMapping
    public Mono<?> cadastrar(@RequestBody UsuarioRequest req) {
        return usuarioRepository.save(req.toEntity());
    }

    @PutMapping("/{id}")
    public Mono<Usuario> atualizar(@PathVariable String id, @RequestBody UsuarioRequest req) {
        return usuarioRepository.findById(id)
                .switchIfEmpty(Mono.error(new UsuarioNotFoundException()))
                .map(usuario -> {
                    usuario.setUsername(req.username());
                    return usuario;
                }).flatMap(usuarioRepository::save);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<?>> excluir(@PathVariable String id) {
        return usuarioRepository.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }

}
