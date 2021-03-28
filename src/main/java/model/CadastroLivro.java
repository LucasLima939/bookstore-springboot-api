package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tab_livro")
public class CadastroLivro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String isbn;
	
	@Column(nullable = false)
	private String titulo;
	
	@Column(length = 64, nullable = false)
	private Double valorDiaria;
	
	@Column(length = 32, nullable = false)
	private Integer numeroExemplares;
	
	@Column(length = 32)
	private Integer numeroExemplaresReservados;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	

}
