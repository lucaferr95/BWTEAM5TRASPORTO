package Entities;

import Enumeration.TipoUtente;
import jakarta.persistence.*;

import java.net.PasswordAuthentication;
import java.util.List;

@Entity
@Table (name="utenti")


public class Utente {
    @OneToMany( mappedBy = "utente")
    private List<Biglietto> biglietti;

    @OneToOne(mappedBy = "utente")
    private Tessera tessera;
@Id
@GeneratedValue
private int id;



@Column(nullable = false)
private String nome;

@Column(nullable = false)
private String cognome;

@Enumerated (EnumType.STRING)
private TipoUtente tipoUtente;
@Column(unique = true) // username univoco
private String username;
private String password;
public  Utente() {}

    public Utente(String nome, String cognome, TipoUtente tipoUtente, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.tipoUtente = tipoUtente;
        this.username = username;
        this.password = password;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Biglietto> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(List<Biglietto> biglietti) {
        this.biglietti = biglietti;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", tipoUtente=" + tipoUtente +
                '}' ;
    }
}
