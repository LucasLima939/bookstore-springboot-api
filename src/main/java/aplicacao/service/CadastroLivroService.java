package aplicacao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import aplicacao.model.CadastroLivro;
import aplicacao.model.LivroLocacao;
import aplicacao.repository.CadastroLivroRepositorio;
import aplicacao.repository.LocacaoRepositorio;

@Service
public class CadastroLivroService {

	@Autowired
	private CadastroLivroRepositorio livroRepository;
	
	@Autowired
	private LocacaoRepositorio locacaoRepository;
	
	
	public CadastroLivro criarLivro(CadastroLivro livro) {
		try {
	        return livroRepository.save(livro);			
		}catch(Exception e) {
			System.out.println(e);
			throw new RuntimeException("erro ao cadastrar livro");	 
			
		}
	}
	
	public CadastroLivro recuperarLivro(Integer id){
		CadastroLivro livro = livroRepository.findById(id).orElse(null);
		if(livro == null)
				throw new NoSuchElementException("livro não encontrado");	
		return livro;
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
	
	public List<CadastroLivro> LivrosLocacaoConvert(List<Integer> ids){
		List<LivroLocacao> todosLivrosLocacao = new ArrayList<LivroLocacao>();
		List<CadastroLivro> todosLivros = livroRepository.findAllById(ids).forEach(todosLivrosLocacao::add);
		return todosLivros;
	}

}
