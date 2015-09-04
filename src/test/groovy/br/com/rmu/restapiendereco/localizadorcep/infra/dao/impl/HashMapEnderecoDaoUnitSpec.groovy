package br.com.rmu.restapiendereco.localizadorcep.infra.dao.impl

import br.com.rmu.restapiendereco.Application
import br.com.rmu.restapiendereco.localizadorcep.domain.model.Endereco
import br.com.rmu.restapiendereco.localizadorcep.infra.dao.IEnderecoDao
import br.com.rmu.restapiendereco.shared.domain.model.Cep
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification

/**
 * Testa se o Dao Mock, baseado em HashMap para busca por cep está funcionando como esperado. Teste baseado nos dados DUMMY que estarão disponíveis no MAP durante a execução da aplicação.
 *
 */
@SpringApplicationConfiguration(classes = Application.class)
class HashMapEnderecoDaoUnitSpec extends Specification {
    @Autowired
    private IEnderecoDao enderecoDao;

    def "A busca deve retornar o endereço quando informado cep existente"() {
        given: "Eu tenho um endereço com o cep 13045909"
        when: "Eu chamar a busca por cep passando 13045909 como parâmetro"
        Endereco endereco = enderecoDao.findByCep(new Cep("13045909"))
        then: "Deve retornar o endereço"
        endereco != null
        endereco.cep.codigo == "13045909"
    }

    def "A busca deve retornar o null quando informado cep inexistente"() {
        given: "Eu não tenho um endereço com o cep 12345678"
        when: "Eu chamar a busca por cep passando 12345678 como parâmetro"
        Endereco endereco = enderecoDao.findByCep(new Cep("12345678"))
        then:"Deve retornar null"
        endereco == null
    }

    def "A busca deve retornar o null quando informado cep null"() {
        when: "Eu chamar a busca por cep passando null como parâmetro "
        Endereco endereco = enderecoDao.findByCep(null)
        then: "Deve retornar null"
        endereco == null
    }
}
