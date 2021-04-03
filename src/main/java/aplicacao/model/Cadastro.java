package aplicacao.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

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
		this.login = new Login(login, senha);
	}
	
	public Cadastro(){}

	@ApiModelProperty(hidden = true)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ApiModelProperty(name = "nome")	
	@Column(length = 50, nullable = false)
	private String name;
	
	//@Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres") 
	@Column(length = 11,nullable = false,unique = true)
	private String cpf;
	
	
	@Column(length = 50,nullable = false,unique = true)
	private String email;

	/* @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cadastro")
	private List<Telefone> telefones = new ArrayList<Telefone>();; */
	@Column(length = 50)
	private String telefone;

	@Embedded
	private Login login;
	
	@ApiModelProperty(hidden = true,readOnly = true)
	@OneToOne(cascade = {CascadeType.ALL})
	private Endereco endereco;
	
	private String cep;
	
	@ApiModelProperty(name = "enderecoNumero")	
	private String numero;

	/* @ElementCollection(fetch = FetchType.LAZY)
	private List<String> emails = new ArrayList<String>(); */

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Login getLogin() {
		return this.login;
	}

	public void setLogin(Login login) {
		this.login = login;
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
