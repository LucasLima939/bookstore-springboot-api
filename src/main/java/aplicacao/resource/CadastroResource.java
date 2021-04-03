package aplicacao.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aplicacao.model.Cadastro;
import aplicacao.model.Sessao;
import aplicacao.service.CadastroService;

@RestController
@RequestMapping("/cadastro")
public class CadastroResource {
	@Autowired
	private CadastroService service;
	
	@PostMapping
	public Sessao cadastro(@RequestBody Cadastro cadastro) {
		return service.cadastrarUsuario(cadastro);
	}

}
