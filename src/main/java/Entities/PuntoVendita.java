package Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("PuntoVendita")
public class PuntoVendita extends Rivenditore {
    private boolean aperto;


    // constructor

    public PuntoVendita(List<Abbonamento> venditeAbbonamenti, List<Biglietto> venditeBiglietti, boolean aperto) {
        super(venditeAbbonamenti, venditeBiglietti);
        this.aperto = aperto;
    }

    public PuntoVendita() {
    }
    // to string

    @Override
    public String toString() {
        return "PuntoVendita{" + super.toString() +
                "aperto=" + aperto +
                '}';
    }

    //get set

    public boolean isAperto() {
        return aperto;
    }

    public void setAperto(boolean aperto) {
        this.aperto = aperto;
    }
}
