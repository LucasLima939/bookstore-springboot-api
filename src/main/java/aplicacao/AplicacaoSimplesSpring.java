package aplicacao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aplicacao.model.Cadastro;
import aplicacao.model.Endereco;
import aplicacao.repository.CadastroRepositorio;


@Component
public class AplicacaoSimplesSpring {
	
	@Autowired
	private CadastroRepositorio cadastroRepository;
	
	public Cadastro criarUsuario(Cadastro usuario) {
        return cadastroRepository.save(usuario);		
	}
	
	public Cadastro recuperarUsuario(Integer id){
		return cadastroRepository.findById(id).orElse(null);
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
	
	public Cadastro editarUsuario(Cadastro cadastro){
		return cadastroRepository.save(cadastro);
	}
	
	public Boolean deletarUsuario(Integer id) {
		try {
		cadastroRepository.deleteById(id);
		return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	
}

