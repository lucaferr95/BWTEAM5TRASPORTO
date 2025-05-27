package Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Biglietto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEmissione;

    private boolean validazione;

    private LocalDate dataValidazione;

    @ManyToOne
    @JoinColumn(name = "rivenditore_id")
    private Rivenditore rivenditore;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;

    public Biglietto() {
    }

    public Biglietto(LocalDate dataEmissione, boolean validazione, LocalDate dataValidazione,
                     Rivenditore rivenditore, Utente utente, Mezzo mezzo) {
        this.dataEmissione = dataEmissione;
        this.validazione = validazione;
        this.dataValidazione = dataValidazione;
        this.rivenditore = rivenditore;
        this.utente = utente;
        this.mezzo = mezzo;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public boolean isValidazione() {
        return validazione;
    }

    public void setValidazione(boolean validazione) {
        this.validazione = validazione;
    }

    public LocalDate getDataValidazione() {
        return dataValidazione;
    }

    public void setDataValidazione(LocalDate dataValidazione) {
        this.dataValidazione = dataValidazione;
    }

    public Rivenditore getRivenditore() {
        return rivenditore;
    }

    public void setRivenditore(Rivenditore rivenditore) {
        this.rivenditore = rivenditore;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", validazione=" + validazione +
                ", dataValidazione=" + dataValidazione +
                ", rivenditore=" + (rivenditore != null ? rivenditore.getId() : null) +
                ", utente=" + (utente != null ? utente.getId() : null) +
                ", mezzo=" + (mezzo != null ? mezzo.getId() : null) +
                '}';
    }
}
