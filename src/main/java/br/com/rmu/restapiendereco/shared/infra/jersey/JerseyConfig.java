package br.com.rmu.restapiendereco.shared.infra.jersey;

import br.com.rmu.restapiendereco.enderecocliente.infra.jersey.JerseyEnderecoDoClienteController;
import br.com.rmu.restapiendereco.localizadorcep.infra.jersey.JerseyEnderecoController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * Classe configuradora do Jersey. Registra os resources.
 */
@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(JerseyEnderecoController.class);
        register(JerseyEnderecoDoClienteController.class);
        register(ParsableExceptionMapper.class);
        register(RegistroNaoEncontradoExceptionMapper.class);
        register(RegraDeNegocioVioladaExceptionMapper.class);
    }
}