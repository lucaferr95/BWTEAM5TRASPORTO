package Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table( name = "biglietti")
public class Biglietto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEmissione;

    private boolean validazione;

    private LocalDate dataValidazione;



    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzi mezzo;

    public Biglietto() {
    }

    public Biglietto(LocalDate dataEmissione, boolean validazione, LocalDate dataValidazione,
                      Utente utente, Mezzi mezzo) {
        this.dataEmissione = dataEmissione;
        this.validazione = validazione;
        this.dataValidazione = dataValidazione;

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



    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Mezzi getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzi mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Biglietto{" +
                "id=" + id +
                ", dataEmissione=" + dataEmissione +
                ", validazione=" + validazione +
                ", dataValidazione=" + dataValidazione +

                ", utente=" + (utente != null ? utente.getId() : null) +
                ", mezzo=" + (mezzo != null ? mezzo.getId() : null) +
                '}'+super.toString();
    }
}
