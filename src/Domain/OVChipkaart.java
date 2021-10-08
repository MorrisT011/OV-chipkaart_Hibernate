package Domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int kaart_nummer;

    private Date geldig_tot;
    private int klasse;
    private float saldo;


    @ManyToOne
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;


    @ManyToMany(mappedBy = "OVChipkaarten")
    private List<Product> producten = new ArrayList<>();



    public OVChipkaart(Date geldig_tot, int klasse, float saldo) {
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public OVChipkaart() {

    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public float getSaldo() {
        return saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public boolean addProduct(Product product) {
        for (Product p : producten) {
            if (p.getProduct_nummer() == product.getProduct_nummer()) {
                return false;
            }
        }
        product.addOVChipkaart(this);
        producten.add(product);
        return true;
    }

    public boolean deleteProduct(Product product) {
        for (Product p : producten) {
            if (p.getProduct_nummer() == product.getProduct_nummer()) {
                product.deleteOVChipkaart(this);
                producten.remove(product);
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Domain.OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", geldig_tot=" + geldig_tot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger_id=" + reiziger;
    }
}
