package aplicacao.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tab_cadastro")

//@NamedEntityGraph(name = "Cadastro.telefones")
public class Cadastro {
	public Cadastro(
			String name,
			String cpf,
			String email,
			String login,
			String senha
			){
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.login = login;
		this.senha = senha;
	}
	
	public Cadastro(){}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 50, nullable = false)
	private String name;

	@Column(length = 11,nullable = false,unique = true)
	private String cpf;

	@Column(length = 50,nullable = false,unique = true)
	private String email;

	/* @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cadastro")
	private List<Telefone> telefones = new ArrayList<Telefone>();; */
	@Column(length = 50)
	private String telefone;

	@Column(length = 20,nullable = false, unique = true)
	private String login;

	@Column(length = 50,nullable = false)
	private String senha; 
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Endereco endereco;

	/* @ElementCollection(fetch = FetchType.LAZY)
	private List<String> emails = new ArrayList<String>(); */

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return this.endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
