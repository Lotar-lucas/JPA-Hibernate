package aplicacao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import dominio.Pessoa;

public class Programa {

	public static void main(String[] args) {

//		// Cria a fábrica de EntityManager (única para todas as operações)
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		// Cria um EntityManager (única instância para todas as operações)
		EntityManager em = emf.createEntityManager();
//
////  //PERSISTENCE -----------------------------------------------------
//		Pessoa p1 = new Pessoa(null, "Carlos da Silva", "carlos@gmail.com");
//		Pessoa p2 = new Pessoa(null, "Joaquim Torres", "joaquim@gmail.com");
//		Pessoa p3 = new Pessoa(null, "Ana Maria", "ana@gmail.com");
//
//		// Inicia uma transação no banco de dados
  //		em.getTransaction().begin();
//
//		// Persiste o objeto p1,p2,p3 no banco de dados (INSERT)
  //		em.persist(p1);
  //		em.persist(p2);
  //		em.persist(p3);
//
//		// Confirma a transação, efetivando todas as operações no banco de dados
//		  em.getTransaction().commit();
//
//		System.out.println("Dados inseridos com sucesso!");

    // GET ---------------------------------------------------------------------
    // Buscar pessoa com ID 2 (usando o MESMO EntityManager)
//        Pessoa pessoa = em.find(Pessoa.class, 20);
//        System.out.println("Pessoa encontrada: " + pessoa);

    //DELETE ---------------------------------------------------------------------
    // Busca a pessoa com ID 2 no banco de dados (usando o MESMO EntityManager)
      Pessoa pessoa = em.find(Pessoa.class, 20);

    // Inicia uma transação
      em.getTransaction().begin();

    // Remove a pessoa do banco de dados (DELETE)
      em.remove(pessoa);

    // Confirma a transação, efetivando a remoção no banco de dados
      em.getTransaction().commit();

      System.out.println("Pessoa deletada com sucesso!");

		// Fecha o EntityManager e libera os recursos associados (NO FINAL de todas as operações)
		  em.close();
		// Fecha a EntityManagerFactory e libera os recursos associados
		  emf.close();
	}
}
