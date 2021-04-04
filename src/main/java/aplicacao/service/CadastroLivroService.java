package aplicacao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import aplicacao.model.CadastroLivro;
import aplicacao.repository.CadastroLivroRepositorio;
import aplicacao.repository.LocacaoRepositorio;

@Service
public class CadastroLivroService {
	

	private Validator validator;

	public CadastroLivroService()
    {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

	@Autowired
	private CadastroLivroRepositorio livroRepository;
	
	@Autowired
	private LocacaoRepositorio locacaoRepository;
	
	
	public CadastroLivro criarLivro(CadastroLivro livro) {
	    Set<ConstraintViolation<CadastroLivro>> violations = validator.validate(livro);
	    if(!violations.isEmpty()) {
			String s = violations.stream().map(e -> e.getMessage()).collect(Collectors.joining(" ; "));
	    	throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, s);
	    }
		try {
	        return livroRepository.save(livro);			
		}catch(Exception e) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao cadastrar livro");	
			
		}
	}
	
	public void deleteLivro(Integer id) {
		try {
			livroRepository.deleteById(id);
		} catch (Exception e) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Livro não localizado, operação não realizada");				
		}
	}
	
	public CadastroLivro recuperarLivro(Integer id){
		CadastroLivro livro = livroRepository.findById(id).orElse(null);
		if(livro == null)
				throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Livro não localizado");	
		return livro;
	}
	
	private Boolean livroExiste(Integer id) {
		return livroRepository.existsById(id);		
	}
	
	public CadastroLivro editarLivro(CadastroLivro livro, Integer id){
		if(livroRepository.existsById(id)) {
			return criarLivro(livro);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Livro não localizado");	
		}
	}
	
	public List<CadastroLivro> recuperarTodosLivros() {
		List<CadastroLivro> todosLivros = new ArrayList<CadastroLivro>();
		try {
			livroRepository.findAll().forEach(todosLivros::add);
		}catch(Exception e) {
			throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao recuperar livros");			
		}
		return todosLivros;
	}
	
	public List<CadastroLivro> recuperarLivrosPorListaId(List<Integer> ids){
		List<CadastroLivro> todosLivros = new ArrayList<CadastroLivro>();
		livroRepository.findAllById(ids).forEach(todosLivros::add);
		return todosLivros;
	}

}
