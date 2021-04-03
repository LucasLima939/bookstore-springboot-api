package aplicacao.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class LivroLocacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;

	private CadastroLivro livro;
	
	private Integer quantidade;
	
	public LivroLocacao (CadastroLivro livro, Integer quantidade) {
		this.livro = livro;
		this.quantidade = quantidade;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CadastroLivro getLivro() {
		return livro;
	}

	public void setLivro(CadastroLivro livro) {
		this.livro = livro;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public List<CadastroLivro> quantidadeLivro() {
		List <CadastroLivro> livros = new ArrayList<CadastroLivro>();
		for (int i = 0; i <= this.quantidade; i ++) {
			livros.add(this.livro);
		}
		return livros;
	}

}
