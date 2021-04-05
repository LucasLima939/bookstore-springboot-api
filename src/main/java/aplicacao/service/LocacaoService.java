package aplicacao.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import aplicacao.exception.BibliotecaException;
import aplicacao.exception.LivroSemEstoqueException;
import aplicacao.model.Cadastro;
import aplicacao.model.CadastroLivro;
import aplicacao.model.LivroLocacao;
import aplicacao.model.Locacao;
import aplicacao.model.StatusLocacao;
import aplicacao.repository.LocacaoRepositorio;

@Service
public class LocacaoService {

	@Autowired
	private LocacaoRepositorio locacaoRepository;

	@Autowired
	private CadastroService cadastroService;

	@Autowired
	private CadastroLivroService cadastroLivroService;
	 
	private Validator validator;

	public LocacaoService()
    {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
	

	public Locacao criarLocacao(Cadastro cadastro, Locacao locacao) throws Exception {
		try {
			locacao.setIdCadastro(cadastro.getId());
			if(!locacao.getLivros().isEmpty()) {
				validarLivros(locacao.getLivros());
				verificarDisponibilidadeLivros(locacao.getLivros());
				atualizarValorLocacao(locacao);
			}
			return locacaoRepository.save(locacao);			
		}catch(Exception e) {
	    	throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao agendar locação");			
		}
	}
	
	private void validarLivros(List<LivroLocacao> livros){
		Set<ConstraintViolation<LivroLocacao>> violations = new HashSet<ConstraintViolation<LivroLocacao>>();
		for(LivroLocacao l:livros) {
			violations.addAll(validator.validate(l));
			if(l.getCadastroLivro() == null) {
				CadastroLivro cadastroLivro = cadastroLivroService.recuperarLivro(l.getLivroId());
				l.setCadastroLivro(cadastroLivro);		
				l.setValorLocacao(cadastroLivro.getValorDiaria());
			}
		}
	    if(!violations.isEmpty()) {
			String s = violations.stream().map(e -> e.getMessage()).collect(Collectors.joining(" ; "));
	    	throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, s);
	    }
		
	}

	
	private void validarLocacao(Locacao locacao){
	    Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
	    if(!violations.isEmpty()) {
			String s = violations.stream().map(e -> e.getMessage()).collect(Collectors.joining(" ; "));
	    	throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, s);
	    }
	}
	
	public Locacao localizarLocacao(Integer id, Cadastro cadastro) {
		Locacao locacao = locacaoRepository.findById(id).orElse(null);
		if(locacao == null)
	    	throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Locação não localizada");
		if(locacao.getIdCadastro() != cadastro.getId())
	    	throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Você não possui autorização para alterar essa locação");
		return locacao;		
	}

	public Locacao retirarLivros(Integer locacaoId, Cadastro cadastro, List<LivroLocacao> livros) {
		if(livros.isEmpty())
	    	throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Insira livros na lista");
		Locacao locacao = localizarLocacao(locacaoId, cadastro);
			
		validarLivros(livros);
		verificarDisponibilidadeLivros(livros);
				
		locacao.setStatus(StatusLocacao.EFETIVADA);
		locacao.inserirLivros(livros);
		atualizarValorLocacao(locacao);
		return salvarLocacao(locacao);
	}
	
	private void verificarDisponibilidadeLivros(List<LivroLocacao> livros) {
		List<Integer> livrosIds = livros.stream().map(l -> l.getLivroId()).collect(Collectors.toList());
		List<CadastroLivro> cadastroLivros = cadastroLivroService.recuperarLivrosPorListaId(livrosIds);
		for(LivroLocacao l:livros) {
			CadastroLivro cadastroLivro = cadastroLivros.stream().filter(livro -> livro.getId().equals(l.getLivroId())).findFirst().orElse(null);
			boolean podeLocar = l.getQuantidade() <= cadastroLivro.getNumeroExemplaresDisponivel();
			if(podeLocar) {
				alterarDisponibilidadeLivroReserva(cadastroLivro, l);
			} else {
		    	throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Quantidade de livros exede a disponível para o livro: " + l.getLivroId());				
			}
		}
	}
	
	private void alterarDisponibilidadeLivroReserva(CadastroLivro cadastroLivro, LivroLocacao l) {
		Integer valorAtualizadoExemplaresReservado = cadastroLivro.getNumeroExemplaresReservados() + l.getQuantidade();
		Integer valorAtualizadoExemplaresDisponivel = cadastroLivro.getNumeroExemplaresDisponivel() - l.getQuantidade();
			cadastroLivro.setNumeroExemplaresReservados(valorAtualizadoExemplaresReservado);
			cadastroLivro.setNumeroExemplaresDisponivel(valorAtualizadoExemplaresDisponivel);
		cadastroLivroService.editarLivro(cadastroLivro, cadastroLivro.getId());		
	}
	
	private void alterarDisponibilidadeLivroDevolucao(CadastroLivro cadastroLivro, LivroLocacao l) {
		Integer valorAtualizadoExemplaresReservado = cadastroLivro.getNumeroExemplaresReservados() - l.getQuantidade();
		Integer valorAtualizadoExemplaresDisponivel = cadastroLivro.getNumeroExemplaresDisponivel() + l.getQuantidade();
			cadastroLivro.setNumeroExemplaresReservados(valorAtualizadoExemplaresReservado);
			cadastroLivro.setNumeroExemplaresDisponivel(valorAtualizadoExemplaresDisponivel);
		cadastroLivroService.editarLivro(cadastroLivro, cadastroLivro.getId());		
	}


	private Locacao salvarLocacao(Locacao locacao) {
		try {
			return locacaoRepository.save(locacao);			
		}catch(Exception e) {
	    	throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Falha ao atualizar locação");			
		}
	}
	
	public String entregarLivros(Integer locacaoId, Cadastro cadastro, List<Integer> livrosIds) {
		Locacao locacao = localizarLocacao(locacaoId, cadastro);
		locacao.getLivros().forEach(livro -> {
			if(livrosIds.contains(livro.getLivroId())) {
				livro.setDataEntrega(new Date(System.currentTimeMillis()));
				CadastroLivro cadastroLivro = cadastroLivroService.recuperarLivro(livro.getLivroId());
				alterarDisponibilidadeLivroDevolucao(cadastroLivro, livro);
			}
		});
		atualizarValorLocacao(locacao);
		if(!verificarLivrosPendentes(locacao)) {
			return finalizarLocacao(locacao);
		} else {
			salvarLocacao(locacao);
			return "Livro(s) entregues!";
		}
	}
	
	private void atualizarValorLocacao(Locacao locacao) {
		double valorTotal = 0.0;
		for(LivroLocacao l:locacao.getLivros()) {
			if(l.getDataEntrega() != null) {
			LocalDate hoje = LocalDate.now();
			LocalDate retiradaConvert = l.getDataRetirada().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			Double numeroDiarias = (double) ChronoUnit.DAYS.between(retiradaConvert, hoje) + 1;
			double valorLocacaoLivro = numeroDiarias * l.getValorLocacao();
			valorTotal =+ valorLocacaoLivro;
		}
		locacao.setValorTotal(valorTotal);
		
	}
		
	}
	
	private Boolean verificarLivrosPendentes(Locacao locacao) {
		Boolean temPendencias = false;
		
		for(LivroLocacao l:locacao.getLivros()) {
			if(l.getDataEntrega() == null) {
				temPendencias = true;
			}
			
		}
		return temPendencias;
	}

	public String finalizarLocacao(Locacao locacao) {
		atualizarValorLocacao(locacao);
		locacao.setDataFinalizacao(new Date());
		StatusLocacao status = StatusLocacao.FINALIZADA;
		salvarLocacao(locacao);
		return "Locacão finalizada com sucesso!";	}

}
