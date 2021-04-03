package aplicacao.resource;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import aplicacao.model.Cadastro;
import aplicacao.model.CadastroLivro;
import aplicacao.model.ErrorResponse;
import aplicacao.model.JwtToken;
import aplicacao.model.LivroLocacao;
import aplicacao.model.Locacao;
import aplicacao.service.CadastroService;
import aplicacao.service.LocacaoService;

@RestController
@RequestMapping("/locacao")
public class LocacaoResource {
	
	@Autowired
	private LocacaoService locacaoService;
	
	@Autowired
	private CadastroService service;
	
	@PostMapping
	public ResponseEntity agendarLocacao(@RequestHeader (name="Authorization") String token, Locacao locacao, List<Integer> ids) throws Exception {
		
	try {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		JwtToken jwtPayload = new Gson().fromJson(payload, JwtToken.class);
		Cadastro cadastro = service.recuperarUsuarioPorLogin(jwtPayload.getSub());
		
	    return new ResponseEntity<>(locacaoService.agendarLivro(cadastro, locacao, ids), HttpStatus.OK); 			
	}catch(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);			
	}
	
			
	}

}
