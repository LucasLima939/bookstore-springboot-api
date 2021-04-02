package aplicacao.model;

import javax.persistence.Embeddable;

@Embeddable
public class Login {
	public Login(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	public Login() {}
	
	private String login;
	
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
