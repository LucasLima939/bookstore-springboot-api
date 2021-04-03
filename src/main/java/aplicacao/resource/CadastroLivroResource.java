package aplicacao.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aplicacao.model.Cadastro;
import aplicacao.model.CadastroLivro;
import aplicacao.model.Login;
import aplicacao.model.Sessao;
import aplicacao.service.CadastroLivroService;


@RestController
@RequestMapping("/cadastro-livro")
public class CadastroLivroResource {
	
		@Autowired
		private CadastroLivroService cadastroLivroService;
		
		@PostMapping
		public CadastroLivro cadastroUsuario(@RequestBody CadastroLivro livro) {
			return cadastroLivroService.criarLivro(livro);
				
		}

	}

