package aplicacao.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

	public Locacao agendarLivro(Locacao locacao, List<LivroLocacao> livrosLocacao) throws Exception {
		Cadastro cadastro = cadastroService.recuperarUsuario(locacao.getCadastro().getId());
		if (cadastro == null) {
			throw new BibliotecaException("Não é possivel realizar locação sem um cliente");
		}
		List<CadastroLivro> livros = new ArrayList();
		livrosLocacao.forEach(livroLocacao -> {
			livroLocacao.quantidadeLivro().forEach(livro -> {
				livros.add(livro);
			});
		});
		if (livros == null) {
			throw new BibliotecaException("Nenhum livro foi selecionado");
		}
		for (int i = 0; i < livrosLocacao.size(); i++) {
			CadastroLivro livro = livros.get(i);
			if (livro.getNumeroExemplares() == 0) {
				throw new LivroSemEstoqueException("Livro sem estoque");
			}
			livro.setNumeroExemplares(livro.getNumeroExemplares() - 1);
			livro.setNumeroExemplaresReservados(livro.getNumeroExemplaresReservados() + 1);
		}
		locacao.setCadastro(cadastro);
		locacao.setLivros(livros);
		locacao.setDataAgendamento(new Date());
		return locacaoRepository.save(locacao);
	}

	public Locacao retirarLivros(Locacao locacao) {
		StatusLocacao status = StatusLocacao.EFETIVADA;
		locacao.setDataRetirada(new Date());
		return locacaoRepository.save(locacao);
	}

	public Locacao finalizarLocacao(Locacao locacao) {
		List<CadastroLivro> livros = locacao.getLivros();
		double valorTotal = 0;
		LocalDate hoje = LocalDate.now();
		LocalDate retiradaConvert = locacao.getDataRetirada().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Double numeroDiarias = (double) ChronoUnit.DAYS.between(retiradaConvert, hoje);
		for (int i = 0; i < livros.size(); i++) {
			CadastroLivro livro = livros.get(i);
			double valorLocacao = numeroDiarias * livro.getValorDiaria();
			valorTotal += valorLocacao;
			livro.setNumeroExemplares(livro.getNumeroExemplares() + 1);
			livro.setNumeroExemplaresReservados(livro.getNumeroExemplaresReservados() - 1);
		}
		locacao.setDataFinalizacao(new Date());
		StatusLocacao status = StatusLocacao.FINALIZADA;
		locacao.setValorTotal(valorTotal);
		return locacaoRepository.save(locacao);
	}

}
