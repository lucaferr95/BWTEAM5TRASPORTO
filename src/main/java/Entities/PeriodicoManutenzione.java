package Entities;

import Enumeration.TipoPeriodicoManutenzione;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "periodici_manutenzione")
public class PeriodicoManutenzione {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "servizio_o_manutenzione")
    private TipoPeriodicoManutenzione servizioManutenzione;
    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "data_fine")
    private LocalDate dataFine;

    @ManyToOne
    @JoinColumn(name = "mezzi_id")
    private Mezzi mezzi;

    public PeriodicoManutenzione() {
    }

    public PeriodicoManutenzione(TipoPeriodicoManutenzione servizioManutenzione, LocalDate dataInizio, LocalDate dataFine, Mezzi mezzi) {
        this.servizioManutenzione = servizioManutenzione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.mezzi = mezzi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoPeriodicoManutenzione getServizioManutenzione() {
        return servizioManutenzione;
    }

    public void setServizioManutenzione(TipoPeriodicoManutenzione servizioManutenzione) {
        this.servizioManutenzione = servizioManutenzione;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public Mezzi getMezzi() {
        return mezzi;
    }

    public void setMezzi(Mezzi mezzi) {
        this.mezzi = mezzi;
    }

    @Override
    public String toString() {
        return "PeriodicoManutenzione{" +
                "id=" + id +
                ", servizioManutenzione=" + servizioManutenzione +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", mezzi=" + mezzi +
                '}';
    }
}
