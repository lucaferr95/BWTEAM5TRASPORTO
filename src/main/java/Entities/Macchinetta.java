package Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("macchinetta")
public class Macchinetta extends Rivenditore {
    private boolean inFunzione;

    public Macchinetta(String puntoVendita, boolean inFunzione) {
        super(puntoVendita);
        this.inFunzione = inFunzione;
    }

    public Macchinetta() {}

    @Override
    public String toString() {
        return "Macchinetta{" +
                "inFunzione=" + inFunzione +
                ", " + super.toString() +
                '}';
    }

    public boolean isInFunzione() {
        return inFunzione;
    }

    public void setInFunzione(boolean inFunzione) {
        this.inFunzione = inFunzione;
    }
}
