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
        	
//        	application.cadastrarUsuario(new Cadastro(
//    				"Time05", // NOME
//    				"1111111113", //CPF
//    				"example@example.com.", //EMAIL
//    				"time05", // LOGIN
//    				"123456"), //SENHA
//    				"50771-140", // CEP
//    				"680" // NUMERO
//        			);
        	
        };
    }

}
