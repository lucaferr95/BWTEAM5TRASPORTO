import Dao.*;
import Entities.*;
import Enumeration.TipoAbbonamento;
import Enumeration.TipoMezzo;
import Enumeration.TipoPeriodicoManutenzione;
import Enumeration.TipoUtente;
import Exceptions.UsernameEsistenteException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner((System.in));

        // DAO
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Postgres");
        EntityManager em = emf.createEntityManager();

        RivenditoreDao rivenditoreDao = new RivenditoreDao(em);
        MezziDao mezziDao = new MezziDao(em);
        PeriodicoManutenzioneDao periodicoDao = new PeriodicoManutenzioneDao(em);
        TesseraDao tesseraDao = new TesseraDao(em);
        TitoloDiViaggioDao titoloDiViaggioDao = new TitoloDiViaggioDao(em);
        TrattaDAO trattaDao = new TrattaDAO(em);
        UtenteDao utenteDao = new UtenteDao(em);

        // MENU

        int scelta = -1;
        while (scelta != 0) {
            try {
                System.out.println("Welcome user!");
                System.out.println("1 - Esegui il Login");
                System.out.println("2 - Registrazione");
                System.out.println("0 - Esci");


                scelta = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
                continue;
            }
            switch (scelta){
                case 1->{
                    try {
                        System.out.println("Inserisci l'username: ");
                        String username = scanner.nextLine();
                        System.out.println("Inserisci la password");
                        String password = scanner.nextLine();

                        Utente utente = utenteDao.trovaUtenteConUsername(username); //utente estratto
                        if (utente!=null){
                            System.out.println("Accesso eseguito, " + username);

                            if (utente.getTipoUtente() == TipoUtente.PLEBEO) {
                                menuPlebeo(utente, tesseraDao, titoloDiViaggioDao);
                            } else {
                                menuPatrizio(mezziDao, periodicoDao, titoloDiViaggioDao, trattaDao, scanner,rivenditoreDao);
                            }
                        } else {
                            System.out.println("Errore: credenziali non valide.\n");
                        }

                            // menu

                    } catch (Exception e) {
                        System.out.println("Errore generico");
                    }

                }

                case 2 -> {

                    try {
                        System.out.println("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.println("Cognome: ");
                        String cognome = scanner.nextLine();
                        System.out.println("Inserisci l'username: ");
                        String username = scanner.nextLine();
                        System.out.println("Inserisci la password");
                        String password = scanner.nextLine();
                        System.out.println("Sei un plebeo(1) o un patrizio(2)?");
                        int tipoUtente = Integer.parseInt(scanner.nextLine());
                        if(tipoUtente == 1){
                            Utente utente = new Utente(nome, cognome, TipoUtente.PLEBEO, username, password);
                            utenteDao.salvaUtente(utente);
                            System.out.println("Registrazione completata!");
                        } else if (tipoUtente == 2){
                            Utente utente = new Utente(nome, cognome, TipoUtente.PATRIZIO, username, password);
                            utenteDao.salvaUtente(utente);
                            System.out.println("Registrazione completata!");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Devi inserire un numero valido per il tipo di utente.");
                    } catch (UsernameEsistenteException e) {
                        System.out.println("Errore: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Errore durante la creazione dell'utente: " + e.getMessage());
                    }

                }

                case 0 -> System.out.println("Alla prossima!");
                default -> System.out.println("scelta non valida");
            }



        }





    }

    public static void menuPlebeo(Utente utente, TesseraDao tesseraDao, TitoloDiViaggioDao titoloDao) {
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;

        while (continua) {
            System.out.println("\n--- MENU PLEBEO ---");
            System.out.println("1) Visualizza tessera");
            System.out.println("2) Acquista biglietto");
            System.out.println("3) Acquista abbonamento mensile");
            System.out.println("4) Verifica validità abbonamento");
            System.out.println("5) Crea tessera"); //aggiungo crea tessera
            System.out.println("0) Logout");
            System.out.print("Scelta: ");
            String scelta1 = scanner.nextLine();

            switch (scelta1) {
                case "1" -> {
                    try {
                        Tessera tessera = tesseraDao.cercaTesseraPerUtente(utente.getId());
                        if (tessera != null) {
                            System.out.println("Hai già un tessera valida fino al: " + tessera.getDataScadenza());
                            System.out.println(tessera);
                    } else {
                        System.out.println("Nessuna tessera trovata.");

                        }
                    }catch (Exception e) {
                        System.out.println("Errore durante la creazione della tessera: " + e.getMessage());}

                }
                case "2" -> {
                    TitoloDiViaggio biglietto = new Biglietto(LocalDate.now(), utente);
                    titoloDao.save(biglietto);
                    System.out.println("Biglietto emesso con codice: " + biglietto.getId()); //todo SIAMO QUI
                }
                case "3" -> {
                    System.out.println("Che tipo di abbonamento desideri? 1:Mensile, 2:Annuale" );
                    int sceltaAbbonamento = Integer.parseInt(scanner.nextLine());
                    if(sceltaAbbonamento == 1){
                        TitoloDiViaggio abbonamento = new Abbonamento(TipoAbbonamento.MENSILE, utente);
                        titoloDao.save(abbonamento);
                        System.out.println("Abbonamento mensile effettuato");
                        if (abbonamento instanceof Abbonamento ) {
                            Abbonamento abb = (Abbonamento) abbonamento;
                            System.out.println("Abbonamento mensile attivo fino al: " + abb.getDataScadenza());
                        }
                    } else if (sceltaAbbonamento == 2){
                        TitoloDiViaggio abbonamento = new Abbonamento(TipoAbbonamento.SETTIMANALE, utente);
                        titoloDao.save(abbonamento);
                        System.out.println("Abbonamento settimanale effettuato");

                        if (abbonamento instanceof Abbonamento ) {
                            Abbonamento abb = (Abbonamento) abbonamento;
                            System.out.println("Abbonamento settimanale attivo fino al: " + abb.getDataScadenza());
                        }
                    }

                }
                case "4" -> {
                    TitoloDiViaggio abbonamento = titoloDao.findAbbonamentoAttivoByUtente(utente);
                    if (abbonamento != null) {
                        if (abbonamento instanceof Abbonamento ) {
                            Abbonamento abb = (Abbonamento) abbonamento;
                            System.out.println("Abbonamento settimanale attivo fino al: " + abb.getDataScadenza());
                        }
                    } else {
                        System.out.println("Nessun abbonamento attivo trovato.");
                    }
                }
                case "5" -> {
                    try {
                        Tessera tesseraEsistente = tesseraDao.cercaTesseraPerUtente(utente.getId());
                        if (tesseraEsistente != null) {
                            System.out.println("Tessera valida fino al: " + tesseraEsistente.getDataScadenza());
                            System.out.println(tesseraEsistente);
                        } else {
                            Tessera nuovaTessera = new Tessera(utente);
                            tesseraDao.salvaTessera(nuovaTessera);
                            System.out.println("Tessera creata con successo! Valida fino al: " + nuovaTessera.getDataScadenza());
                            System.out.println(nuovaTessera);
                        }
                    } catch (Exception e) {
                        System.out.println("Errore durante la creazione della tessera: " + e.getMessage());
                    }
                }

                case "0" -> continua = false;
                default -> System.out.println("Scelta non valida.");
            }
        }


    }
    public static void menuPatrizio(MezziDao mezziDao, PeriodicoManutenzioneDao manutenzioneDao,
                                    TitoloDiViaggioDao titoloDiViaggioDao, TrattaDAO trattaDao, Scanner scanner, RivenditoreDao rivenditoreDao) {
        boolean continua = true;

        while (continua) {
            System.out.println("\n--- MENU PATRIZIO ---");
            System.out.println("1) Inserisci mezzo");
            System.out.println("2) Inserisci manutenzione");
            System.out.println("3) Numero biglietti e abbonamenti emessi in un giorno");
            System.out.println("4) Cerca mezzi per tratta");
            System.out.println("5) Calcola tempo medio effettivo per tratta da parte di un mezzo");
            System.out.println("6) Iscrivi un nuovo rivenditore ");
            System.out.println("7) Crea una tratta ");
            System.out.println("0) Logout");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> {
                    System.out.print("Tipo mezzo (TRAM/AUTOBUS): ");
                    TipoMezzo tipo = TipoMezzo.valueOf(scanner.nextLine().toUpperCase());
                    //cambiato senza underscore
                    System.out.print("Stato attuale (IN SERVIZIO/IN MANUTENZIONE): ");
                    TipoPeriodicoManutenzione stato = TipoPeriodicoManutenzione.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Posti disponibili: ");
                    int posti = Integer.parseInt(scanner.nextLine());
                    System.out.print("numero vidimazioni per il viaggio: ");
                    // int vidimazioni = Integer.parseInt(scanner.nextLine());

                    Mezzi mezzo = new Mezzi(tipo,stato,posti/*,vidimazioni*/);
                    mezziDao.save(mezzo);
                    System.out.println("Mezzo inserito con ID: " + mezzo.getId());
                }
                case "2" -> {
                    System.out.print("ID mezzo per manutenzione: ");
                    Long idMezzo = Long.parseLong(scanner.nextLine());
                    Mezzi mezzo = mezziDao.getById(idMezzo);

                    if (mezzo != null) {
                        System.out.print("Tipo intervento (SERVIZIO/MANUTENZIONE): ");
                        TipoPeriodicoManutenzione tipoMan = TipoPeriodicoManutenzione.valueOf(scanner.nextLine().toUpperCase());

                        PeriodicoManutenzione man = new PeriodicoManutenzione(tipoMan, LocalDate.now(), LocalDate.now().plusDays(5), mezzo);
                        manutenzioneDao.save(man);
                        System.out.println("Manutenzione inserita con ID: " + man.getId());
                    } else {
                        System.out.println("Mezzo non trovato.");
                    }
                }
                case "3" -> {
                    System.out.print("Inserisci data (es: 2024-05-26): ");
                    LocalDate data = LocalDate.parse(scanner.nextLine());
                    long count = titoloDiViaggioDao.countTitoliEmessiInData(data);
                    System.out.println("Titoli di viaggio emessi il " + data + ": " + count);
                }
                case "4" -> {
                    System.out.print("ID della tratta: ");
                    Long idTratta = Long.parseLong(scanner.nextLine());
                    List<Mezzi> mezzi = mezziDao.getMezziByTrattaId(idTratta);
                    if (mezzi.isEmpty()) {
                        System.out.println("Nessun mezzo assegnato a questa tratta.");
                    } else {
                        mezzi.forEach(m -> System.out.println("Mezzo ID: " + m.getId() + ", tipo: " + m.getTipoMezzo()));
                    }
                }
                case "5" -> {
                    System.out.print("ID del mezzo: ");
                    Long mezzoId = Long.parseLong(scanner.nextLine());
                    System.out.print("ID della tratta: ");
                    Long trattaId = Long.parseLong(scanner.nextLine());

                    Double media = trattaDao.calcolaTempoMedioEffettivo(mezzoId, trattaId);
                    if (media != null) {
                        System.out.println("Tempo medio effettivo: " + media + " minuti");
                    } else {
                        System.out.println("Nessuna percorrenza trovata per questo mezzo su questa tratta.");
                    }
                }
                case "6" -> {
                    System.out.print("Vuoi creare un sito fisico (1) o una macchinetta (2)? ");
                    int scelta2 = Integer.parseInt(scanner.nextLine());

                    Rivenditore rivenditore;

                    if (scelta2 == 1) {
                        System.out.print("Dove si trova questo nuovo punto vendita? ");
                        String posizione = scanner.nextLine();
                        rivenditore = new PuntoVendita(posizione, true);
                        System.out.println("Punto vendita creato in: " + posizione);
                    } else {
                        System.out.print("Dove si trova la nuova macchinetta? ");
                        String posizione = scanner.nextLine();
                        rivenditore = new Macchinetta(posizione, true);
                        System.out.println("Macchinetta creata in: " + posizione);
                    }

                    rivenditoreDao.save(rivenditore);
                    System.out.println("Rivenditore salvato correttamente!");
                }

                case "7" -> {
                    Mezzi veicolo = null;
                    System.out.println("Vuoi creare un tratta per il Tram 1) o per il bus 2)?");
                    int scelta1 = Integer.parseInt(scanner.nextLine());

                    if (scelta1 == 1) {
                        veicolo = new Mezzi(TipoMezzo.TRAM, TipoPeriodicoManutenzione.IN_SERVIZIO, 30);
                    } else if (scelta1 == 2) {
                        veicolo = new Mezzi(TipoMezzo.AUTOBUS, TipoPeriodicoManutenzione.IN_SERVIZIO, 60);
                    } else {
                        System.out.println("Scelta non valida.");
                        break;
                    }

                    System.out.println("Come vuoi chiamare la tratta? ");
                    String nomeTratta = scanner.nextLine();

                    System.out.println("Da dove inizia questa nuova tratta? ");
                    String zonaPartenza = scanner.nextLine();

                    System.out.println("Dove finisce questa nuova tratta? ");
                    String zonaArrivo = scanner.nextLine();


                    System.out.println("Inserisci il tempo previsto per la tratta (HH:mm): ");
                    String inputTempo = scanner.nextLine();

                    LocalTime tempoPrevisto = null;
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        tempoPrevisto = LocalTime.parse(inputTempo, formatter);
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato orario non valido. Usa HH:mm, ad esempio 01:30");
                        return;
                    }

                    LocalTime tempoIdeale = tempoPrevisto;

                    Tratta nuovaTratta = new Tratta(veicolo, nomeTratta, zonaPartenza, tempoIdeale, zonaArrivo);
                    trattaDao.save(nuovaTratta);
                    System.out.println("Tratta creata con successo");
                }


                case "0" -> continua = false;
                default -> System.out.println("Scelta non valida.");
            }
        }
    }
}