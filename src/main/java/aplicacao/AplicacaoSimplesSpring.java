package aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.Cadastro;
import model.CadastroLivro;
import repository.CadastroLivroRepositorio;
import repository.CadastroRepositorio;

@Component
public class AplicacaoSimplesSpring {
//	@Autowired
//	private CadastroRepositorio cadastroRepository;
//	
	@Autowired
	private CadastroLivroRepositorio livroRepository;
	
//	public Cadastro criarUsuario() {
//		Cadastro cadastro =  new Cadastro();
//        cadastro.setEmail("teste@teste2");
//        cadastro.setCpf("22222222223");
//        cadastro.setName("Lucas02");
//        cadastro.setLogin("teste03");
//        cadastro.setSenha("123456");
//        cadastro.setTelefone("99999999");
//        return cadastroRepository.save(cadastro);
//		
////	}
	
	public CadastroLivro cadastroLivro() {
		CadastroLivro livro =  new CadastroLivro();
		livro.setTitulo("Livro legal");
		livro.setIsbn("5453");
		livro.setNumeroExemplares(25);
		livro.setNumeroExemplaresReservados(13);
		livro.setValorDiaria(25.00);
        return livroRepository.save(livro);
		
	}
	
}
