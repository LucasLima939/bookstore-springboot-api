package aplicacao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aplicacao.exception.BibliotecaException;
import aplicacao.exception.LivroSemEstoqueException;
import aplicacao.model.Cadastro;
import aplicacao.model.CadastroLivro;
import aplicacao.model.Endereco;
import aplicacao.model.Locacao;
import aplicacao.model.StatusLocacao;
import aplicacao.repository.CadastroLivroRepositorio;
import aplicacao.repository.CadastroRepositorio;
import aplicacao.repository.LocacaoRepositorio;


@Component
public class AplicacaoSimplesSpring {
	
	@Autowired
	private CadastroLivroRepositorio livroRepository;
	
	@Autowired
	private CadastroRepositorio cadastroRepository;
	
	@Autowired
	private LocacaoRepositorio locacaoRepository;
	
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
	
	public CadastroLivro criarLivro(CadastroLivro livro) {
        return livroRepository.save(livro);		
	}
	
	public CadastroLivro recuperarLivro(Integer id){
		return livroRepository.findById(id).orElse(null);
	}
	
	public List<CadastroLivro> recuperarTodosLivros() {
		List<CadastroLivro> todosLivros = new ArrayList<CadastroLivro>();
		livroRepository.findAll().forEach(todosLivros::add);
		return todosLivros;
	}
	
	public List<CadastroLivro> recuperarLivrosPorListaId(List<Integer> ids){
		List<CadastroLivro> todosLivros = new ArrayList<CadastroLivro>();
		livroRepository.findAllById(ids).forEach(todosLivros::add);
		return todosLivros;
	}
	
	public Locacao agendarLivro (Locacao locacao) throws Exception{
		Cadastro cadastro = locacao.getCadastro();
		List<CadastroLivro> livros = locacao.getLivros();
		if (cadastro == null) {
            throw new BibliotecaException("Impossivel locar sem um cliente");
        }
        if (livros == null) {
            throw new BibliotecaException("Nenhum livro foi selecionado");
        }
        for (int i = 0; i < livros.size(); i++) {
			CadastroLivro cadastroLivro = livros.get(i);
			if (cadastroLivro.getNumeroExemplares() == 0) {
				throw new LivroSemEstoqueException("Livro sem estoque");
			}
			cadastroLivro.setNumeroExemplares(cadastroLivro.getNumeroExemplares() - 1);
			cadastroLivro.setNumeroExemplaresReservados(cadastroLivro.getNumeroExemplaresReservados() + 1);
		}
        Locacao loc = locacaoRepository.save(locacao);
        return loc;
	}
	
}


