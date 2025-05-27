package Entities;

import java.time.LocalDate;

public abstract class TitoloDiViaggio {

    protected LocalDate dataEmissione;
    protected Rivenditore rivenditore;

    public TitoloDiViaggio() {
    }

    public TitoloDiViaggio(LocalDate dataEmissione, Rivenditore rivenditore) {
        this.dataEmissione = dataEmissione;
        this.rivenditore = rivenditore;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public Rivenditore getRivenditore() {
        return rivenditore;
    }

    public void setRivenditore(Rivenditore rivenditore) {
        this.rivenditore = rivenditore;
    }

    @Override
    public String toString() {
        return "TitoloDiViaggio{" +
                "dataEmissione=" + dataEmissione +
                ", rivenditore=" + (rivenditore != null ? rivenditore.getId() : null) +
                '}';
    }
}
