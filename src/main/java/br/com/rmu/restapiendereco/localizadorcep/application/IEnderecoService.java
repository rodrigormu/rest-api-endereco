package br.com.rmu.restapiendereco.localizadorcep.application;

import br.com.rmu.restapiendereco.localizadorcep.domain.model.Endereco;

/**
 * Serviços relacionados a endereço
 */
public interface IEnderecoService {
    /**
     * Busca o endereço a partir do CEP informado. O Cep deve estar em formato válido, caso não esteja,
     * será lançada CepInvalidoException. Se não for encontrado endereço  para o Cep, serão feitas buscas
     * com ceps mais genéricos substituindo o código por "zero" da direita para esquerda até que o cep seja
     * só "zeros". Ex: Se for informado o CEP 13045-999 e não for encontrado endereço, será feita uma busca
     * com 13045990, se não encontrar, buscará por 13045900, e assim sucessivamente até encontrar endereço
     * ou o Cep ser "00000000".
     *
     * @param pCep - Cep usado na busca
     * @return O endereço encontrado
     */
    Endereco buscarPorCep(String pCep);
}

