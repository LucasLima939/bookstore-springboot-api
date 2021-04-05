package aplicacao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@Entity
@Table(name = "tab_livro_locacao")
public class LivroLocacao {
	

    @ManyToOne
    @JsonIgnore
    private Locacao locacao;	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

	@JsonProperty("livro_id")
	@NotNull(message = "Insira o ID do livro que você deseja locar")
	@Column
	private Integer livroId;
	
	@NotNull(message = "Insira a QUANTIDADE do livro que você deseja locar")
	@Column
	private Integer quantidade;

	@ApiModelProperty(readOnly = true)
	@Column
	private double valorLocacao;

	@JsonProperty("livros")
	@ApiModelProperty(hidden = true,readOnly = true)
	@ManyToOne(cascade = {CascadeType.ALL})
	private CadastroLivro cadastroLivro;

	@ApiModelProperty(readOnly = true)	
	@Temporal(TemporalType.DATE)
	@Column
	private Date dataRetirada = new Date(System.currentTimeMillis());

	@ApiModelProperty(readOnly = true)	
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;
	
	

	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

	public CadastroLivro getCadastroLivro() {
		return cadastroLivro;
	}

	public void setCadastroLivro(CadastroLivro cadastroLivro) {
		this.cadastroLivro = cadastroLivro;
	}

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


	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public double getValorLocacao() {
		return valorLocacao;
	}

	public void setValorLocacao(double valorLocacao) {
		this.valorLocacao = valorLocacao;
	}

}
