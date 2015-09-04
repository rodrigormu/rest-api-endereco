package br.com.rmu.restapiendereco.localizadorcep.interfaces.restapi

import br.com.rmu.restapiendereco.Application
import br.com.rmu.restapiendereco.localizadorcep.domain.model.Endereco
import br.com.rmu.restapiendereco.localizadorcep.domain.repository.IEnderecosRepository
import br.com.rmu.restapiendereco.shared.domain.exception.CepInvalidoException
import br.com.rmu.restapiendereco.shared.domain.model.Cep
import br.com.rmu.restapiendereco.shared.infra.exception.Erro
import org.apache.http.HttpStatus
import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Narrative
import spock.lang.Specification

/**
 * Classe com testes que garantem o comportamento geral do serviço de busca de endereços por cep.
 * Garante o tratamento de requst inválido, not found e sucesso.
 */
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port=9000")
@Narrative("""
EU, como usuário,
QUERO informar meu CEP e obter o nome da minha RUA, BAIRRO, CIDADE e ESTADO
PARA preencher meus dados de forma automática
""")
class BuscaEnderecoPorCepApiSpec extends Specification {
    @Autowired
    private IEnderecosRepository enderecosRepository;

    private int serverPort = 9000
    private String apiUri = "http://localhost:" + serverPort + "/api/enderecos"
    private RestTemplate restTemplate = new TestRestTemplate();

    def "Chamar o serviço de busca de endereço por cep sem informar o cep via QueryString deve responder status 400"() {
        when: "Eu chamar o serviço de busca de endereços por CEP sem passar o CEP via query string"
        ResponseEntity<Erro> entity = restTemplate.getForEntity(apiUri, Erro.class)
        then: "Deve responder status 400"
        entity.statusCode.value() == HttpStatus.SC_BAD_REQUEST
        and: "O body deve conter o código e mensagem de erro"
        entity.body.codigo != null
        entity.body.mensagem != null
    }

    def "Chamar o serviço de busca de endereço por cep passando cep inválido deve responder status 400"() {
        when: "Eu chamar o serviço de busca de endereços passando um CEP inválido"
        ResponseEntity<Erro> entity = restTemplate.getForEntity(apiUri + "?cep=12230p00", Erro.class)
        then: "Deve responder status 400"
        entity.statusCode.value() == HttpStatus.SC_BAD_REQUEST
        and:
        "com a mensagem ${CepInvalidoException.MENSAGEM_CEP_INVALIDO.mensagem}"
        entity.body.mensagem == CepInvalidoException.MENSAGEM_CEP_INVALIDO.mensagem
    }

    def "Chamar o serviço de busca de endereço por cep passando cep que não corresponde a nenhum endereço deve retornar 404"() {
        when: "Eu chamar o serviço de busca de endereços passando um CEP que não possui endereço vinculado nem utilizando a busca genérica"
        ResponseEntity<Erro> entity = restTemplate.getForEntity(apiUri + "?cep=92230-680", Erro.class)
        then: "Deve responder status 404"
        entity.statusCode.value() == HttpStatus.SC_NOT_FOUND
    }

    def "Chamar o serviço de busca de endereço por cep passando cep que que corresponde a um endereço deve retornar 200"() {
        when: "Eu chamar o serviço de busca de endereços passando um CEP que possui endereço vinculado"
        Endereco enderecoEsperado = enderecosRepository.findByCep(new Cep("13045000"));
        Assert.assertNotNull(enderecoEsperado)
        ResponseEntity<Endereco> entity = restTemplate.getForEntity(apiUri + "?cep=13045000", Endereco.class)
        then: "Deve responder status 200"
        entity.statusCode.value() == HttpStatus.SC_OK
        and: "O endereço deve ser retornado no body"
        entity.body.cep.codigo == enderecoEsperado.cep.codigo
        entity.body.cidade == enderecoEsperado.cidade
        entity.body.rua == enderecoEsperado.rua
        entity.body.estado == enderecoEsperado.estado
    }
}
