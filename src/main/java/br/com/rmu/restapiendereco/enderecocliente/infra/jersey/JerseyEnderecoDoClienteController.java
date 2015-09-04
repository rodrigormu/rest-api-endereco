package br.com.rmu.restapiendereco.enderecocliente.infra.jersey;

import br.com.rmu.restapiendereco.enderecocliente.application.IEnderecoDoClienteService;
import br.com.rmu.restapiendereco.enderecocliente.domain.model.EnderecoDoCliente;
import br.com.rmu.restapiendereco.enderecocliente.domain.repository.IEnderecosDoClienteRepository;
import br.com.rmu.restapiendereco.enderecocliente.interfaces.restapi.IEnderecoDoClienteController;
import br.com.rmu.restapiendereco.shared.infra.exception.RegistroNaoEncontradoException;
import br.com.rmu.restapiendereco.shared.infra.exception.RegraDeNegocioVioladaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Restfull controller do Endereço de cliente implementado usando o Jersey.
 */
@Path("/enderecoscliente")
@Produces(MediaType.APPLICATION_JSON)
@Component
public class JerseyEnderecoDoClienteController implements IEnderecoDoClienteController {
    @Autowired
    private IEnderecosDoClienteRepository enderecosDoClienteRepository;
    @Autowired
    private IEnderecoDoClienteService enderecoDoClienteService;

    @POST
    @Override
    public EnderecoDoCliente incluirEnderecoDoCliente(EnderecoDoCliente pEnderecoDoCliente) throws RegraDeNegocioVioladaException {
        return enderecoDoClienteService.incluirEndereco(pEnderecoDoCliente);
    }

    @PUT
    @Path("{id}")
    @Override
    public EnderecoDoCliente alterarEnderecoDoCliente(@PathParam("id") Long pId, EnderecoDoCliente pEnderecoDoCliente) throws RegraDeNegocioVioladaException {
        return enderecoDoClienteService.alterarEndereco(pId, pEnderecoDoCliente);
    }

    @DELETE
    @Path("{id}")
    @Override
    public void excluirEnderecoDoCliente(@PathParam("id") Long pId) {
        enderecoDoClienteService.apagarEndereco(pId);
    }

    @GET
    @Path("{id}")
    @Override
    public EnderecoDoCliente buscarEnderecoDoCliente(@PathParam("id") Long pId) throws RegraDeNegocioVioladaException {
        EnderecoDoCliente endereco = enderecosDoClienteRepository.findById(pId);
        if (endereco == null) {
            throw new RegistroNaoEncontradoException();
        }
        return endereco;
    }
}
