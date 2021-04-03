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
        	
        };
    }

}
