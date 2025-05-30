package Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table (name = "tessere")
public class Tessera
{
@Id
@GeneratedValue
@Column (name="codice_identificativo")
private int codiceIdentificativo;

@OneToOne
@JoinColumn(name = "utente_id")
private Utente utente;

@OneToOne(mappedBy ="tessera")
private Abbonamento abbonamento;

@Column (name="validita_annuale")
private LocalDate validitaAnnuale;
private boolean attiva= true;

private LocalDate dataScadenza;

public Tessera(){};

    public Tessera(LocalDate validitaAnnuale, boolean attiva) {
        this.validitaAnnuale = validitaAnnuale;
        this.attiva = attiva;
        this.dataScadenza = validitaAnnuale.plusYears(1);
    }

    //aggiungo un costruttore
    public Tessera(Utente utente) {
        this.utente = utente;
        this.validitaAnnuale = LocalDate.now();
        this.attiva = true;
        this.dataScadenza = validitaAnnuale.plusYears(1);
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public int getCodiceIdentificativo() {
        return codiceIdentificativo;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public LocalDate getValiditaAnnuale() {
        return validitaAnnuale;
    }

    public void setValiditaAnnuale(LocalDate validitaAnnuale) {
        this.validitaAnnuale = validitaAnnuale;
    }

    public boolean isAttiva() {
        return attiva;
    }

    public void setAttiva(boolean attiva) {
        this.attiva = attiva;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Abbonamento getAbbonamento() {
        return abbonamento;
    }

    public void setAbbonamento(Abbonamento abbonamento) {
        this.abbonamento = abbonamento;
    }

    @Override
    public String toString() {
        return "Tessera: [" +
                "codiceIdentificativo: " + codiceIdentificativo +
                ", utenteId: " + (utente != null ? utente.getId() : "null") +
                ", abbonamentoId: " + (abbonamento != null ? abbonamento.getId() : "null") +
                ", validitaAnnuale: " + validitaAnnuale +
                ", stato=" + (attiva ? "attiva" : "non attiva") +
                ']';
    }
}
