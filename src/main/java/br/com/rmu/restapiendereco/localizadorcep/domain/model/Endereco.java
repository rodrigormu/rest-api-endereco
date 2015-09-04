package br.com.rmu.restapiendereco.localizadorcep.domain.model;

import br.com.rmu.restapiendereco.shared.domain.model.Cep;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.Objects;

/**
 * Representa um endereço não vinculado a cliente.
 */
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Embedded
    private Cep cep;
    private String rua;
    private String cidade;
    private String estado;

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
     * @param pCidade - Nome da Cidade
     * @param pEstado - Sigla do estado
     */
    public Endereco(Cep pCep, String pRua, String pCidade, String pEstado) {
        this();
        Validate.notNull(pCep, "CEP é obrigatório.");
        Validate.notBlank(pRua, "Rua é obrigatório.");
        Validate.notBlank(pCidade, "Cidade é obrigatório.");
        Validate.notBlank(pEstado, "Estado é obrigatório.");
        this.cep = pCep;
        this.rua = pRua;
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
