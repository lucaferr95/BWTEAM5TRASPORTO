package Entities;

import Enumeration.TipoMezzo;
import Enumeration.TipoPeriodicoManutenzione;
import jakarta.persistence.*;

import java.util.List;
// Giulia
@Entity
@Table(name = "mezzi")
public class Mezzi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_mezzo")
    private TipoMezzo tipoMezzo;
    @Column(name = "stato_attuale")
    @Enumerated(EnumType.STRING)
    private TipoPeriodicoManutenzione statoAttuale;

    private int posti;
    @Column(name = "numero_vidimazioni")
    private int numerovidimazioni;
    @OneToMany(mappedBy = "mezzo")
    private List<Tratta> tratte;

    @OneToMany(mappedBy = "mezzi")
    private List<PeriodicoManutenzione> serviziManutenzioni;


    public Mezzi() {
    }


    public List<Tratta> getTratte() {
        return tratte;
    }

    public void setTratte(List<Tratta> tratte) {
        this.tratte = tratte;
    }

    public TipoPeriodicoManutenzione getStatoAttuale() {
        return statoAttuale;
    }

    public void setStatoAttuale(TipoPeriodicoManutenzione statoAttuale) {
        this.statoAttuale = statoAttuale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }


    public int getPosti() {
        return posti;
    }

    public void setPosti(int posti) {
        this.posti = posti;
    }

    public int getNumerovidimazioni() {
        return numerovidimazioni;
    }

    public void setNumerovidimazioni(int numerovidimazioni) {
        this.numerovidimazioni = numerovidimazioni;
    }

    public List<PeriodicoManutenzione> getServiziManutenzioni() {
        return serviziManutenzioni;
    }

    public void setServiziManutenzioni(List<PeriodicoManutenzione> serviziManutenzioni) {
        this.serviziManutenzioni = serviziManutenzioni;
    }

    public Mezzi(TipoMezzo tipoMezzo, TipoPeriodicoManutenzione statoAttuale, int posti, int numerovidimazioni) {
        this.tipoMezzo = tipoMezzo;
        this.statoAttuale = statoAttuale;
        this.posti = posti;
        this.numerovidimazioni = numerovidimazioni;
    }

    @Override
    public String toString() {
        return "Mezzi{" +
                "id=" + id +
                ", tipoMezzo=" + tipoMezzo +
                ", statoAttuale=" + statoAttuale +
                ", posti=" + posti +
                ", numerovidimazioni=" + numerovidimazioni +
                ", tratte=" + tratte +
                ", serviziManutenzioni=" + serviziManutenzioni +
                '}';
    }
}
