package aplicacao.resource;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.google.gson.Gson;

import aplicacao.model.Cadastro;
import aplicacao.model.CadastroLivro;
import aplicacao.model.ErrorResponse;
import aplicacao.model.JwtToken;
import aplicacao.model.LivroLocacao;
import aplicacao.model.Locacao;
import aplicacao.service.CadastroService;
import aplicacao.service.LocacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.jfr.BooleanFlag;

@RestController
@RequestMapping("/locacao")
@Api(value = "Locação", description = "Realiza locação", tags = { "Locação" })
public class LocacaoResource {
	
	@Autowired
	private LocacaoService locacaoService;
	
	@Autowired
	private CadastroService service;
	
	@PostMapping
	@ApiOperation(value="Agendar locação", tags = { "Locação" })
	public ResponseEntity agendarLocacao(@RequestHeader (name="Authorization") String token, @RequestBody Locacao locacao) throws Exception {	
		try {	
		    return new ResponseEntity<>(locacaoService.criarLocacao(tokenToCadastro(token), locacao), HttpStatus.OK); 			
		}catch(HttpClientErrorException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());			
		}	
	}
	
	@GetMapping(path = "/{id}")
	@ApiOperation(value="Pesquisar locação", tags = { "Locação" })
	public ResponseEntity recuperarLocacao(@RequestHeader(name="Authorization") String token, @PathVariable("id") Integer id) {
		try {
		    return new ResponseEntity<>(locacaoService.localizarLocacao(id, tokenToCadastro(token)), HttpStatus.OK); 			
		}catch(HttpClientErrorException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());			
		}
	}
	
	@PostMapping(path = "/{id}/reservar")
	@ApiOperation(value="Reservar livros", tags = { "Locação" })
	public ResponseEntity reservarLivros(@RequestHeader (name="Authorization") String token, @RequestBody List<LivroLocacao> livros, @PathVariable("id") Integer id) {
		try {
		    return new ResponseEntity<>(locacaoService.retirarLivros(id, tokenToCadastro(token), livros), HttpStatus.OK); 			
		}catch(HttpClientErrorException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());			
		}
	}
	
	@PostMapping(path = "/{id}/devolver")
	@ApiOperation(value="Devolver livros", tags = { "Locação" })
	public ResponseEntity devolverLivros(@RequestHeader (name="Authorization") String token, @RequestBody List<Integer> livrosIds, @PathVariable("id") Integer id) {
		try {
		    return new ResponseEntity<>(locacaoService.entregarLivros(id, tokenToCadastro(token), livrosIds), HttpStatus.OK); 			
		}catch(HttpClientErrorException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());			
		}
	}
	
	private Cadastro tokenToCadastro(String token) {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		JwtToken jwtPayload = new Gson().fromJson(payload, JwtToken.class);
		return service.recuperarUsuarioPorLogin(jwtPayload.getSub());		
	}

}
