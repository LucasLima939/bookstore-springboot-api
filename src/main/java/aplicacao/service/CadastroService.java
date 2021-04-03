package aplicacao.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import aplicacao.model.Cadastro;
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
	
	public Sessao cadastrarUsuario(Cadastro usuario) {
		Cadastro cadastro = null;
		cadastro = criarUsuario(usuario, usuario.getCep(), usuario.getNumero());
		

		return loginService.iniciarSessao(cadastro.getLogin().getLogin());
	}
	
	public Cadastro criarUsuario(Cadastro usuario, String cep, String numero) {
		ViaCepModel model = null;
		if(cep != null && !cep.isEmpty()) {
			model = viaCepService.getModelByCep(cep);
			} else {
				throw new RuntimeException("cep inválido");
			}
		if(model != null) {
			usuario.setEndereco(new Endereco(model, numero));
			String senha = null;
			try {
				senha = encoder.encode(usuario.getLogin().getSenha());				
			
				usuario.getLogin().setSenha(senha);
				return cadastroRepository.save(usuario);
			}catch (Exception e) {
				throw new RuntimeException("falha ao cadastrar usuário, verifique os dados e tente novamente");				
			}
        } else {
			throw new RuntimeException("erro ao recuperar endereço");
        }
	}
	
	public Cadastro recuperarUsuario(Integer id){
		Cadastro cadastro = cadastroRepository.findById(id).orElse(null);
		if(cadastro == null)
			throw new RuntimeException("erro ao recuperar usuário");
		return cadastro;
	}
	
	public Cadastro recuperarUsuarioPorLogin(String login){
		Cadastro cadastro = cadastroRepository.findByLoginLogin(login);
		if(cadastro == null)
			throw new RuntimeException("erro ao recuperar usuário, gere um novo token");
		return cadastro;
	}
	
	public List<Cadastro> recuperarTodosUsuarios() {
		List<Cadastro> todosUsuarios = new ArrayList<Cadastro>();
		cadastroRepository.findAll().forEach(todosUsuarios::add);
		return todosUsuarios;
	}
	
	public List<Cadastro> recuperarUsuariosPorListaId(List<Integer> ids){
		List<Cadastro> todosUsuarios = new ArrayList<Cadastro>();
		cadastroRepository.findAllById(ids).forEach(todosUsuarios::add);
		return todosUsuarios;
	}
	
//	public Cadastro editarUsuario(Cadastro cadastro, Integer id){
//		if(cadastroRepository.findById(id).orElse(null) == null)
//			throw new RuntimeErrorException("Usuário não localizado");
//		return cadastroRepository.save(cadastro);
//	}
	
	public Boolean deletarUsuario(Integer id) {
		try {
		cadastroRepository.deleteById(id);
		return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
}
