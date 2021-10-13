import Domain.Adres;
import Domain.OVChipkaart;
import Domain.Product;
import Domain.Reiziger;
import daohibernate.AdresDAOHibernate;
import daohibernate.OVChipkaartDAOHibernate;
import daohibernate.ProductDAOHibernate;
import daohibernate.ReizigerDAOHibernate;
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
//        testManytoMany();
        test1();

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

        Reiziger reiziger = new Reiziger(418L, "M.", "", "Tular", Date.valueOf("1999-09-03"));
        Adres adres = new Adres(333L, "5482LE", "21", "Voortstraat", "Schijndel");

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

        Reiziger reiziger = new Reiziger(419L, "M.", "", "Tular", Date.valueOf("1999-09-03"));
        OVChipkaart ovChipkaart1 = new OVChipkaart(11111, Date.valueOf("2022-01-01"), 2, 0.0F);
        OVChipkaart ovChipkaart2 = new OVChipkaart(22222, Date.valueOf("2022-01-01"), 2, 10.0F);

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
        Transaction trans = session.beginTransaction();


        Reiziger reiziger = new Reiziger(420L ,"M.", "", "Tular", Date.valueOf("1999-09-03"));

        OVChipkaart ovChipkaart1 = new OVChipkaart(88888, Date.valueOf("2022-01-01"), 2, 0.0F);
        OVChipkaart ovChipkaart2 = new OVChipkaart(99999, Date.valueOf("2024-01-01"), 1, 10.0F);

        Product product1 = new Product(10L, "Test1", "Test1", 5.0F);
        Product product2 = new Product(20L, "Test2", "Test2", 15.0F);

        ovChipkaart1.setReiziger(reiziger);
        ovChipkaart2.setReiziger(reiziger);

        ovChipkaart1.addProduct(product1);
        ovChipkaart1.addProduct(product2);

        ovChipkaart2.addProduct(product1);
        ovChipkaart2.addProduct(product2);


        List<OVChipkaart> ovChipkaarts = new ArrayList<>();
        ovChipkaarts.add(ovChipkaart1); ovChipkaarts.add(ovChipkaart1); ovChipkaarts.add(ovChipkaart1);

        reiziger.setOVChipkaarten(ovChipkaarts);

        session.save(reiziger);
        session.save(ovChipkaart1);
        session.save(ovChipkaart2);
        session.save(product1);
        session.save(product2);

        trans.commit();

        session.close();
    }



    private static void test1() {
        AdresDAOHibernate adao = new AdresDAOHibernate(getSession());
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(getSession());
        OVChipkaartDAOHibernate odao = new OVChipkaartDAOHibernate(getSession());
        ProductDAOHibernate pdao = new ProductDAOHibernate(getSession());

        Reiziger reiziger = new Reiziger(421L, "M", "", "Tular", Date.valueOf("1999-09-03"));
        Adres adres = new Adres(444L, "5482LE", "21", "Hoofdstraat", "Schijndel");
        adres.setReiziger(reiziger);

        OVChipkaart ovChip = new OVChipkaart(66666, Date.valueOf("2023-01-01"), 1, 42.00F);
        ovChip.setReiziger(reiziger);

        Product product = new Product(66666L, "Test", "Test product voor DP", 7.50F);
        ovChip.addProduct(product);

        Reiziger rzgr = new Reiziger(1L, "G", "van", "Rijn", Date.valueOf("2002-09-17"));
        Reiziger rzg = new Reiziger(2L,"B", "van", "Rijn", Date.valueOf("2002-10-22"));
        rzgr.setId(1L);
        rzgr.setId(2L);

        System.out.println("REIZIGER TESTS:");
        System.out.println("\nSave & findAll:");
        for (Reiziger r : rdao.findAll()){
            System.out.println(r);
        }
        System.out.println();
        rdao.save(reiziger);
        for (Reiziger r : rdao.findAll()){
            System.out.println(r);
        }

        System.out.println("\nUpdate & findById:");
        reiziger.setAchternaam("TEST");
        rdao.update(reiziger);
        System.out.println(rdao.findById(reiziger.getId()));

        System.out.println("\nfindByGbD:");
        for (Reiziger r : rdao.findByGbdatum("2002-12-03")){
            System.out.println(r);
        }


        System.out.println();
        System.out.println("ADRES TESTS:");
        System.out.println("\nSave & findAll:");

        for (Adres a : adao.findAll()) {
            System.out.println(a);
        }
        System.out.println();
        adao.save(adres);
        for (Adres a : adao.findAll()) {
            System.out.println(a);
        }

        System.out.println("\nUpdate & findByReiziger:");
        adres.setPostcode("6666AA");
        adao.update(adres);

        System.out.println(adao.findByReiziger(reiziger));
        System.out.println();


        System.out.println("PRODUCT TEST:");
        System.out.println("\nSave & findAll:");
        for (Product p : pdao.findAll()){
            System.out.println(p);
        }
        odao.save(ovChip);
        pdao.save(product);
        System.out.println();
        for (Product p : pdao.findAll()){
            System.out.println(p);
        }

        System.out.println("\nUpdate:");
        product.setBeschrijving("De beschrijving is veranderd");
        pdao.update(product);

        for (Product p : pdao.findAll()) {
            if (p == product) {
                System.out.println(p);
            }
        }


        System.out.println("\nOVCHIPKAART TEST:");
        System.out.println("\nSave & findAll:");
        for (OVChipkaart o : odao.findAll()){
            System.out.println(o);
        }
        System.out.println();

        for (OVChipkaart o : odao.findAll()){
            System.out.println(o);
        }
        System.out.println("\nUpdate + findByReiziger: ");
        ovChip.setSaldo(999.99F);
        odao.update(ovChip);

        System.out.println(odao.findByReiziger(reiziger));

        System.out.println("\nPRODUCT test findByOvchipkaart: ");
        System.out.println(pdao.findByOVChipkaart(ovChip));

        System.out.println();
        System.out.println("DELETE ALL");
        pdao.delete(product);

        odao.delete(ovChip);

        adao.delete(adres);

        rdao.delete(reiziger);
        System.out.println("Kijk of reiziger nog bestaat:");
        System.out.println(rdao.findById(reiziger.getId()));

    }
}