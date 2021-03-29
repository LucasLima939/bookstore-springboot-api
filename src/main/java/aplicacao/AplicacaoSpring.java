package aplicacao;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import aplicacao.model.Cadastro;
import aplicacao.model.Endereco;

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
        	
        	Cadastro usuario = application.recuperarUsuario(3);
        	if(usuario != null) {
        		usuario.setName("TESTANDO2");
        		Cadastro response = application.editarUsuario(usuario);
        		System.out.println(response.getName());        		
        	}
        };
    }

}
