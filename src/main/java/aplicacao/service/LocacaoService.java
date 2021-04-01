package aplicacao.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aplicacao.exception.BibliotecaException;
import aplicacao.exception.LivroSemEstoqueException;
import aplicacao.model.Cadastro;
import aplicacao.model.CadastroLivro;
import aplicacao.model.Locacao;
import aplicacao.model.StatusLocacao;
import aplicacao.repository.LocacaoRepositorio;

@Component
public class LocacaoService {

	
	@Autowired
	private LocacaoRepositorio locacaoRepository;
	
	
	public Locacao agendarLivro (Locacao locacao) throws Exception{
		Cadastro cadastro = locacao.getCadastro();
		List<CadastroLivro> livros = locacao.getLivros();
		if (cadastro == null) {
            throw new BibliotecaException("Não é possivel realizar locação sem um cliente");
        }
        if (livros == null) {
            throw new BibliotecaException("Nenhum livro foi selecionado");
        }
        for (int i = 0; i < livros.size(); i++) {
			CadastroLivro livro = livros.get(i);
			if (livro.getNumeroExemplares() == 0) {
				throw new LivroSemEstoqueException("Livro sem estoque");
			}
			livro.setNumeroExemplares(livro.getNumeroExemplares() - 1);
			livro.setNumeroExemplaresReservados(livro.getNumeroExemplaresReservados() + 1);
		}
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
		LocalDate retiradaConvert = locacao.getDataRetirada().toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		Double numeroDiarias = (double) ChronoUnit.DAYS.between(retiradaConvert, hoje);
		for(int i = 0; i < livros.size(); i++) {
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
