package Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("macchinetta")
public class Macchinetta extends Rivenditore {
    private boolean inFunzione;


    // constructor

    public Macchinetta(List<Abbonamento> venditeAbbonamenti, List<Biglietto> venditeBiglietti, boolean inFunzione) {
        super(venditeAbbonamenti, venditeBiglietti);
        this.inFunzione = inFunzione;
    }

    public Macchinetta() {
    }
    // to string

    @Override
    public String toString() {
        return "Macchinetta{" +
                "inFunzione=" + inFunzione +
                '}';
    }

    //get set

    public boolean isInFunzione() {
        return inFunzione;
    }

    public void setInFunzione(boolean inFunzione) {
        this.inFunzione = inFunzione;
    }
}
