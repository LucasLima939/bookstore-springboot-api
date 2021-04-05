package aplicacao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "tab_locacao")
public class Locacao {
	
	public Locacao(Cadastro cadastro, Date dataAgendamento){
		this.idCadastro = cadastro.getId();
		this.dataAgendamento = dataAgendamento;
	}
	
	public Locacao(Date dataAgendamento){
		this.dataAgendamento = dataAgendamento;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(readOnly = true)	
	private Integer id;
	
	@Column
    private Integer idCadastro;
	
	@Embedded
	@ApiModelProperty(readOnly = true)
	@Column
    private List<LivroLocacao> livros = new ArrayList<LivroLocacao>();

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataAgendamento = new Date(System.currentTimeMillis());

	@ApiModelProperty(readOnly = true)	
	@Temporal(TemporalType.DATE)
	@Column
	private Date dataFinalizacao;

	@ApiModelProperty(readOnly = true)	
	@Column
	private Double valorTotal = 0.0;

	@Enumerated(EnumType.STRING)
	@ApiModelProperty(readOnly = true)	
	@Column
	private StatusLocacao status = StatusLocacao.RESERVADA;
	
	@Transient
	private List<Integer> livrosIds;
	
	public void inserirLivros(List<LivroLocacao> livros){
		this.livros.addAll(livros);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StatusLocacao getStatus() {
		return status;
	}

	public void setStatus(StatusLocacao status) {
		this.status = status;
	}

	public List<Integer> getLivrosIds() {
		return livrosIds;
	}

	public void setLivrosIds(List<Integer> livrosIds) {
		this.livrosIds = livrosIds;
	}

	public Integer getCadastro() {
		return idCadastro;
	}

	public void setCadastro(Integer cadastro) {
		this.idCadastro = cadastro;
	}

	public List<LivroLocacao> getLivros() {
		return livros;
	}

	public void setLivros(List<LivroLocacao> livros) {
		this.livros = livros;
	}
	
	public Date getDataAgendamento() {
		return dataAgendamento;
	}
	
	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}
	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}
	
	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	
	public Double getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Locacao()
	{}
	
	
}
