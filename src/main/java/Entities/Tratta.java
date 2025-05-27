package Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tratte")
public class Tratta {


    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn( name = "mezzi_id")
    private Mezzi mezzo;

    @Column(name = "tempo_effettivo")
    private LocalTime tempoEffettivo;

    @Column(name = "nome_tratta")
    private String nomeTratta;

    @Column(name = "zona_partenza")
    private String zonaDiPartenza;

    @Column(name = "tempo_previsto")
    private LocalTime tempoPrevisto;

    private String capolinea;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mezzi getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzi mezzo) {
        this.mezzo = mezzo;
    }

    public LocalTime getTempoEffettivo() {
        return tempoEffettivo;
    }

    public void setTempoEffettivo(LocalTime tempoEffettivo) {
        this.tempoEffettivo = tempoEffettivo;
    }

    public String getNomeTratta() {
        return nomeTratta;
    }

    public void setNomeTratta(String nomeTratta) {
        this.nomeTratta = nomeTratta;
    }

    public String getZonaDiPartenza() {
        return zonaDiPartenza;
    }

    public void setZonaDiPartenza(String zonaDiPartenza) {
        this.zonaDiPartenza = zonaDiPartenza;
    }

    public LocalTime getTempoPrevisto() {
        return tempoPrevisto;
    }

    public void setTempoPrevisto(LocalTime tempoPrevisto) {
        this.tempoPrevisto = tempoPrevisto;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public Tratta( Mezzi mezzo, LocalTime tempoEffettivo, String nomeTratta, String zonaDiPartenza, LocalTime tempoPrevisto, String capolinea) {

        this.mezzo = mezzo;
        this.tempoEffettivo = tempoEffettivo;
        this.nomeTratta = nomeTratta;
        this.zonaDiPartenza = zonaDiPartenza;
        this.tempoPrevisto = tempoPrevisto;
        this.capolinea = capolinea;
    }

    public Tratta(){}

    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", mezzo=" + mezzo +
                ", tempoEffettivo=" + tempoEffettivo +
                ", nomeTratta='" + nomeTratta + '\'' +

                ", zonaDiPartenza='" + zonaDiPartenza + '\'' +
                ", tempoPrevisto=" + tempoPrevisto +
                ", capolinea='" + capolinea + '\'' +
                '}';
    }
}