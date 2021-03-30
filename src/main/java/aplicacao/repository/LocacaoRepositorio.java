package aplicacao.repository;

import org.springframework.data.repository.CrudRepository;

import aplicacao.model.Locacao;

public interface LocacaoRepositorio extends CrudRepository<Locacao, Integer> {

}
