package interfaces;

import Domain.OVChipkaart;
import Domain.Product;
import Domain.Reiziger;

import java.util.List;

public interface ProductDAO {
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    List<Product> findAll();
}
