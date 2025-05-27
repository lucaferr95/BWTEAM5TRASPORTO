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

    @OneToMany(mappedBy = "rivenditore")
    private List<TitoloDiViaggio> titoloDiViaggio;







    @Override
    public String toString() {
        return "Rivenditore{" +
                "id=" + id +
                ", titoloDiViaggio=" + titoloDiViaggio +
                '}';
    }

    public Rivenditore() {
    }

    public List<TitoloDiViaggio> getTitoloDiViaggio() {
        return titoloDiViaggio;
    }

    public void setTitoloDiViaggio(List<TitoloDiViaggio> titoloDiViaggio) {
        this.titoloDiViaggio = titoloDiViaggio;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}



