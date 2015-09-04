package br.com.rmu.restapiendereco.localizadorcep.domain.model

import br.com.rmu.restapiendereco.shared.domain.model.Cep
import spock.lang.Specification

/**
 * Created by rodrigomunhoz on 31/08/15.
 */
class EnderecoUnitSpec extends Specification {

    private static final Cep CEP_TESTE = new Cep("13045000")
    private static final String RUA_TESTE = "Rua"
    private static final String CIDADE_TESTE = "Cidade"
    private static final String ESTADO_TESTE = "SP"

    def "Instanciação do endereço com todos os parametros null"() {
        when:
        new Endereco(null, null, null, null)
        then:
        thrown(NullPointerException)
    }

    def "Instanciação do endereço com rua string vazia"() {
        when:
        new Endereco(CEP_TESTE, "", CIDADE_TESTE, ESTADO_TESTE)
        then:
        thrown(IllegalArgumentException)
    }

    def "Instanciação do endereço com rua sendo espaço em branco"() {
        when:
        new Endereco(CEP_TESTE, " ", CIDADE_TESTE, ESTADO_TESTE)
        then:
        thrown(IllegalArgumentException)
    }

    def "Instanciação do endereço cidade string vazia"() {
        when:
        new Endereco(CEP_TESTE, RUA_TESTE, "", ESTADO_TESTE)
        then:
        thrown(IllegalArgumentException)
    }

    def "Instanciação do endereço cidade sendo espaço em branco"() {
        when:
        new Endereco(CEP_TESTE, RUA_TESTE, " ", ESTADO_TESTE)
        then:
        thrown(IllegalArgumentException)
    }

    def "Instanciação do endereço estado string vazia"() {
        when:
        new Endereco(CEP_TESTE, RUA_TESTE, CIDADE_TESTE, "")
        then:
        thrown(IllegalArgumentException)
    }

    def "Instanciação do endereço estado sendo espaço em branco"() {
        when:
        new Endereco(CEP_TESTE, RUA_TESTE, CIDADE_TESTE, " ")
        then:
        thrown(IllegalArgumentException)
    }

    def "Instanciação do endereço com cep, rua, cidade estado"() {
        when:
        Endereco endereco = new Endereco(CEP_TESTE, RUA_TESTE, CIDADE_TESTE, ESTADO_TESTE)
        then:
        endereco.cep == CEP_TESTE
        endereco.rua == RUA_TESTE
        endereco.cidade == CIDADE_TESTE
        endereco.estado == ESTADO_TESTE
    }
}
