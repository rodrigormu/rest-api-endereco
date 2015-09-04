package br.com.rmu.restapiendereco.shared.domain.exception;

import br.com.rmu.restapiendereco.shared.infra.exception.Erro;
import br.com.rmu.restapiendereco.shared.infra.exception.FormatoInvalidoException;

/**
 * Created by rodrigomunhoz on 02/09/15.
 */
public class CepInvalidoException extends FormatoInvalidoException {
    /**
     *
     */
    public static final Erro MENSAGEM_CEP_INVALIDO = new Erro("CEP_INVALIDO", "cep.invalido");

    /**
     * Construtor com mensagem de erro
     */
    public CepInvalidoException() {
        super(MENSAGEM_CEP_INVALIDO);
    }
}
