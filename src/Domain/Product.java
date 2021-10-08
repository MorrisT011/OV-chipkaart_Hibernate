package Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long product_nummer;
    private String naam;
    private String beschrijving;
    private float prijs;


    @ManyToMany
    @JoinTable(
            name = "ov_chipkaart_product",
            joinColumns = @JoinColumn(name = "product_nummer"),
            inverseJoinColumns = @JoinColumn(name = "kaart_nummer")
    )
    private List<OVChipkaart> OVChipkaarten = new ArrayList<>();




    public Product(String naam, String beschrijving, float prijs) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Product() {

    }

    public Long getProduct_nummer() {
        return product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public float getPrijs() {
        return prijs;
    }

    public List<OVChipkaart> getOVChipkaarten() {
        return OVChipkaarten;
    }

    public boolean addOVChipkaart(OVChipkaart ovChipkaart) {
        for (OVChipkaart ov : OVChipkaarten) {
            if (ov.getKaart_nummer() == ovChipkaart.getKaart_nummer()){
                return false;
            }
        }

        OVChipkaarten.add(ovChipkaart);
        return true;
    }

    public boolean deleteOVChipkaart(OVChipkaart ovChipkaart) {
        for (OVChipkaart ov : OVChipkaarten) {
            if (ov.getKaart_nummer() == ovChipkaart.getKaart_nummer()) {
                OVChipkaarten.remove(ovChipkaart);
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Domain.Product: " +
                "product_nummer = " + product_nummer +
                ", naam = '" + naam + '\'' +
                ", beschrijving = '" + beschrijving + '\'' +
                ", prijs = " + prijs;
    }
}
