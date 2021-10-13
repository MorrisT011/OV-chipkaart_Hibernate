package daohibernate;

import Domain.Adres;
import Domain.Reiziger;
import interfaces.ReizigerDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            Transaction trans = session.beginTransaction();

            session.save(reiziger);

            trans.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            Transaction trans = session.beginTransaction();

            session.update(reiziger);

            trans.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            Transaction trans = session.beginTransaction();

            session.delete(reiziger);

            trans.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public Reiziger findById(Long id) {
        try {
            Reiziger reiziger = session.get(Reiziger.class, id);

            return reiziger;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            // Veel gedoe om deze goed te krijgen, ging het uiteindelijk dus om '' bij de datum
            List<Reiziger> reizigerList = session.createQuery("FROM Reiziger WHERE geboortedatum = '" + datum + "'", Reiziger.class).list();

            return reizigerList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    @Override
    public List<Reiziger> findAll() {
        try {
            List<Reiziger> reizigerList = session.createQuery("FROM Reiziger", Reiziger.class).list();

            return reizigerList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
