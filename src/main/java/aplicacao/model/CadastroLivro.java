package aplicacao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tab_cadastro_livro")
public class CadastroLivro {
	
	CadastroLivro(String isbn, String titulo, double valorDiaria, int numeroExemplares){
		this.isbn = isbn;
		this.titulo = titulo;
		this.valorDiaria = valorDiaria;
		this.numeroExemplares = numeroExemplares;		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(length = 50, nullable = false)
	@NotNull @NotEmpty
	private String isbn;
	
	@Column(length = 100)
	@NotNull @NotEmpty
	private String titulo;
	
	@NotNull @DecimalMin(value = "0.0", inclusive = false)
	private Double valorDiaria;
	
	@NotNull @NotEmpty
	private int numeroExemplares;
	
	private int numeroExemplaresReservados;
	
	public CadastroLivro(String isbn, String titulo, Double valorDiaria, int numeroExemplares) {
		this.isbn = isbn;
		this.titulo = titulo;
		this.valorDiaria = valorDiaria;
		this.numeroExemplares = numeroExemplares;
	}
	

	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public Double getValorDiaria() {
		return valorDiaria;
	}
	
	public void setValorDiaria(Double valorDiaria) {
		this.valorDiaria = valorDiaria;
	}
	
	public int getNumeroExemplares() {
		return numeroExemplares;
	}
	
	public void setNumeroExemplares(Integer numeroExemplares) {
		this.numeroExemplares = numeroExemplares;
	}
	
	public int getNumeroExemplaresReservados() {
		return numeroExemplaresReservados;
	}
	
	public void setNumeroExemplaresReservados(Integer numeroExemplaresReservados) {
		this.numeroExemplaresReservados = numeroExemplaresReservados;
	}
	
	public CadastroLivro()
	{}
	
	

}
