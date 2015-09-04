package br.com.rmu.restapiendereco.localizadorcep.application

import br.com.rmu.restapiendereco.Application
import br.com.rmu.restapiendereco.localizadorcep.domain.model.Endereco
import br.com.rmu.restapiendereco.localizadorcep.domain.repository.IEnderecosRepository
import br.com.rmu.restapiendereco.shared.domain.exception.CepInvalidoException
import br.com.rmu.restapiendereco.shared.infra.exception.RegistroNaoEncontradoException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification

/**
 * Created by rodrigomunhoz on 02/09/15.
 */
@SpringApplicationConfiguration(classes = Application.class)
class BuscarEnderecoPorCepSpec extends Specification {

    @Autowired
    private IEnderecoService enderecoService
    @Autowired
    private IEnderecosRepository enderecoRepository

    def "Buscar endereço por CEP sem informa o CEP."() {
        when: "Eu buscar um endereço sem informa o CEP"
        enderecoService.buscarPorCep(null)
        then: "Deve ser lançada exceção informando que o CEP é obrigatório"
        thrown(NullPointerException)
    }

    def "Buscar endereço usando CEP inválido."() {
        setup:
        def cepInvalido = "1304500"
        when:
        "Eu buscar um endereço informando o CEP $cepInvalido"
        enderecoService.buscarPorCep(cepInvalido)
        then: "Deve ser lançada exceção informando que o CEP é inválido"
        thrown(CepInvalidoException)
    }

    def "Buscar endereço com CEP que não possui endereço cadastrado"() {
        given: "Eu tenho endereço cadastrado para o CEP 13045909"
        String cepProcurado = "99999990"
        when:
        "Eu buscar endereço pelo CEP $cepProcurado"
        enderecoService.buscarPorCep(cepProcurado)
        then: "Deve lançar exceçãoinformando que não foi encontrado registro"
        thrown(RegistroNaoEncontradoException)
    }

    def "Buscar endereço com CEP que possui endereço cadastrado"() {
        given: "Eu tenho endereço cadastrado para o CEP 13045909"
        String cepProcurado = "13045909"
        when:
        "Eu buscar endereço pelo CEP $cepProcurado"
        Endereco endereco = enderecoService.buscarPorCep(cepProcurado)
        then: "Deve retornar o endereco encontrado"
        endereco.cep.codigo == cepProcurado
    }

    def "Buscar endereço com CEP que possui endereço cadastrado deve retornar endereço mesmo quando o cep informado estiver formatado"() {
        given: "Eu tenho endereço cadastrado para o CEP 13045909"
        String cepProcurado = "13.045-909"
        when:
        "Eu buscar endereço pelo CEP $cepProcurado"
        Endereco endereco = enderecoService.buscarPorCep(cepProcurado)
        then: "Deve retornar o endereco encontrado"
        endereco.cep.codigo == cepProcurado.replaceAll("\\.", "").replaceAll("-", "")
    }

    def "Buscar endereço por CEP mais genérico quando o buscado não estiver no cadastro. (CEP BUSCADO = #cepBuscado / CEP RETORNADO = #cepRetornado)"(cepBuscado, cepRetornado) {
        given: "Eu tenha endereços cadastrados para os CEPs (00000000, 10000000, 13000000, 13100000, 13040000, 13045000, 13046100, 13047120)"
        when:
        "Eu buscar endereço pelo CEP $cepBuscado"
        Endereco endereco = enderecoService.buscarPorCep(cepBuscado)
        then: "Deve retornar o endereco encontrado"
        endereco.cep.codigo == cepRetornado.replaceAll("\\.", "").replaceAll("-", "")
        where:
        cepBuscado   | cepRetornado
        "13.047-129" | "13047120"
        "13.046-198" | "13046100"
        "13.045-768" | "13045000"
        "13.048-987" | "13040000"
        "13.199-999" | "13100000"
        "13.999-999" | "13000000"
        "19.999-999" | "10000000"
    }
}
