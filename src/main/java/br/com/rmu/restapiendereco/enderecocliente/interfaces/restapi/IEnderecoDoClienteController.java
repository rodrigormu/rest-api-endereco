package br.com.rmu.restapiendereco.enderecocliente.interfaces.restapi;

import br.com.rmu.restapiendereco.enderecocliente.domain.model.EnderecoDoCliente;
import br.com.rmu.restapiendereco.shared.infra.exception.RegraDeNegocioVioladaException;

/**
 * Define os serviços que serão expostos para Endereço de Cliente.
 */
public interface IEnderecoDoClienteController {
    EnderecoDoCliente incluirEnderecoDoCliente(EnderecoDoCliente pEnderecoDoCliente) throws RegraDeNegocioVioladaException;

    EnderecoDoCliente alterarEnderecoDoCliente(Long pId, EnderecoDoCliente pEnderecoDoCliente) throws RegraDeNegocioVioladaException;

    void excluirEnderecoDoCliente(Long pId);

    EnderecoDoCliente buscarEnderecoDoCliente(Long pId) throws RegraDeNegocioVioladaException;
}
