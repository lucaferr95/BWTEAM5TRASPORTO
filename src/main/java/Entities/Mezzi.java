package Entities;

import Enumeration.TipoMezzo;
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
    TipoMezzo tipoMezzo;
    @Column(name = "stato_attuale")
    private String statoAttuale;
    private boolean validazione;
    private int posti;
    @Column(name = "numero_vidimazioni")
    private int numerovidimazioni;
    @OneToMany(mappedBy = "mezzi")
    private Tratta tratta;

    @OneToMany(mappedBy = "mezzi")
    private List<PeriodicoManutenzione> serviziManutenzioni;


    public Mezzi() {
    }

    public Mezzi(TipoMezzo tipoMezzo, String statoAttuale, boolean validazione, int posti, int numerovidimazioni, Tratta tratta) {
        this.tipoMezzo = tipoMezzo;
        this.statoAttuale = statoAttuale;
        this.validazione = validazione;
        this.posti = posti;
        this.numerovidimazioni = numerovidimazioni;
        this.tratta = tratta;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
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

    public String getStatoAttuale() {
        return statoAttuale;
    }

    public void setStatoAttuale(String statoAttuale) {
        this.statoAttuale = statoAttuale;
    }

    public boolean isValidazione() {
        return validazione;
    }

    public void setValidazione(boolean validazione) {
        this.validazione = validazione;
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

    @Override
    public String toString() {
        return "Mezzi{" +
                "id=" + id +
                ", tipoMezzo=" + tipoMezzo +
                ", statoAttuale='" + statoAttuale + '\'' +
                ", validazione=" + validazione +
                ", posti=" + posti +
                ", numerovidimazioni=" + numerovidimazioni +
                ", tratta=" + tratta +
                ", serviziManutenzioni=" + serviziManutenzioni +
                '}';
    }
}
