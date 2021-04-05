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
import org.springframework.web.client.HttpClientErrorException;

import com.google.gson.Gson;

import aplicacao.model.Cadastro;
import aplicacao.model.ErrorResponse;
import aplicacao.model.JwtToken;
import aplicacao.model.ViaCepModel;
import aplicacao.service.CadastroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/usuarios")
@Api(value = "Usuario", description = "Consulta usuario", tags = { "Usuario" })
public class UsuarioResource {

	@Autowired
	private CadastroService service;
	
//	@PutMapping
//	public ResponseEntity editarUsuario(@RequestBody Cadastro cadastro) {
//		
//	}
	
	@GetMapping(path = "/me")
	@ApiOperation(value="Consultar usuario", tags = { "Usuario" })
	public ResponseEntity get(@RequestHeader (name="Authorization") String token) {
		try {
			Cadastro cadastro = service.recuperarUsuarioPorLogin(recoverLoginFromToken(token));
		    return new ResponseEntity<>(cadastro, HttpStatus.OK); 			
		}catch(HttpClientErrorException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());			
		}
	}
	

	@PutMapping(path = "/me")
	@ApiOperation(value="Editar usuario", tags = { "Usuario" })
	public ResponseEntity editar(@RequestHeader (name="Authorization") String token, @RequestBody Cadastro cadastro) {
		try {
			Cadastro usuario = service.editarUsuarioLogado(recoverLoginFromToken(token), cadastro);
		    return new ResponseEntity<>(usuario, HttpStatus.OK); 			
		}catch(HttpClientErrorException e) {
			return new ResponseEntity<>(new ErrorResponse(e.getMessage()), e.getStatusCode());			
		}		
	}
	
	private String recoverLoginFromToken(String token) {
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		JwtToken jwtPayload = new Gson().fromJson(payload, JwtToken.class);
		return jwtPayload.getSub();		
	}
}
