package aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aplicacao.model.Cadastro;
import aplicacao.model.Endereco;
import aplicacao.repository.CadastroRepositorio;


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
						"50000-000"));
        return cadastroRepository.save(cadastro);
		
	}
	
}

