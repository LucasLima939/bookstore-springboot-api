package aplicacao.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import aplicacao.model.ErrorResponse;
import aplicacao.model.Login;
import aplicacao.model.Sessao;
import aplicacao.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginResource {
	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public ResponseEntity login(@RequestBody Login login) {
		try { 
			Sessao sessao = loginService.logar(login);
		    return new ResponseEntity<>(sessao, HttpStatus.OK); 			
		}catch(HttpClientErrorException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());
		}
	}
			
	
}
