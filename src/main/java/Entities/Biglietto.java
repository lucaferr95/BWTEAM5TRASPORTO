package Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table( name = "biglietti")
public class Biglietto extends TitoloDiViaggio{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


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


    public Biglietto(LocalDate dataEmissione, Utente utente) {
        super(dataEmissione);
        this.utente = utente;
    }


    @Override // il bilgietto è sempre valido fino a validazione
    public boolean isValido() {
        return !validazione;
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
                ", validazione=" + validazione +
                ", dataValidazione=" + dataValidazione +

                ", utente=" + (utente != null ? utente.getId() : null) +
                ", mezzo=" + (mezzo != null ? mezzo.getId() : null) +
                '}'+super.toString();
    }
}
