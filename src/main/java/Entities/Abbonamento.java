package Entities;

import Enumeration.TipoAbbonamento;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Abbonamento extends TitoloDiViaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_abbonamento")
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;


    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
    @Column( name = "data_scadenza")
    private LocalDate dataScadenza;

    public Abbonamento() {
    }

    public Abbonamento(TipoAbbonamento tipoAbbonamento, Utente utente, LocalDate dataScadenza) {
        this.tipoAbbonamento = tipoAbbonamento;
        this.utente = utente;
        this.dataScadenza = dataScadenza;
    }

    public Long getId() {
        return id;
    }

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
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
                ", tipo='" + tipoAbbonamento + '\'' +
                ", utente=" + (utente != null ? utente.getId() : null) +
                ", dataScadenza=" + dataScadenza +
                ", dataEmissione=" + dataEmissione +
                ", rivenditore=" + (rivenditore != null ? rivenditore.getId() : null) +
                '}'+ super.toString();
    }
}
