package aplicacao.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aplicacao.model.CadastroLivro;
import aplicacao.repository.CadastroLivroRepositorio;
import aplicacao.repository.LocacaoRepositorio;

@Component
public class CadastroLivroService {

	@Autowired
	private CadastroLivroRepositorio livroRepository;
	
	@Autowired
	private LocacaoRepositorio locacaoRepository;
	
	
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

}
