package br.com.rmu.restapiendereco.enderecocliente.interfaces.restapi

import br.com.rmu.restapiendereco.Application
import br.com.rmu.restapiendereco.enderecocliente.application.IEnderecoDoClienteService
import br.com.rmu.restapiendereco.enderecocliente.domain.model.EnderecoDoCliente
import br.com.rmu.restapiendereco.enderecocliente.domain.repository.IEnderecosDoClienteRepository
import br.com.rmu.restapiendereco.shared.domain.model.Cep
import br.com.rmu.restapiendereco.shared.infra.exception.Erro
import org.apache.http.HttpStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Classe com testes que garantem o comportamento geral do serviço de busca de endereços por cep.
 * Garante o tratamento de requst inválido, not found e sucesso.
 */
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest("server.port=9000")
@Narrative(
        """
EU, como usuário
QUERO administrar meus dados de endereço
PARA Incluir, Consultar, Atualizar, Deletar meu endereço
"""
)
class CrudEnderecoClienteApiSpec extends Specification {
    @Autowired
    private IEnderecosDoClienteRepository enderecosDoClienteRepository
    @Autowired
    private IEnderecoDoClienteService enderecoDoClienteService
    private int serverPort = 9000
    private String apiUri = "http://localhost:" + serverPort + "/api/enderecoscliente"
    private RestTemplate restTemplate = new TestRestTemplate()

    def cleanup() {
        enderecosDoClienteRepository.deleteAll()
    }

    def "Buscar um endereço informando ID inexistente"() {
        given: "Não exista um endereço com id 999"
        assert !enderecosDoClienteRepository.findById(999)
        when: "Eu solicitar o endereço informando o ID 999"
        ResponseEntity<Erro> entity = restTemplate.getForEntity("$apiUri/999", Erro.class)
        then: "Deve responder status 404"
        entity.statusCode.value() == HttpStatus.SC_NOT_FOUND
    }

    def "Buscar um endereço informando ID existente"() {
        given: "Eu tenho um endereço de cliente cadastrado"
        EnderecoDoCliente enderecoCadastrado = cadastrarEnderecoDoCliente()
        when: "Eu solicitar o endereço informando id existente"
        ResponseEntity<EnderecoDoCliente> entity = restTemplate.getForEntity("$apiUri/${enderecoCadastrado.id}", EnderecoDoCliente.class)
        then: "Deve responder status 200"
        entity.statusCode.value() == HttpStatus.SC_OK
        and: "O body deve conter os dados do endereço"
        entity.body.id == enderecoCadastrado.id
        entity.body.cep.codigo == enderecoCadastrado.cep.codigo
        entity.body.rua == enderecoCadastrado.rua
        entity.body.numero == enderecoCadastrado.numero
        entity.body.cidade == enderecoCadastrado.cidade
        entity.body.estado == enderecoCadastrado.estado
    }

    def "Alterar endereço informando ID inexistente"() {
        given: "Não exista um endereço com o id 999"
        assert !enderecosDoClienteRepository.findById(999)
        when: "Eu solicitar alteração informando o id 999"
        ResponseEntity<Erro> retorno = restTemplate.exchange(
                "$apiUri/999",
                HttpMethod.PUT,
                null,
                Erro.class);
        then: "Deve responder status 404"
        retorno.statusCode.value() == HttpStatus.SC_NOT_FOUND
    }

    def "Alterar endereço informando ID existente"() {
        given: "Eu tenho um endereco de cliente cadastrado"
        EnderecoDoCliente enderecoCadastrado = cadastrarEnderecoDoCliente()
        def saoPaulo = "São Paulo"
        def complemento = "Apartamento 15"
        when:
        "Eu alterar a cidade para $saoPaulo e o complemento para $complemento"
        enderecoCadastrado.cidade = saoPaulo
        enderecoCadastrado.complemento = complemento
        and: "Solicitar alteração informando o id desse endereco"
        HttpEntity<EnderecoDoCliente> enderecoDoBody = new HttpEntity<>(enderecoCadastrado)
        ResponseEntity<EnderecoDoCliente> retorno = restTemplate.exchange(
                "$apiUri/${enderecoCadastrado.id}",
                HttpMethod.PUT,
                enderecoDoBody,
                EnderecoDoCliente.class)
        then: "Deve responder status 200"
        retorno.statusCode.value() == HttpStatus.SC_OK
        and: "A cidade e o complemento devem ter sido alterados"
        retorno.body.cidade == saoPaulo
        retorno.body.complemento == complemento
    }

