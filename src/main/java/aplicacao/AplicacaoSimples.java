package aplicacao;

import java.util.ArrayList;
import java.util.List;

import model.Cadastro;
import model.Telefone;
import repository.CadastroRepositorio;

public class AplicacaoSimples {

    static CadastroRepositorio cadastroRepositorio = new CadastroRepositorio();
	
	public static void main(String[] args) {
		recuperarTodosUsuários();
        System.exit(0);

	}

    private static void cadastrarUsuario(){
        Cadastro cadastro = new Cadastro();
        cadastro.setEmail("teste@teste2");
        cadastro.setCpf("22222222223");
        cadastro.setName("Lucas02");
        cadastro.setLogin("teste03");
        cadastro.setSenha("123456");
        cadastro.setTelefone("99999999");

        cadastroRepositorio.create(cadastro);
        
    }

    private static void recuperarUsuario(){
        Cadastro cadastro = cadastroRepositorio.getCadastro(1);
        if(cadastro != null){
            System.out.println("Usuário " + cadastro.getName());
        } else {
            System.out.println("Usuário não cadastrado!");
        }
    }

    private static void recuperarTodosUsuários(){
        List<Cadastro> cadastros = cadastroRepositorio.getAllCadastros();
        for(Cadastro c : cadastros){
            System.out.println(c.getName());
        }
    }

}
