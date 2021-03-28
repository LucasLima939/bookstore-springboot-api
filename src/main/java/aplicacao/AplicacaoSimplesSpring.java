package aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Cadastro;
import model.Endereco;
import repository.CadastroRepositorio;

@Component
public class AplicacaoSimplesSpring {
	@Autowired
	private CadastroRepositorio cadastroRepository;
	
	public Cadastro criarUsuario() {
		Cadastro cadastro =  new Cadastro(
				"Lucas02",
				"22222222223", 
				"teste@teste2",
				"teste03",
				"123456",
				new Endereco(
						"50000-000"
						)
				);
        cadastro.setEmail("teste@teste2");
        cadastro.setCpf("22222222223");
        cadastro.setName("Lucas02");
        cadastro.setLogin("teste03");
        cadastro.setSenha("123456");
        cadastro.setTelefone("99999999");
        return cadastroRepository.save(cadastro);
		
	}
	
}
