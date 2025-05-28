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
import java.util.InputMismatchException;
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
        while (scelta !=0){
            try{

                System.out.println("Welcome user!");
                System.out.println("1 - Esegui il Login");
                System.out.println("2 - Registrazione");
                System.out.println("0 - Esci");


            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero valido.");
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
                                menuPatrizio(mezziDao, periodicoDao, titoloDiViaggioDao, trattaDao, scanner);

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
                        } // else

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
            System.out.println("0) Logout");
            System.out.print("Scelta: ");
            String scelta1 = scanner.nextLine();

            switch (scelta1) {
                case "1" -> {
                    Tessera tessera = tesseraDao.cercaTesseraPerUtente(utente.getId());
                    if (tessera != null) {
                        System.out.println("Tessera valida fino al: " + tessera.getDataScadenza());
                    } else {
                        System.out.println("Nessuna tessera trovata.");
                    }
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
                case "0" -> continua = false;
                default -> System.out.println("Scelta non valida.");
            }
        }


    }
    public static void menuPatrizio(MezziDao mezziDao, PeriodicoManutenzioneDao manutenzioneDao,
                                    TitoloDiViaggioDao titoloDiViaggioDao, TrattaDAO trattaDao, Scanner scanner) {
        boolean continua = true;

        while (continua) {
            System.out.println("\n--- MENU PATRIZIO ---");
            System.out.println("1) Inserisci mezzo");
            System.out.println("2) Inserisci manutenzione");
            System.out.println("3) Numero biglietti e abbonamenti emessi in un giorno");
            System.out.println("4) Cerca mezzi per tratta");
            System.out.println("5) Calcola tempo medio effettivo per tratta da parte di un mezzo");
            System.out.println("0) Logout");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> {
                    System.out.print("Tipo mezzo (TRAM/AUTOBUS): ");
                    TipoMezzo tipo = TipoMezzo.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Stato attuale: ");
                    String stato = scanner.nextLine();
                    System.out.print("Posti disponibili: ");
                    int posti = Integer.parseInt(scanner.nextLine());

                    Mezzi mezzo = new Mezzi(tipo, stato, true, posti, 0, null);
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
                case "0" -> continua = false;
                default -> System.out.println("Scelta non valida.");
            }
        }
    }
}