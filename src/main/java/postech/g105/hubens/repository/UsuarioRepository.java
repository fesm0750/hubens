package postech.g105.hubens.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import postech.g105.hubens.model.Usuario;

@Repository
public interface UsuarioRepository extends ReactiveMongoRepository<Usuario, String>{
    
}
