package aplicacao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import aplicacao.model.Cadastro;
import aplicacao.model.CadastroLivro;
import aplicacao.model.Endereco;
import aplicacao.model.Sessao;
import aplicacao.model.ViaCepModel;
import aplicacao.repository.CadastroLivroRepositorio;
import aplicacao.repository.CadastroRepositorio;
import aplicacao.repository.LocacaoRepositorio;

@Service
public class CadastroService {
	
	@Autowired
	private CadastroRepositorio cadastroRepository;
	
	@Autowired
	private ViaCepService viaCepService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private PasswordEncoder encoder;
	 
	private Validator validator;

	public CadastroService()
    {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
	
	public Sessao cadastrarUsuario(Cadastro usuario) {
		validarUsuario(usuario);
	    Cadastro cadastro = criarUsuario(usuario, usuario.getCep(), usuario.getNumero());
		

		return loginService.iniciarSessao(cadastro.getLogin().getLogin());
	}
	
	public Cadastro editarUsuario(Cadastro usuario, Integer id) {
		if(cadastroRepository.existsById(id)) {
			usuario.setId(id);
			usuario.setEndereco(recuperarEnderecoViaCep(usuario.getCep(), usuario.getNumero()));
			validarUsuario(usuario);
			return salvarUsuario(usuario);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Usuário não localizado");	
		}
	}
	
	private void validarUsuario(Cadastro usuario){
	    Set<ConstraintViolation<Cadastro>> violations = validator.validate(usuario);
	    if(!violations.isEmpty()) {
			String s = violations.stream().map(e -> e.getMessage()).collect(Collectors.joining(" ; "));
	    	throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, s);
	    }
	}
	
	public Cadastro criarUsuario(Cadastro usuario, String cep, String numero) {
		usuario.setEndereco(recuperarEnderecoViaCep(cep, numero));
		String senha = null;
		senha = encoder.encode(usuario.getLogin().getSenha());				
		if(senha == null)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Erro ao processar senha");
		usuario.getLogin().setSenha(senha);
		
		return salvarUsuario(usuario);
	}
	
	private Endereco recuperarEnderecoViaCep(String cep, String numero) {
		ViaCepModel model = null;
		if(cep == null || cep.isEmpty())
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "cep inválido");
		model = viaCepService.getModelByCep(cep);
		if(model == null)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Erro ao recuperar endereço via CEP");
		return new Endereco(model, numero);
	}
	
	private Cadastro salvarUsuario(Cadastro usuario) {
		try {
			return cadastroRepository.save(usuario);
		}catch (Exception e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "falha ao cadastrar usuário");	
		}
	}
	
	public Cadastro recuperarUsuario(Integer id){
		Cadastro cadastro = cadastroRepository.findById(id).orElse(null);
		if(cadastro == null)
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "usuário não localizado");
		return cadastro;
	}
	
	public Cadastro recuperarUsuarioPorLogin(String login){
		Cadastro cadastro = cadastroRepository.findByLoginLogin(login);
		if(cadastro == null)
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "erro ao recuperar usuário");
		return cadastro;
	}
	
	public Cadastro editarUsuarioLogado(String login, Cadastro cadastro){
		Cadastro usuario = recuperarUsuarioPorLogin(login);
		return editarUsuario(cadastro, usuario.getId());
		
	}
	
	public List<Cadastro> recuperarTodosUsuarios() {
		List<Cadastro> todosUsuarios = new ArrayList<Cadastro>();
		try {
			cadastroRepository.findAll().forEach(todosUsuarios::add);
		}catch(Exception e) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao recuperar livros");			
		}
		return todosUsuarios;
	}
	
	public List<Cadastro> recuperarUsuariosPorListaId(List<Integer> ids){
		List<Cadastro> todosUsuarios = new ArrayList<Cadastro>();
		try {
			cadastroRepository.findAllById(ids).forEach(todosUsuarios::add);
		}catch(Exception e) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao recuperar livros");			
		}
		return todosUsuarios;
	}
	
	public void deletarUsuario(Integer id) {
		try {
			cadastroRepository.deleteById(id);
		} catch (Exception e) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "erro ao recuperar usuário, gere um novo token");
		}
	}
	
}
