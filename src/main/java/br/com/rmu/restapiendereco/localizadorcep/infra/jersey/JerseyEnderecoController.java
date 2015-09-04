package br.com.rmu.restapiendereco.localizadorcep.infra.jersey;

import br.com.rmu.restapiendereco.localizadorcep.application.IEnderecoService;
import br.com.rmu.restapiendereco.localizadorcep.domain.model.Endereco;
import br.com.rmu.restapiendereco.localizadorcep.interfaces.restapi.IEnderecoController;
import br.com.rmu.restapiendereco.shared.infra.exception.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class JerseyEnderecoController implements IEnderecoController {
    @Autowired
    private IEnderecoService enderecoService;

    @GET
    @Override
    public Endereco findByCep(@QueryParam("cep") String pCep) {
        if (pCep == null) {
            throw new WebApplicationException(
                    Response.status(HttpURLConnection.HTTP_BAD_REQUEST)
                            .entity(new Erro("CEP_OBRIGATORIO", "O QueryParam cep é obrigatório."))
                            .build()
            );
        }
        return enderecoService.buscarPorCep(pCep);
    }
}
