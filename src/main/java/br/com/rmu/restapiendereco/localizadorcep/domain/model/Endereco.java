package br.com.rmu.restapiendereco.localizadorcep.domain.model;

import br.com.rmu.restapiendereco.shared.domain.model.Cep;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Representa um endereço não vinculado a cliente.
 */
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;
    @Embedded
    private Cep cep;
    private String rua;
    private String cidade;
    private String estado;
    private String bairro;

    /**
     * Construtor somente para JPA. Não deve ser utilizado
     */
    protected Endereco() {
        super();
    }

    /**
     * Construtor com todos os parâmetros obrigatórios
     *
     * @param pCep    - CEP do endereço
     * @param pRua    - Nome da rua, avenida, alameda, etc
     * @param pBairro - Nome do Bairro
     * @param pCidade - Nome da Cidade
     * @param pEstado - Sigla do estado
     */
    public Endereco(Cep pCep, String pRua, String pBairro, String pCidade, String pEstado) {
        this();
        this.cep = pCep;
        this.rua = pRua;
        this.cidade = pCidade;
        this.estado = pEstado;
        this.bairro = pBairro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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
}
