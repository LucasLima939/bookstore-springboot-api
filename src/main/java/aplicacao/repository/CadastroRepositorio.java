package aplicacao.repository;

import org.springframework.data.repository.CrudRepository;

import aplicacao.model.Cadastro;

public interface CadastroRepositorio extends CrudRepository<Cadastro, Integer>{
	
	Cadastro findByLoginLogin(String login);
	
	
    
    

}
