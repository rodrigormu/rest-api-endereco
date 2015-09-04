package br.com.rmu.restapiendereco.localizadorcep.interfaces.restapi;

import br.com.rmu.restapiendereco.localizadorcep.domain.model.Endereco;

public interface IEnderecoController {
    Endereco findByCep(String pCep);
}
