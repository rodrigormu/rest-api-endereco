package br.com.rmu.restapiendereco.enderecocliente.application.impl;

import br.com.rmu.restapiendereco.enderecocliente.application.IEnderecoDoClienteService;
import br.com.rmu.restapiendereco.enderecocliente.domain.model.EnderecoDoCliente;
import br.com.rmu.restapiendereco.enderecocliente.domain.repository.IEnderecosDoClienteRepository;
import br.com.rmu.restapiendereco.shared.infra.exception.RegistroNaoEncontradoException;
import br.com.rmu.restapiendereco.shared.infra.exception.RegraDeNegocioVioladaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by rodrigomunhoz on 03/09/15.
 */
@Service
public class EnderecoDoClienteService implements IEnderecoDoClienteService {
    @Autowired
    private LocalValidatorFactoryBean validatorFactory;
    @Autowired
    private IEnderecosDoClienteRepository enderecosDoClienteRepository;

    @Override
    public EnderecoDoCliente incluirEndereco(EnderecoDoCliente pEnderecoDoCliente) throws RegraDeNegocioVioladaException {
        validar(pEnderecoDoCliente);
        return enderecosDoClienteRepository.save(pEnderecoDoCliente);
    }

    @Override
    public EnderecoDoCliente alterarEndereco(Long pIdEnderecoCliente, EnderecoDoCliente pEnderecoComAsAlteracoes) throws RegraDeNegocioVioladaException {
        EnderecoDoCliente enderecoParaAlterar = buscaEnderecoClienteDandoErroSeNaoEncontrar(pIdEnderecoCliente);
        validar(pEnderecoComAsAlteracoes);
        enderecoParaAlterar.setCidade(pEnderecoComAsAlteracoes.getCidade());
        enderecoParaAlterar.setEstado(pEnderecoComAsAlteracoes.getEstado());
        enderecoParaAlterar.setCep(pEnderecoComAsAlteracoes.getCep());
        enderecoParaAlterar.setComplemento(pEnderecoComAsAlteracoes.getComplemento());
        enderecoParaAlterar.setRua(pEnderecoComAsAlteracoes.getRua());
        enderecoParaAlterar.setNumero(pEnderecoComAsAlteracoes.getNumero());
        enderecoParaAlterar.setBairro(pEnderecoComAsAlteracoes.getBairro());
        return enderecosDoClienteRepository.save(enderecoParaAlterar);
    }

    @Override
    public void apagarEndereco(Long pIdEnderecoCliente) {
        EnderecoDoCliente enderecoParaApagar = buscaEnderecoClienteDandoErroSeNaoEncontrar(pIdEnderecoCliente);
        enderecosDoClienteRepository.delete(enderecoParaApagar);
    }

    private EnderecoDoCliente buscaEnderecoClienteDandoErroSeNaoEncontrar(Long pIdEnderecoCliente) {
        EnderecoDoCliente enderecoDesejado = enderecosDoClienteRepository.findById(pIdEnderecoCliente);
        if (enderecoDesejado == null) {
            throw new RegistroNaoEncontradoException();
        }
        return enderecoDesejado;
    }

    private void validar(EnderecoDoCliente pEndereco) throws RegraDeNegocioVioladaException {
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<EnderecoDoCliente>> violations = validator.validate(pEndereco);
        if (!violations.isEmpty())
            throw new RegraDeNegocioVioladaException(violations);
    }
}
