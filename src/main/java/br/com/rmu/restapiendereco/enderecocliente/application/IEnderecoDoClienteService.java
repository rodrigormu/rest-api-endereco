package br.com.rmu.restapiendereco.enderecocliente.application;

import br.com.rmu.restapiendereco.enderecocliente.domain.model.EnderecoDoCliente;
import br.com.rmu.restapiendereco.shared.infra.exception.RegraDeNegocioVioladaException;

public interface IEnderecoDoClienteService {
    /**
     * Adiciona um novo endereço de cliente. Valida a entidade antes de salvar.
     *
     * @param pEnderecoDoCliente - Endereço que será adicionado
     * @return novo endereço criado
     * @throws RegraDeNegocioVioladaException se alguma regra de negócio foi violada.
     */
    EnderecoDoCliente incluirEndereco(EnderecoDoCliente pEnderecoDoCliente) throws RegraDeNegocioVioladaException;

    /**
     * Altera um endereço de cliente. Valida a entidade antes de salvar
     *
     * @param pIdEnderecoCliente - Id do endereco que está sendo alterado. Será usado para carregar o endereço. Deve existir na base, caso contrário lança uma exceção
     * @param pEnderecoDoCliente - Endereço que será alterado
     * @return novo endereço criado
     * @throws RegraDeNegocioVioladaException se alguma regra de negócio foi violada.
     */
    EnderecoDoCliente alterarEndereco(Long pIdEnderecoCliente, EnderecoDoCliente pEnderecoDoCliente) throws RegraDeNegocioVioladaException;

    /**
     * Exclui um endereço de cliente.
     *
     * @param pIdEnderecoCliente - Id do endereco que está sendo exluido. Será usado para carregar o endereço. Deve existir na base, caso contrário lança uma exceção
     */
    void apagarEndereco(Long pIdEnderecoCliente);

}
