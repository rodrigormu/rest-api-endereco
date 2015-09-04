package br.com.rmu.restapiendereco.shared.infra.exception;

/**
 * Exception não verificada que deve ser lançada quando algum dado do sistema estiver com formato inválido.
 */
public class FormatoInvalidoException extends ParsableException {

    /**
     * Construtor com mensagem de erro
     *
     * @param pErro - A mensagem de erro
     */
    public FormatoInvalidoException(Erro pErro) {
        super(pErro);
    }

}
