package aplicacao.model;

import java.util.Date;

public class Sessao {
	
	public Sessao(String login,
			Date dataInicio,
			Date dataFim) {
		this.login = login;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}
	
	
	private String login;
	private String token;
	private Date dataInicio;
	private Date dataFim;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	

}
