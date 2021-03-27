package aplicacao;

import java.util.ArrayList;
import java.util.List;

import model.Cadastro;
import model.Telefone;
import repository.CadastroRepositorio;

public class AplicacaoSimples {

    static CadastroRepositorio cadastroRepositorio = new CadastroRepositorio();
	
	public static void main(String[] args) {
		cadastrarUsuario();
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
        CadastroRepositorio repositorio = new CadastroRepositorio();

        repositorio.create(cadastro);
        
		repositorio.fecharConexao();
    }

    private static void recuperarUsuario(){
        Cadastro cadastro = cadastroRepositorio.getCadastro(1);
        if(cadastro != null){
            System.out.println("Usuário " + cadastro.getName());
        } else {
            System.out.println("Usuário não cadastrado!");
        }
    }

    /* recuperarTodosUsuários(){
        List<Cadastro> cadastros = cadastroRepositorio.getAllCadastros();
        cadastros.forEach((u){
            System.out.println(u.getName());
        });
    } */

}
