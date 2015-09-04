package br.com.rmu.restapiendereco.enderecocliente.domain.model;

import br.com.rmu.restapiendereco.shared.domain.model.Cep;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Representa um endereço de cliente
 */
@Entity
public class EnderecoDoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Embedded
    @NotNull(message = "enderecoDoCliente.cep.not.null")
    private Cep cep;
    @NotBlank(message = "enderecoDoCliente.rua.not.blank")
    private String rua;
    @NotNull(message = "enderecoDoCliente.numero.not.null")
    private Integer numero;
    @NotBlank(message = "enderecoDoCliente.cidade.not.blank")
    private String cidade;
    @NotBlank(message = "enderecoDoCliente.estado.not.blank")
    private String estado;
    private String bairro;
    private String complemento;

    /**
     * Construtor somente para JPA. Não deve ser utilizado
     */
    protected EnderecoDoCliente() {
        super();
    }

    /**
     * Construtor com todos os parâmetros obrigatórios
     *
     * @param pCep    - CEP do endereço
     * @param pRua    - Nome da rua, avenida, alameda, etc
     * @param pNumero - Número
     * @param pCidade - Nome da Cidade
     * @param pEstado - Sigla do estado
     */
    public EnderecoDoCliente(Cep pCep, String pRua, Integer pNumero, String pCidade, String pEstado) {
        this();
        this.cep = pCep;
        this.rua = pRua;
        this.numero = pNumero;
        this.cidade = pCidade;
        this.estado = pEstado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long pId) {
        this.id = pId;
    }

    public Cep getCep() {
        return cep;
    }

    public void setCep(Cep pCep) {
        this.cep = pCep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String pRua) {
        this.rua = pRua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer pNumero) {
        this.numero = pNumero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String pCidade) {
        this.cidade = pCidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String pEstado) {
        this.estado = pEstado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String pBairro) {
        this.bairro = pBairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String pComplemento) {
        this.complemento = pComplemento;
    }
}
