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
	    Set<ConstraintViolation<Cadastro>> violations = validator.validate(usuario);
	    if(!violations.isEmpty()) {
			String s = violations.stream().map(e -> e.getMessage()).collect(Collectors.joining(" ; "));
	    	throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, s);
	    }
	    Cadastro cadastro = criarUsuario(usuario, usuario.getCep(), usuario.getNumero());
		

		return loginService.iniciarSessao(cadastro.getLogin().getLogin());
	}
	
	public Cadastro criarUsuario(Cadastro usuario, String cep, String numero) {
		ViaCepModel model = null;
		if(cep != null && !cep.isEmpty()) {
			model = viaCepService.getModelByCep(cep);
			} else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "cep inválido");
			}
		if(model != null) {
			usuario.setEndereco(new Endereco(model, numero));
			String senha = null;
			try {
				senha = encoder.encode(usuario.getLogin().getSenha());				
			
				usuario.getLogin().setSenha(senha);
				return cadastroRepository.save(usuario);
			}catch (Exception e) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "falha ao cadastrar usuário");	
			}
        } else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "erro ao recuperar endereço");
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
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "erro ao recuperar usuário, gere um novo token");
		return cadastro;
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
