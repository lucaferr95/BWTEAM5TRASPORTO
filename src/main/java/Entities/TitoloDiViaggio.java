package Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "titoli_viaggi")
public abstract class TitoloDiViaggio {
    @Column(name = "data_emissione")
    protected LocalDate dataEmissione;
    @ManyToOne
    @JoinColumn( name = "rivenditore_id")
    protected Rivenditore rivenditore;
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TitoloDiViaggio() {
    }

    public abstract boolean isValido(); //non so se serve ma credo di si

    public TitoloDiViaggio(LocalDate dataEmissione ) {
        this.dataEmissione = dataEmissione;

    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public Rivenditore getRivenditore() {
        return rivenditore;
    }

    public void setRivenditore(Rivenditore rivenditore) {
        this.rivenditore = rivenditore;
    }

    @Override
    public String toString() {
        return "TitoloDiViaggio{" +
                "dataEmissione=" + dataEmissione +
                ", rivenditore=" + rivenditore +
                ", id=" + id +
                '}';
    }
}
