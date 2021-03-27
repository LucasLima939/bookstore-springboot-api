package model;

public class CadastroLivro {
	
	private Integer id;
	private String isbn;
	private String titulo;
	private Double valorDiaria;
	private int numeroExemplares;
	private int numeroExemplaresReservados;
	
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
	
	public void setNumeroExemplares(int numeroExemplares) {
		this.numeroExemplares = numeroExemplares;
	}
	
	public int getNumeroExemplaresReservados() {
		return numeroExemplaresReservados;
	}
	
	public void setNumeroExemplaresReservados(int numeroExemplaresReservados) {
		this.numeroExemplaresReservados = numeroExemplaresReservados;
	}
	
	

}
