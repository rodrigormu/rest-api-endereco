package br.com.rmu.restapiendereco.localizadorcep.domain.model

import br.com.rmu.restapiendereco.shared.domain.model.Cep
import org.junit.Assert
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by rodrigomunhoz on 31/08/15.
 */
class CepUnitSpec extends Specification {
    @Unroll
    def "Ao verificar se o CEP #cep é válido deve retornar #resultado"(cep, resultado) {
        given: "Tenho uma instancia de CEP com o código #cep"
        Cep cepParaValidar = new Cep(cep)
        expect: "Ao verificar se é válido deve retornar #resultado"
        cepParaValidar.isValido() == resultado
        where:
        cep          | resultado
        "12OPI-000"  | false
        "13045-000"  | true
        "13045000"   | true
        "13.045-000" | true
        "13.045000"  | true
        "1#\$PI-000" | false
        "130450000"  | false
        null         | false
    }

    def "Deve remover a formatação quando um cep formatado é informado"() {
        given: "Tenho uma instancia de CEP com formatação (13.045-000)"
        String codigoFormatado = "13.045-000"
        String codigoSemFormatacao = "13045000"
        when: "Eu solicitar a remoção da formatação"
        then: "O cep deve ficar sem formatação (13045000)"
        Assert.assertEquals(codigoSemFormatacao, new Cep().removerFormatacaoCodigo(codigoFormatado))
    }

    def "Deve retornar null quando for solicitado remover a formatação de cep=null"() {
        given: "Tenho uma instancia de CEP sem código"
        when: "Quando eu solicitar a remoção da formatação"
        then: "Deve retornar null"
        Assert.assertNull(new Cep().removerFormatacaoCodigo(null))
    }

}
