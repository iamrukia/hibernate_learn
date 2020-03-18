package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JpaGoodTest {
    private static EntityManagerFactory entityManagerFactory;
    public static void main(String[] args) {
        //setup

        // like discussed with regards to SessionFactory, an EntityManagerFactory is set up once for an application
        // 		IMPORTANT: notice how the name here matches the name we gave the persistence-unit in persistence.xml!
        entityManagerFactory = Persistence.createEntityManagerFactory( "org.hibernate.tutorial.jpa" );



        // create a couple of events...
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist( new Good( "a new world awaits") );
        entityManager.persist( new Good( "a new world awaits" ) );
        entityManager.getTransaction().commit();
        entityManager.close();

        // now lets pull events from the database and list them
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Good> result = entityManager.createQuery( "from Good", Good.class ).getResultList();
        for ( Good good : result ) {
            System.out.println( "Good (" +  good.getTitle() );
        }
        entityManager.getTransaction().commit();
        entityManager.close();







        //tear down
        entityManagerFactory.close();
    }
}
