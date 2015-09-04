package br.com.rmu.restapiendereco.shared.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Representa um CEP. Garante o formato válido.
 */
@Embeddable
public class Cep {
    /**
     * Define o formato aceito para CEP (##.###-###, #####-### ou ########).
     */
    private static final Pattern CODIGO_PATTERN = Pattern.compile("^(([0-9]{2}\\.[0-9]{3}-[0-9]{3})|([0-9]{2}[0-9]{3}-[0-9]{3})|([0-9]{8}))$");
    @NotBlank(message = "cep.not.blank")
    private String codigo;

    /**
     * Construtor somente para JPA. Não deve ser utilizado
     */
    protected Cep() {
        super();
    }

    /**
     * Construtor com o código. Valida se o código é válido frente a um dos padrões (##.###-###, #####-### ou ########).
     *
     * @param pCodigo - Código completo do CEP (inclusive sufixo). Qualquer separador será removido.
     */
    public Cep(String pCodigo) {
        this();
        atribuiValorAoCodigo(pCodigo);
    }

    public String getCodigo() {
        return this.codigo;
    }

    /**
     * Atribui valor ao atributo Código. Valida o formato e remove caracteres de formatação (pontos e traços).
     *
     * @param pCodigo Código sendo atribuido
     */
    public void setCodigo(String pCodigo) {
        atribuiValorAoCodigo(pCodigo);
    }

    /**
     * Método privado que atribui valor ao Código. Valida e remove formatação antes de atribuir. É privado para poder ser chamado pelo construtor de forma segura.
     *
     * @param pCodigo Código sendo atribuido
     */
    private void atribuiValorAoCodigo(String pCodigo) {
        this.codigo = removerFormatacaoCodigo(pCodigo);
    }

    /**
     * Verifica se o código recebido como parâmetro é válido de acordo com os formatos permitidos de CEP.
     *
     * @return true se o código é valido e falso caso não seja.
     */
    @JsonIgnore
    public boolean isValido() {
        boolean bRet = false;
        if (this.codigo != null) {
            Matcher matcher = CODIGO_PATTERN.matcher(this.codigo);
            bRet = matcher.find();
        }
        return bRet;
    }

    /**
     * Remove pontos e traços de um string.
     *
     * @param pCodigo - String que terá formatação removida.
     * @return O código sem formatação.
     */
    protected String removerFormatacaoCodigo(String pCodigo) {
        String codigoSemFormatacao = pCodigo;
        if (pCodigo != null) {
            codigoSemFormatacao = pCodigo.replaceAll("\\.", "").replace("-", "");
        }
        return codigoSemFormatacao;
    }

   }
