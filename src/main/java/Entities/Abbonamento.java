package Entities;

import Enumeration.TipoAbbonamento;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Abbonamento extends TitoloDiViaggio {

@OneToOne
@JoinColumn(name = "tessera_id")
private Tessera tessera;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_abbonamento")
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;




    @Column( name = "data_scadenza")
    private LocalDate dataScadenza;

    public Abbonamento() {
    }

    public Abbonamento(TipoAbbonamento tipoAbbonamento) {
        this.dataEmissione = LocalDate.now();
        this.tipoAbbonamento = tipoAbbonamento;
        if (tipoAbbonamento == TipoAbbonamento.SETTIMANALE) {
            this.dataScadenza = dataEmissione.plusDays(7);
        }
        else if (tipoAbbonamento == TipoAbbonamento.MENSILE){
            this.dataScadenza = dataEmissione.plusDays(30);
        }
    }

    @Override // qua facciamo l'override del metodo per vedere se l'abbonamento è valido o no per salire sul bus
    public boolean isValido() {
        return dataScadenza != null &&
                (dataScadenza.isAfter(LocalDate.now()) || dataScadenza.isEqual(LocalDate.now()));
    }


    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
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



    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    @Override
    public String toString() {
        return "Abbonamento{" +
                "tessera=" + tessera +
                ", id=" + id +
                ", tipoAbbonamento=" + tipoAbbonamento +
                ", dataScadenza=" + dataScadenza +
                ", dataEmissione=" + dataEmissione +
                ", rivenditore=" + rivenditore +
                '}' +super.toString();
    }
}
