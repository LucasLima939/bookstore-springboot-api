package aplicacao;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

public class AplicacaoSpring {
	
	public static void main(String[] args) {
		SpringApplication.run(AplicacaoSpring.class, args);
	}
	
//	@Bean
//    public CommandLineRunner run(AplicacaoSimplesSpring application) throws Exception {
//        return args -> {
//        	//application.criarUsuario();
//        };
//    }

}
