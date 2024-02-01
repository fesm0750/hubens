package postech.g105.hubens.model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class UsuarioTest {

    final String id = "1";
    final String username = "Teste username";

    @Test
    public void criarUsuario_TestaConstrutor() {
        var usuario = new Usuario(id, username);

        assertEquals(id, usuario.getId());
        assertEquals(username, usuario.getUsername());
    }

    @Test
    public void settersUsuario_TestaSetters() {
        var usuario = new Usuario();
        usuario.setId(id);
        usuario.setUsername(username);

        assertEquals(id, usuario.getId());
        assertEquals(username, usuario.getUsername());
    }
    
}
