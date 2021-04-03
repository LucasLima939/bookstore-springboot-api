package aplicacao.resource;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import aplicacao.model.Cadastro;
import aplicacao.model.ErrorResponse;
import aplicacao.model.JwtToken;
import aplicacao.model.ViaCepModel;
import aplicacao.service.CadastroService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

	@Autowired
	private CadastroService service;
	
//	@PutMapping
//	public ResponseEntity editarUsuario(@RequestBody Cadastro cadastro) {
//		
//	}
	
	@GetMapping(path = "/me")
	public ResponseEntity get(@RequestHeader (name="Authorization") String token) {
		try {
			String[] chunks = token.split("\\.");
			Base64.Decoder decoder = Base64.getDecoder();
			String payload = new String(decoder.decode(chunks[1]));
			JwtToken jwtPayload = new Gson().fromJson(payload, JwtToken.class);
			Cadastro cadastro = service.recuperarUsuarioPorLogin(jwtPayload.getSub());
		    return new ResponseEntity<>(cadastro, HttpStatus.OK); 			
		}catch(Exception e) {
	        ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage(e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
}
