package br.com.rmu.restapiendereco.localizadorcep.infra.dao.impl;

import br.com.rmu.restapiendereco.localizadorcep.domain.model.Endereco;
import br.com.rmu.restapiendereco.localizadorcep.domain.repository.IEnderecosRepository;
import br.com.rmu.restapiendereco.localizadorcep.infra.dao.IEnderecoDao;
import br.com.rmu.restapiendereco.shared.domain.model.Cep;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigomunhoz on 03/09/15.
 */
@Component
public class HashMapEnderecoDao implements IEnderecoDao, IEnderecosRepository {
    private static Map<String, Endereco> dados;

    @PostConstruct
    private void inicializarDados() {
        dados = new LinkedHashMap<>();
        List<String> ceps = Arrays.asList("10000000", "13000000", "13100000", "13040000"
                , "13045000", "13046100", "13047120", "13045909");
        for (String cep : ceps) {
            dados.put(cep, novaInstanciaDeEndereco(cep, "Rua do " + cep, "Cidade do cep " + cep, "Estado do cep " + cep));
        }
    }

    private Endereco novaInstanciaDeEndereco(String pCep, String pRua, String pCidade, String pEstado) {
        return new Endereco(new Cep(pCep), pRua, pCidade, pEstado);
    }

    @Override
    public Endereco findByCep(Cep pCep) {
        Endereco retorno = null;
        if (pCep != null) {
            retorno = dados.get(pCep.getCodigo());
        }
        return retorno;
    }
}
