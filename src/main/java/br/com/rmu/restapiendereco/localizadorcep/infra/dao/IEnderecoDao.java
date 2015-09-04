package br.com.rmu.restapiendereco.localizadorcep.infra.dao;

import br.com.rmu.restapiendereco.localizadorcep.domain.model.Endereco;
import br.com.rmu.restapiendereco.shared.domain.model.Cep;

/**
 * Created by rodrigomunhoz on 03/09/15.
 */
public interface IEnderecoDao {
    /**
     * Busca e retorna um endereço correspondente ao CEP recebido como parâmetro.
     *
     * @param pCep - Cep usado na busca
     * @return Endereço encontrado ou null se nenhum endereço for encontrado para o CEP.
     */
    Endereco findByCep(final Cep pCep);
}
