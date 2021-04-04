package aplicacao.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

@Embeddable
public class Login {
	public Login(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	public Login() {}
	
	@NotEmpty(message = "O campo LOGIN não pode ser vazio")
	private String login;

	@NotEmpty(message = "O campo SENHA não pode ser vazio")
	@JsonProperty(access = Access.WRITE_ONLY)	
	private String senha;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
