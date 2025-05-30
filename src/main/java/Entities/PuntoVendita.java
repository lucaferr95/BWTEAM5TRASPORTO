package Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("PuntoVendita")
public class PuntoVendita extends Rivenditore {
    private String nome;
    private String posizione;
    private boolean aperto;

    public PuntoVendita(String puntoVendita, boolean aperto) {
        super(puntoVendita);
        this.aperto = aperto;
    }

    public PuntoVendita() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "PuntoVendita{" +
                "aperto=" + aperto +
                ", " + super.toString() +
                '}';
    }

    public boolean isAperto() {
        return aperto;
    }

    public void setAperto(boolean aperto) {
        this.aperto = aperto;
    }

    public String getPosizione() {
        return posizione;
    }

    public void setPosizione(String posizione) {
    }
}