    def "Excluir endereço informando ID inexistente"() {
        given: "Não exista um endereço com o id 999"
        assert !enderecosDoClienteRepository.findById(999)
        when: "Eu solicitar exclusão informando o id 999"
        ResponseEntity<Erro> retorno = restTemplate.exchange(
                "$apiUri/999",
                HttpMethod.DELETE,
                null,
                Erro.class);
        then: "Deve responder status 404"
        retorno.statusCode.value() == HttpStatus.SC_NOT_FOUND
    }

    def "Excluir endereço informando ID existente"() {
        given: "Eu tenho um endereco de cliente cadastrado"
        EnderecoDoCliente enderecoCadastrado = cadastrarEnderecoDoCliente()
        when: "Solicitar exclusão informando o id desse endereco"
        ResponseEntity retorno = restTemplate.exchange(
                "$apiUri/${enderecoCadastrado.id}",
                HttpMethod.DELETE,
                null,
                Object.class)
        then: "Deve responder status 204"
        retorno.statusCode.value() == HttpStatus.SC_NO_CONTENT
    }

    def "Incluir endereço informando todos os campos obrigatórios"() {
        when: "Solicitar inclusão informando todos os campos obrigatórios"
        EnderecoDoCliente novoEndereco = new EnderecoDoCliente(new Cep("13045000"), "Rua 5", 90, "Campinas", "SP")
        HttpEntity<EnderecoDoCliente> enderecoDoBody = new HttpEntity<>(novoEndereco)
        ResponseEntity<EnderecoDoCliente> retorno = restTemplate.exchange(
                "$apiUri",
                HttpMethod.POST,
                enderecoDoBody,
                EnderecoDoCliente.class)
        then: "Deve responder status 200"
        retorno.statusCode.value() == HttpStatus.SC_OK
        and: "O body deve conter os dados do endereço"
        retorno.body.id != null
        retorno.body.cep.codigo == novoEndereco.cep.codigo
        retorno.body.rua == novoEndereco.rua
        retorno.body.numero == novoEndereco.numero
        retorno.body.cidade == novoEndereco.cidade
        retorno.body.estado == novoEndereco.estado
    }

    @Unroll
    def "Incluir endereço sem informar #propriedade"(novoEndereco, propriedade, mensagemDeErro) {
        when:
        "Solicitar inclusão sem informar o campo $propriedade que é obrigatório"
        ParameterizedTypeReference<Collection<Erro>> typeReference = new ParameterizedTypeReference<Collection<Erro>>() {
        }
        HttpEntity<EnderecoDoCliente> enderecoDoBody = new HttpEntity<>(novoEndereco)
        ResponseEntity<Collection<Erro>> retorno = restTemplate.exchange(
                "$apiUri",
                HttpMethod.POST,
                enderecoDoBody,
                typeReference)
        then: "Deve responder status 400"
        retorno.statusCode.value() == HttpStatus.SC_BAD_REQUEST
        and:
        "O body deve conter mensagem de erro com a mensagem $mensagemDeErro"
        retorno.body.first().mensagem == mensagemDeErro
        where:
        novoEndereco                                                                | propriedade | mensagemDeErro
        new EnderecoDoCliente(null, "Rua 5", 90, "Campinas", "SP")                  | "Cep"       | "enderecoDoCliente.cep.not.null"
        new EnderecoDoCliente(new Cep("13056997"), null, 90, "Campinas", "SP")      | "Rua"       | "enderecoDoCliente.rua.not.blank"
        new EnderecoDoCliente(new Cep("13056997"), "Rua 5", null, "Campinas", "SP") | "Número"    | "enderecoDoCliente.numero.not.null"
        new EnderecoDoCliente(new Cep("13056997"), "Rua 5", 90, null, "SP")         | "Cidade"    | "enderecoDoCliente.cidade.not.blank"
        new EnderecoDoCliente(new Cep("13056997"), "Rua 5", 90, "Campinas", null)   | "Estado"    | "enderecoDoCliente.estado.not.blank"
    }

    private EnderecoDoCliente cadastrarEnderecoDoCliente() {
        EnderecoDoCliente novoEndereco = new EnderecoDoCliente(new Cep("12230680"), "Rua 3", 132, "Campinas", "SP")
        return enderecoDoClienteService.incluirEndereco(novoEndereco)
    }
}
