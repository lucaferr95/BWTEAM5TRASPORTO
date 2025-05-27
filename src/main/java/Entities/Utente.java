package Entities;

import Enumeration.TipoUtente;
import jakarta.persistence.*;

@Entity
@Table (name="utenti")

public class Utente {
@Id
@GeneratedValue
private int id;

@Column(nullable = false)
private String nome;

@Column(nullable = false)
private String cognome;

@Enumerated (EnumType.STRING)
private TipoUtente tipoUtente;

public  Utente() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public TipoUtente getTipoUtente() {
        return tipoUtente;
    }

    public void setTipoUtente(TipoUtente tipoUtente) {
        this.tipoUtente = tipoUtente;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", tipoUtente=" + tipoUtente +
                '}';
    }
}
