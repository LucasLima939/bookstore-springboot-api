package aplicacao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import aplicacao.model.CadastroLivro;
import aplicacao.model.LivroLocacao;
import aplicacao.model.Locacao;

@SpringBootApplication
public class AplicacaoSpring {
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(AplicacaoSpring.class, args);
	}
	
	@Bean
    public CommandLineRunner run(AplicacaoSimplesSpring application) throws Exception {
        return args -> {        	
        	
//        	application.cadastrarUsuario(new Cadastro(
//    				"Time3", // NOME
//    				"12345678923", //CPF
//    				"example3@example.com", //EMAIL
//    				"time3", // LOGIN
//    				"01001111 01001001"), //SENHA
//    				"01001-001", // CEP
//    				"123" // NUMERO
//        			);
        	
//        	application.cadastrarLivro(new CadastroLivro(
//        			"3323456",
//        			"Desmistificando Node JS",
//        			15.0,
//        			9
//        			));
        	
        	
        	List<LivroLocacao> livrosLocacao = new ArrayList();
        
        	List<CadastroLivro> livros = application.getLivros();
        	
        	livros.forEach(livro ->
        	{livrosLocacao.add(new LivroLocacao(livro, 1));});
        	application.cadastrarLocacao(new Locacao(
        			application.getCadastro(1),
        			new Date(2020-03-04)
        			), livrosLocacao);
     
        	
        };
    }

}
