package aplicacao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

@Embeddable
public class LivroLocacao {
	
	@ApiModelProperty(name = "livroId")
	@NotNull(message = "Insira o ID do livro que você deseja locar")
	@Column
	private Integer id;
	
	@NotNull(message = "Insira a QUANTIDADE do livro que você deseja locar")
	@Column
	private Integer quantidade;

	@ApiModelProperty(readOnly = true)
	@Column
	private double valorLocacao;	

	@ApiModelProperty(readOnly = true)	
	@Temporal(TemporalType.DATE)
	@Column
	private Date dataRetirada = new Date(System.currentTimeMillis());

	public Date getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	@ApiModelProperty(readOnly = true)	
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public double getValorLocacao() {
		return valorLocacao;
	}

	public void setValorLocacao(double valorLocacao) {
		this.valorLocacao = valorLocacao;
	}

}
