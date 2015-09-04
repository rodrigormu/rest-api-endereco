package br.com.rmu.restapiendereco.shared.infra.exception;

import javax.validation.ConstraintViolation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Deve ser lançada quando erros de negócio forem lançados em operações envolvendo operações de CRUD, pois nesses casos
 * deve-se informar o maior número de violações possíveis.
 */
public class RegraDeNegocioVioladaException extends Exception {
    private Collection<Erro> erros;

    /**
     * Constutor padrão.
     */
    public RegraDeNegocioVioladaException() {
        super();
        this.erros = new HashSet<>();
    }

    /**
     * Construtor que já inicializa a lista de erros com as violações do Hibernate Validator.
     *
     * @param pViolations - Lista de violações.
     */
    public RegraDeNegocioVioladaException(Set<? extends ConstraintViolation> pViolations) {
        this();
        for (ConstraintViolation violation : pViolations) {
            this.erros.add(new Erro(violation.getMessageTemplate(), violation.getMessage()));
        }
    }

    /**
     * Retorna a lista de erros.
     *
     * @return Lista de erros da exception
     */
    public Collection<Erro> getErros() {
        return erros;
    }

}
