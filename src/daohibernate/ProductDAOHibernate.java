package daohibernate;

import Domain.Adres;
import Domain.OVChipkaart;
import Domain.Product;
import interfaces.ProductDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        try {
            Transaction trans = session.beginTransaction();

            session.save(product);

            trans.commit();

            return true;

        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        try {
            Transaction trans = session.beginTransaction();

            session.update(product);

            trans.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try {
            Transaction trans = session.beginTransaction();

            session.delete(product);

            trans.commit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        try {

            List<Product> allProductsList = session.createQuery("FROM Product", Product.class).list();
            List<Product> neededProductsList = new ArrayList<>();

            for (Product p : allProductsList) {
                if (p.getOVChipkaarten().contains(ovChipkaart)){
                    neededProductsList.add(p);
                }
            }

            return neededProductsList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            List<Product> productList = session.createQuery("FROM Product ", Product.class).list();

            return productList;

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
