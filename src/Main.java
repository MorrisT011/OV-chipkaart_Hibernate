import Domain.Adres;
import Domain.OVChipkaart;
import Domain.Product;
import Domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
//        testFetchAll();
//        testOneToOne();
//        testOneToMany();
        testManytoMany();

    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testOneToOne() {
        Session session = getSession();

        Reiziger reiziger = new Reiziger("M.", "", "Tular", Date.valueOf("1999-09-03"));
        Adres adres = new Adres("5482LE", "21", "Voortstraat", "Schijndel");

        Transaction trans = session.beginTransaction();

        session.save(reiziger);
        trans.commit();

        adres.setReiziger(reiziger);

        Transaction trans2 = session.beginTransaction();
        session.save(adres);
        trans2.commit();

        session.close();
    }

    private static void testOneToMany() {
        Session session = getSession();

        Reiziger reiziger = new Reiziger("M.", "", "Tular", Date.valueOf("1999-09-03"));
        OVChipkaart ovChipkaart1 = new OVChipkaart(Date.valueOf("2022-01-01"), 2, 0.0F);
        OVChipkaart ovChipkaart2 = new OVChipkaart(Date.valueOf("2022-01-01"), 2, 10.0F);

        List<OVChipkaart> kaarten = new ArrayList<>();
        kaarten.add(ovChipkaart1);
        kaarten.add(ovChipkaart2);

        reiziger.setOVChipkaarten(kaarten);

        Transaction trans = session.beginTransaction();

        session.save(reiziger);
        trans.commit();

        session.close();
    }

    private static void testManytoMany() {
        Session session = getSession();

        Reiziger reiziger = new Reiziger("M.", "", "Tular", Date.valueOf("1999-09-03"));

        OVChipkaart ovChipkaart1 = new OVChipkaart(Date.valueOf("2022-01-01"), 2, 0.0F);
        ovChipkaart1.setReiziger(reiziger);
        OVChipkaart ovChipkaart2 = new OVChipkaart(Date.valueOf("2024-01-01"), 1, 10.0F);
        ovChipkaart2.setReiziger(reiziger);

        Product product1 = new Product("Test1", "Test1", 5.0F);
        Product product2 = new Product("Test2", "Test2", 15.0F);

        List<Product> products = new ArrayList<>();
        products.add(product1); products.add(product2);

        List<OVChipkaart> ovChipkaarts = new ArrayList<>();
        ovChipkaarts.add(ovChipkaart1); ovChipkaarts.add(ovChipkaart1); ovChipkaarts.add(ovChipkaart1);

        reiziger.setOVChipkaarten(ovChipkaarts);

        session.save(reiziger);


        for (OVChipkaart o : ovChipkaarts) {
            Transaction trans = session.beginTransaction();
            session.save(o);
            trans.commit();
        }

        for (Product p : products) {
            Transaction trans2 = session.beginTransaction();
            session.save(p);
            trans2.commit();
        }

        session.close();
    }
}