package Entities;

import Enumeration.TipoUtente;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name="utenti")


public class Utente {
    @OneToMany( mappedBy = "utente")
    private List<Biglietto> biglietti;

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

    public Utente(String nome, String cognome, TipoUtente tipoUtente) {
        this.nome = nome;
        this.cognome = cognome;
        this.tipoUtente = tipoUtente;
    }

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

    public int getId() {
        return id;
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
