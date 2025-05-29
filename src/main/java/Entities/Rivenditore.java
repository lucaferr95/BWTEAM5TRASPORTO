package Entities;

import jakarta.persistence.*;

import java.util.List;

@NamedQuery(
        name = "Rivenditore.findAll",
        query = "SELECT r FROM Rivenditore r"
)

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_rivenditore")
public abstract class Rivenditore {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "punto_vendita")
    private String puntoVendita;

    @OneToMany(mappedBy = "rivenditore")
    private List<TitoloDiViaggio> titoliDiViaggio;

    @Override
    public String toString() {
        return "Rivenditore{" +
                "id=" + id +
                ", puntoVendita='" + puntoVendita + '\'' +

                '}';
    }



    public Rivenditore(String puntoVendita) {
        this.puntoVendita = puntoVendita;
    }

    public String getPuntoVendita() {
        return puntoVendita;
    }

    public void setPuntoVendita(String puntoVendita) {
        this.puntoVendita = puntoVendita;
    }

    public Rivenditore() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}



