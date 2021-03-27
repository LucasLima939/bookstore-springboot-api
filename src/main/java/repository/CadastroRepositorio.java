package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Cadastro;

public class CadastroRepositorio {
    private EntityManager entityManager;

    public CadastroRepositorio() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MY_PU");
        entityManager = factory.createEntityManager();
        
    }

    public void create(Cadastro cadastro){
        entityManager.getTransaction().begin();
        entityManager.persist(cadastro);
        entityManager.getTransaction().commit();
    }

    public void update(Cadastro cadastro){
        entityManager.getTransaction().begin();
        entityManager.merge(cadastro);
        entityManager.getTransaction().commit();
    }

    public Cadastro getCadastro(Integer id) {
        return entityManager.find(Cadastro.class, id);
    }

    public List<Cadastro> getAllCadastros(){
        Query query = entityManager.createQuery("SELECT e FROM Produto e");
        return query.getResultList();
    }

    public void fecharConexao() {
        entityManager.clear();
        entityManager.close();
    }
    

}
