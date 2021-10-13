package daohibernate;

import Domain.Adres;
import Domain.Reiziger;
import interfaces.AdresDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            Transaction trans = session.beginTransaction();

            session.save(adres);

            trans.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

    }

    @Override
    public boolean update(Adres adres) {
        try {
            Transaction trans = session.beginTransaction();

            session.update(adres);

            trans.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            Transaction trans = session.beginTransaction();

            session.delete(adres);

            trans.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            Adres adres = session.createQuery("FROM Adres WHERE reiziger_id = " + reiziger.getId(), Adres.class).getSingleResult();
            return adres;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public List<Adres> findAll() {
        try {
            List<Adres> adresList = session.createQuery("FROM Adres", Adres.class).list();

            return adresList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
