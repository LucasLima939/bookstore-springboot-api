package aplicacao.resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aplicacao.model.Login;

@RestController
@RequestMapping("/login")
public class LoginResource {
	
	@PostMapping
	public void login(@RequestBody Login login) {
		
	}

}
