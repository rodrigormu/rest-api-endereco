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
        setup:
        Cep cepParaValidar = new Cep(cep)
        expect:
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
        String codigoFormatado = "13.0.45-0-00"
        String codigoSemFormatacao = "13045000"
        Assert.assertEquals(codigoSemFormatacao, new Cep().removerFormatacaoCodigo(codigoFormatado))
    }

    def "Deve retornar null quando for solicitado remover a formatação de cep=null"() {
        Assert.assertNull(new Cep().removerFormatacaoCodigo(null))
    }

}
