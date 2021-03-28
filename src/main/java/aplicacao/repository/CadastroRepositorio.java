package aplicacao.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.springframework.data.repository.CrudRepository;

import aplicacao.model.Cadastro;
import aplicacao.model.Endereco;

public interface CadastroRepositorio extends CrudRepository<Cadastro, Integer>{
    
    

}
