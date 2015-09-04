package br.com.rmu.restapiendereco.enderecocliente.domain.repository;

import br.com.rmu.restapiendereco.enderecocliente.domain.model.EnderecoDoCliente;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositório de endereços do cliente. A implementação será gerada automaticamente graças ao uso do SpringData
 */
public interface IEnderecosDoClienteRepository extends CrudRepository<EnderecoDoCliente, Long> {

    void delete(final EnderecoDoCliente pEndereco);

    EnderecoDoCliente save(EnderecoDoCliente pEndereco);

    EnderecoDoCliente findById(Long pId);
}
