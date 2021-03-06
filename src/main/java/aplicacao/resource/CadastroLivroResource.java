package aplicacao.resource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import aplicacao.model.CadastroLivro;
import aplicacao.model.ErrorResponse;
import aplicacao.model.Sessao;
import aplicacao.service.CadastroLivroService;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@RestController
@RequestMapping("/livros")
public class CadastroLivroResource {
	 
    	private Validator validator;
	
		public CadastroLivroResource()
	    {
	        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
	        validator = validatorFactory.getValidator();
	    }
	
		@Autowired
		private CadastroLivroService cadastroLivroService;
		
		@PostMapping
		public ResponseEntity cadastroLivro(@RequestBody CadastroLivro livro) {
			try {
				CadastroLivro livroCadastrado = cadastroLivroService.criarLivro(livro);
			    return new ResponseEntity<>(livroCadastrado, HttpStatus.CREATED); 			
			}catch(HttpClientErrorException e) {
				return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());
			}
		}
		
		@GetMapping
		public ResponseEntity recuperarTodosOsLivros() {			
			try {
				List<CadastroLivro> livrosCadastrado = cadastroLivroService.recuperarTodosLivros();
			    return new ResponseEntity<>(livrosCadastrado, HttpStatus.OK); 			
			}catch(HttpClientErrorException e) {
				return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());
			}
		}
		
		@GetMapping(path = "/{id}")
		public ResponseEntity get(@PathVariable("id") Integer id) {								
			try {
				CadastroLivro livro = cadastroLivroService.recuperarLivro(id);
			    return new ResponseEntity<>(livro, HttpStatus.OK); 			
			}catch(HttpClientErrorException e) {
				return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());
			}
		}
		
		@PutMapping(path = "/{id}")
		public ResponseEntity editar(@PathVariable("id") Integer id, @RequestBody CadastroLivro livro) {
			try {
				CadastroLivro cadastroLivro = cadastroLivroService.editarLivro(livro, id);
			    return new ResponseEntity<>(cadastroLivro, HttpStatus.OK); 		
			}catch(HttpClientErrorException e) {
				return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());
			}
		}
		
		@DeleteMapping(path = "/{id}")
		public ResponseEntity deletar(@PathVariable("id") Integer id) {
			try {
				cadastroLivroService.deleteLivro(id);
				return new ResponseEntity<>("Livro deletado com sucesso", HttpStatus.OK);
			} catch(HttpClientErrorException e) {
				return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());
			}
		}
		
	
	}

