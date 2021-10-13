package daohibernate;

import Domain.Adres;
import Domain.OVChipkaart;
import Domain.Reiziger;
import interfaces.OVChipkaartDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        try {
            Transaction trans = session.beginTransaction();

            session.save(ovChipkaart);

            trans.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) {
        try {
            Transaction trans = session.beginTransaction();

            session.update(ovChipkaart);

            trans.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) {
        try {
            Transaction trans = session.beginTransaction();

            session.delete(ovChipkaart);

            trans.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            List<OVChipkaart> ovChipkaartList = session.createQuery("FROM OVChipkaart WHERE reiziger_id = " + reiziger.getId(), OVChipkaart.class).list();

            return ovChipkaartList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        try {
            List<OVChipkaart> ovChipkaartList = session.createQuery("FROM OVChipkaart ", OVChipkaart.class).list();

            return ovChipkaartList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
