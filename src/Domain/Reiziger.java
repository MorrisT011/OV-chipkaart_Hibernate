package Domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Reiziger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reiziger_id")
    private Long id;


    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;


    @OneToOne(mappedBy = "reiziger")
    private Adres adres;


    @OneToMany(
            mappedBy = "reiziger",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OVChipkaart> OVChipkaarten = new ArrayList<>();


    public Reiziger(String voorletters, String tussenvoegsels, String achternaam, Date geboortedatum) {
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsels;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOVChipkaarten() {
        return OVChipkaarten;
    }

    public void setOVChipkaarten(List<OVChipkaart> OVChipkaarten) {
        for (OVChipkaart o : OVChipkaarten) {
            o.setReiziger(this);
            this.OVChipkaarten.add(o);
        }
    }

    @Override
    public String toString() {
        return "Domain.Reiziger: " + "id: " + id + ", voorletters: '" + voorletters + '\'' +
                ", tussenvoegsels: '" + tussenvoegsel + '\'' + ", achternaam: '" +
                achternaam + '\'' + ", geboortedatum: " + geboortedatum;
    }
}
