package aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Cadastro;
import repository.CadastroRepositorio;

@Component
public class AplicacaoSimplesSpring {
	@Autowired
	private CadastroRepositorio cadastroRepository;
	
	public Cadastro criarUsuario() {
		Cadastro cadastro =  new Cadastro();
        cadastro.setEmail("teste@teste2");
        cadastro.setCpf("22222222223");
        cadastro.setName("Lucas02");
        cadastro.setLogin("teste03");
        cadastro.setSenha("123456");
        cadastro.setTelefone("99999999");
        return cadastroRepository.save(cadastro);
		
	}
	
}
