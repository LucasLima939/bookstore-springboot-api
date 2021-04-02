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
        	
        	application.cadastrarUsuario(new Cadastro(
    				"Time04", // NOME
    				"1111111112", //CPF
    				"example@example.com.br", //EMAIL
    				"time04", // LOGIN
    				"01001111 01001000"), //SENHA
    				"50771-140", // CEP
    				"680" // NUMERO
        			);
        	
        };
    }

}
