package aplicacao.resource;

import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import aplicacao.model.Cadastro;
import aplicacao.model.ErrorResponse;
import aplicacao.model.Sessao;
import aplicacao.service.CadastroService;

@RestController
@RequestMapping("/cadastro")
public class CadastroResource {
	@Autowired
	private CadastroService service;
	
	@PostMapping
	public ResponseEntity cadastro(@Valid @RequestBody Cadastro cadastro) {
		try {
			Sessao sessao = service.cadastrarUsuario(cadastro);
		    return new ResponseEntity<>(sessao, HttpStatus.OK); 			
		}catch(Exception e) {
	        ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
