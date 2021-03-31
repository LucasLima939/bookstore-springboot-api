package aplicacao;


import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import aplicacao.model.Cadastro;
import aplicacao.model.CadastroLivro;
import aplicacao.model.Endereco;
import aplicacao.model.Locacao;

@SpringBootApplication
public class AplicacaoSpring {
	
	public static void main(String[] args) {
		SpringApplication.run(AplicacaoSpring.class, args);
	}
	
	@Bean
    public CommandLineRunner run(AplicacaoSimplesSpring application) throws Exception {
        return args -> {
//        	application.criarUsuario(new Cadastro(
//				"Lucas03",
//				"22222222224", 
//				"teste@teste3",
//				"teste04",
//				"123457",
//				new Endereco(
//						"60000-000")));
        	
//        	Cadastro usuario = application.recuperarUsuario(3);
//        	if(usuario != null) {
//        		usuario.setName("TESTANDO2");
//        		Cadastro response = application.editarUsuario(usuario);
//        		System.out.println(usuario.getName());     
        	//}
        	
//        	application.criarLivro(new CadastroLivro(
//        			"1234456",
//        			"Livro 1",
//        			20.00,
//        			5));
        	
//        	CadastroLivro livro = application.recuperarLivro(1);
//        	if(livro != null)
//        		System.out.println(livro.getTitulo());
        	
        	Cadastro cadastro = application.recuperarUsuario(1);
        	Locacao loc = new Locacao(cadastro, application.recuperarTodosLivros());
        	application.agendarLivro(loc);
        };
    }

}
