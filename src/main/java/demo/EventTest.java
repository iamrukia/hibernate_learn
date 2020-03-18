package demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


import java.time.LocalDateTime;
import java.util.List;


public class EventTest {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        //Setup

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }

        // create a couple of events...
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new Event("Our very first event!"));
        session.save(new Event("A follow up event"));
        session.getTransaction().commit();
        session.close();

        // now lets pull events from the database and list them
        session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Good").list();
        for (Event event : (List<Event>) result) {
            System.out.println("Event (" + event.getTitle());
        }
        session.getTransaction().commit();
        session.close();


        //Tear down
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

}