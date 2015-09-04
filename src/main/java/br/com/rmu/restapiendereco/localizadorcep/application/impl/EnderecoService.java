package br.com.rmu.restapiendereco.localizadorcep.application.impl;

import br.com.rmu.restapiendereco.localizadorcep.application.IEnderecoService;
import br.com.rmu.restapiendereco.localizadorcep.domain.model.Endereco;
import br.com.rmu.restapiendereco.localizadorcep.domain.repository.IEnderecosRepository;
import br.com.rmu.restapiendereco.shared.domain.exception.CepInvalidoException;
import br.com.rmu.restapiendereco.shared.domain.model.Cep;
import br.com.rmu.restapiendereco.shared.infra.exception.RegistroNaoEncontradoException;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rodrigomunhoz on 31/08/15.
 */
@Service
public class EnderecoService implements IEnderecoService {
    @Autowired
    private IEnderecosRepository enderecosRepository;

    @Override
    public Endereco buscarPorCep(String pCep) {
        Validate.notNull(pCep, "CEP é obrigatório.");
        Cep cepDesejado = new Cep(pCep);
        validarCep(cepDesejado);
        Endereco enderecoEncontrado = enderecosRepository.findByCep(cepDesejado);
        if (enderecoEncontrado == null) {
            enderecoEncontrado = buscarCepMaisGenerico(cepDesejado);
        }
        if (enderecoEncontrado == null) {
            throw new RegistroNaoEncontradoException();
        }
        return enderecoEncontrado;
    }

    private void validarCep(Cep cepDesejado) {
        if (!cepDesejado.isValido())
            throw new CepInvalidoException();
    }

    /**
     * Busca o cep mais genérico substituindo o desejado por "zeros" direita pra esquerda
     *
     * @param pCepDesejado - Cep desejado para busca
     * @return
     */
    private Endereco buscarCepMaisGenerico(Cep pCepDesejado) {
        Endereco retorno = null;
        Cep cepBuscado = new Cep(pCepDesejado.getCodigo());
        StringBuilder codigoCep = new StringBuilder(cepBuscado.getCodigo());
        for (int i = codigoCep.length() - 1; i >= 0; i--) {
            codigoCep.setCharAt(i, '0');
            cepBuscado.setCodigo(codigoCep.toString());
            retorno = enderecosRepository.findByCep(cepBuscado);
            if (retorno != null) {
                break;
            }
        }
        return retorno;
    }
}
