package Domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {

    @Id
    private int kaart_nummer;

    private Date geldig_tot;
    private int klasse;
    private float saldo;


    @ManyToOne
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;


    @ManyToMany(mappedBy = "OVChipkaarten")
    private List<Product> producten = new ArrayList<>();



    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, float saldo) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
    }

    public OVChipkaart() {

    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
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
        return "OVChipkaart: " +
                "kaart_nummer = " + kaart_nummer +
                ", geldig_tot = " + geldig_tot +
                ", klasse = " + klasse +
                ", saldo = " + saldo +
                ", reiziger_id = " + reiziger.getId();
    }
}
