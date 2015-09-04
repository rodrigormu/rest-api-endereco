package br.com.rmu.restapiendereco.shared.infra.exception;

/**
 * Classe genérica de erro da aplicação.
 */
public class ParsableException extends RuntimeException {
    private Erro erro;

    public ParsableException(Erro pErro) {
        super(pErro.getMensagem());
        this.erro = pErro;
    }

    public Erro getErro() {
        return erro;
    }
}
