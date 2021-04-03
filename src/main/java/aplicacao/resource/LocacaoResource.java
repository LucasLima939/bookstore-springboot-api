package aplicacao.resource;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aplicacao.model.LivroLocacao;
import aplicacao.model.Locacao;
import aplicacao.service.LocacaoService;

@RestController
@RequestMapping("/locacao")
public class LocacaoResource {
	
	private LocacaoService locacaoService = new LocacaoService();
	
	public Locacao agendarLocacao(@RequestBody Locacao locacao, List<LivroLocacao> livrosLocacao) throws Exception {
		return locacaoService.agendarLivro(locacao, livrosLocacao);		
	}

}
