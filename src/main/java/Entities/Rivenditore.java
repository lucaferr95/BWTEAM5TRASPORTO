package Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_rivenditore")
public abstract class Rivenditore {
    @Id
    @GeneratedValue
    private int id;

    //@OneToMany(mappedBy = "rivenditore")
    private List<Abbonamento> venditeAbbonamenti;
    //@OneToMany(mappedBy = "rivenditore")
    private List<Biglietto> venditeBiglietti;

    // constructor

    public Rivenditore(List<Abbonamento> venditeAbbonamenti, List<Biglietto> venditeBiglietti) {
        this.venditeAbbonamenti = venditeAbbonamenti;
        this.venditeBiglietti = venditeBiglietti;
    }

    public Rivenditore() {
    }

    // to string

    @Override
    public String toString() {
        return "Rivenditore{" +
                "id=" + id +
                ", venditeAbbonamenti=" + venditeAbbonamenti +
                ", venditeBiglietti=" + venditeBiglietti +
                '}';
    }

    //get set

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Abbonamento> getVenditeAbbonamenti() {
        return venditeAbbonamenti;
    }

    public void setVenditeAbbonamenti(List<Abbonamento> venditeAbbonamenti) {
        this.venditeAbbonamenti = venditeAbbonamenti;
    }

    public List<Biglietto> getVenditeBiglietti() {
        return venditeBiglietti;
    }

    public void setVenditeBiglietti(List<Biglietto> venditeBiglietti) {
        this.venditeBiglietti = venditeBiglietti;
    }
}
