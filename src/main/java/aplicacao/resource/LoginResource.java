package aplicacao.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aplicacao.model.Login;
import aplicacao.model.Sessao;
import aplicacao.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginResource {
	@Autowired
	private LoginService loginService;
	
	@PostMapping
	public Sessao login(@RequestBody Login login) {
		return loginService.logar(login);
			
	}

}
