package br.com.rmu.restapiendereco.shared.infra.exception;

/**
 * Exception não verificada que deve ser lançada quando não for encontrado registro.
 */
public class RegistroNaoEncontradoException extends ParsableException {

    private static final Erro MENSAGEM_ERRO = new Erro("REGISTRO_NAO_ENCONTRADO", "Não foi encontrado registro para o filtro informado.");

    /**
     * Construtor que inicia uma mensagem de erro padrão.
     */
    public RegistroNaoEncontradoException() {
        super(MENSAGEM_ERRO);
    }
}
