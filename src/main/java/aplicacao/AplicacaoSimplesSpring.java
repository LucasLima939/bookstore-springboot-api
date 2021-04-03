package aplicacao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aplicacao.exception.BibliotecaException;
import aplicacao.exception.LivroSemEstoqueException;
import aplicacao.model.Cadastro;
import aplicacao.model.CadastroLivro;
import aplicacao.model.Endereco;
import aplicacao.model.Locacao;
import aplicacao.model.StatusLocacao;
import aplicacao.repository.CadastroLivroRepositorio;
import aplicacao.repository.CadastroRepositorio;
import aplicacao.repository.LocacaoRepositorio;
import aplicacao.service.CadastroLivroService;
import aplicacao.service.CadastroService;
import aplicacao.service.LocacaoService;
import aplicacao.service.ViaCepService;


@Component
public class AplicacaoSimplesSpring {
	
    @Autowired
	private CadastroService cadastroService;
	
	@Autowired
	private CadastroLivroService cadastroLivroService;
	
	@Autowired
	private LocacaoService locacaoService;
	
	public void cadastrarUsuario(Cadastro usuario, String cep, String numero){
		if(usuario != null)
			cadastroService.criarUsuario(usuario, 
					cep,
					numero
					); // CEP
	}
	
//	public void editarUsuario(Cadastro usuario) {
//		if(usuario != null && usuario.getId() != null)
//			cadastroService.editarUsuario(usuario);
//	}
	
	
}


