package aplicacao.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import aplicacao.model.CadastroLivro;
import aplicacao.service.CadastroLivroService;

@RestController
@RequestMapping("/cadastro-livro")
public class CadastroLivroResource {
	
		@Autowired
		private CadastroLivroService cadastroLivroService;
		
		@PostMapping
		public CadastroLivro cadastroLivro(@RequestBody @Valid CadastroLivro livro) {
			return cadastroLivroService.criarLivro(livro);				
		}
		
		@GetMapping
		public Iterable<CadastroLivro> get() {
			return cadastroLivroService.recuperarTodosLivros();
		}
		
		@GetMapping(path = "/find/{id}")
		public CadastroLivro get(@PathVariable("id") Integer id) {
			return cadastroLivroService.recuperarLivro(id);
		}
		
	
	}

