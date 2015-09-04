package br.com.rmu.restapiendereco.shared.infra.exception;

/**
 * Representação amigável de uma mensagem de erro. Possui código e mensagem detalhada
 */
public class Erro {
    private String codigo;
    private String mensagem;

    public Erro(String pCodigo, String pMensagem) {
        this.codigo = pCodigo;
        this.mensagem = pMensagem;
    }

    /**
     * Construtor privado para compatibilidade com frameworks de mash e unmarsh JSON.
     */
    protected Erro() {
        super();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

}
