package Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Abbonamento extends TitoloDiViaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    private LocalDate dataScadenza;

    public Abbonamento() {
    }

    public Abbonamento(LocalDate dataEmissione, Rivenditore rivenditore, String tipo,
                       Utente utente, LocalDate dataScadenza) {
        super(dataEmissione, rivenditore);
        this.tipo = tipo;
        this.utente = utente;
        this.dataScadenza = dataScadenza;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", utente=" + (utente != null ? utente.getId() : null) +
                ", dataScadenza=" + dataScadenza +
                ", dataEmissione=" + dataEmissione +
                ", rivenditore=" + (rivenditore != null ? rivenditore.getId() : null) +
                '}';
    }
}
