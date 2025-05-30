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
import java.util.ArrayList;
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


//         AMMINISTRATORE
        Utente patrizio= new Utente("Gino", "Parmigino",TipoUtente.PATRIZIO,"Topogigio","ciao");
        utenteDao.salvaUtente(patrizio);


        // MENU
        int scelta = -1;
        while (scelta != 0) {
            try {

                System.out.println("""
                                        ╔════════════════════╗
                                            💻 TEAM 5 💻      
                                          💪Giulia's Slaves🐢
                                        ╚════════════════════╝
                                        """);
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
                case 1 -> {
                    boolean loginRiuscito = false;

                    while (!loginRiuscito) {
                        try {


                            System.out.println("Inserisci l'username: ");
                            String username = scanner.nextLine();
                            System.out.println("Inserisci la password");
                            String password = scanner.nextLine();

                            Utente utente = utenteDao.trovaUtenteConUsername(username);

                            if (utente != null && utente.getPassword().equals(password)) {
                                System.out.println("Accesso eseguito, " + username);
                                loginRiuscito = true;

                                if (utente.getTipoUtente() == TipoUtente.PLEBEO) {
                                    menuPlebeo(utente, tesseraDao, titoloDiViaggioDao, rivenditoreDao, trattaDao, mezziDao);
                                } else {
                                    menuPatrizio(mezziDao, periodicoDao, titoloDiViaggioDao, trattaDao, scanner, rivenditoreDao);
                                }
                            } else {
                                System.out.println("Username o password errati. Riprova.\n");
                            }

                        } catch (Exception e) {
                            System.out.println("Errore durante il login: " + e.getMessage());
                        }
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
                        Utente utente = new Utente(nome, cognome, TipoUtente.PLEBEO, username, password);
                        utenteDao.salvaUtente(utente);
                        System.out.println("Registrazione completata!");
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

    public static void menuPlebeo(Utente utente, TesseraDao tesseraDao, TitoloDiViaggioDao titoloDao,
                                  RivenditoreDao rivenditoreDao, TrattaDAO trattaDAO, MezziDao mezziDao) {
        Scanner scanner = new Scanner(System.in);
        boolean continua = true;


        while (continua) {
            System.out.println("\n--- MENU PLEBEO ---");
            System.out.println("1) Acquista biglietto");
            System.out.println("2) Acquista una tessera");
            System.out.println("3) Acquista abbonamento");
            System.out.println("4) Visualizza tessera  ");
            System.out.println("5) Verifica validità abbonamento");
            System.out.println("6) Percorri una tratta");
            System.out.println("0) Logout");
            System.out.print("Scelta: ");
            String scelta1 = scanner.nextLine();

            switch (scelta1) {
                case "1" -> {

                    System.out.println("Dove vuoi acquistare il biglietto? 1: Macchinetta, 2: Punto Vendita");
                    int sceltaRivenditore = Integer.parseInt(scanner.nextLine());

                    Rivenditore rivenditoreScelto = null;

                    if (sceltaRivenditore == 1) {
                        rivenditoreScelto = rivenditoreDao.getRivenditoreByTipo(Macchinetta.class);
                    } else if (sceltaRivenditore == 2) {
                        List<PuntoVendita> puntiVendita = rivenditoreDao.getTuttiIPuntiVendita();

                        if (puntiVendita.isEmpty()) {
                            System.out.println("Nessun punto vendita disponibile.");

                        }

                        System.out.println("Punti vendita disponibili:");
                        for (PuntoVendita pv : puntiVendita) {
                            System.out.println("- ID: " + pv.getId() + " | Nome: " + pv.getNome());
                        }

                        System.out.print("Inserisci l'ID del punto vendita: ");
                        long idScelto = Long.parseLong(scanner.nextLine());
                        rivenditoreScelto = rivenditoreDao.getById((int) idScelto);

                        if (!(rivenditoreScelto instanceof PuntoVendita)) {
                            System.out.println("ID non corrisponde a un punto vendita.");

                        }
                    } else {
                        System.out.println("Scelta non valida.");
                        return;
                    }

                    Biglietto biglietto = new Biglietto(LocalDate.now(), utente);
                    biglietto.setRivenditore(rivenditoreScelto);
                    biglietto.setUtente(utente); // Non obbligatorio se già passato nel costruttore, ma va bene

                    // Inizializza la lista se è null
                    if (utente.getBiglietti() == null) {
                        utente.setBiglietti(new ArrayList<>());
                    }
                    utente.getBiglietti().add(biglietto);

                    titoloDao.save(biglietto);

                    System.out.println("Biglietto emesso con codice: " + biglietto.getId());

                }
                case "2" -> {
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

                case "3" -> {
                    System.out.println("Che tipo di abbonamento desideri? 1:Mensile, 2:Settimanale");
                    int sceltaAbbonamento = Integer.parseInt(scanner.nextLine());

                    TipoAbbonamento tipoScelto = null;
                    if (sceltaAbbonamento == 1) {
                        tipoScelto = TipoAbbonamento.MENSILE;
                    } else if (sceltaAbbonamento == 2) {
                        tipoScelto = TipoAbbonamento.SETTIMANALE;
                    } else {
                        System.out.println("Scelta tipo abbonamento non valida.");
                        break;
                    }

                    // Verifica tessera
                    Tessera tessera = tesseraDao.cercaTesseraPerUtente(utente.getId());
                    if (tessera == null) {
                        System.out.println("Devi prima creare una tessera prima di acquistare un abbonamento.");
                        break;
                    }

                    // Verifica esistenza abbonamento attivo dello stesso tipo
                    Abbonamento abbonamentoEsistente = titoloDao.findAbbonamentoAttivoByTipo(utente, tipoScelto);
                    if (abbonamentoEsistente != null) {
                        System.out.println("Abbonamento " + tipoScelto.name().toLowerCase()
                                + " giÃ  attivo fino al: " + abbonamentoEsistente.getDataScadenza());
                        break;
                    }

                    // Scelta rivenditore
                    System.out.println("Dove vuoi acquistare l'abbonamento? 1: Macchinetta, 2: Punto Vendita");
                    int sceltaRivenditore = Integer.parseInt(scanner.nextLine());

                    Rivenditore rivenditoreScelto = null;
                    if (sceltaRivenditore == 1) {
                        rivenditoreScelto = rivenditoreDao.getRivenditoreByTipo(Macchinetta.class);
                    } else if (sceltaRivenditore == 2) {
                        rivenditoreScelto = rivenditoreDao.getRivenditoreByTipo(PuntoVendita.class);
                    } else {
                        System.out.println("Scelta rivenditore non valida.");
                        break;
                    }

                    // Crea e salva l'abbonamento
                    Abbonamento nuovoAbbonamento = new Abbonamento(tipoScelto);
                    nuovoAbbonamento.setTessera(tessera);
                    nuovoAbbonamento.setRivenditore(rivenditoreScelto);
                    titoloDao.save(nuovoAbbonamento);

                    System.out.println("Abbonamento " + tipoScelto.name().toLowerCase()
                            + " effettuato con scadenza: " + nuovoAbbonamento.getDataScadenza());
                }

                case "4" -> {


                    //visualizza tessera
                    try {
                        Tessera tessera = tesseraDao.cercaTesseraPerUtente(utente.getId());
                        if (tessera != null) {
                            System.out.println("Hai già un tessera valida fino al: " + tessera.getDataScadenza());
                            System.out.println(tessera); // cambia tostring
                    } else {
                        System.out.println("Nessuna tessera trovata.");

                        }
                    }catch (Exception e) {
                        System.out.println("Errore durante la creazione della tessera: " + e.getMessage());}
                }
                case "5" -> {
                    TitoloDiViaggio abbonamento = titoloDao.findAbbonamentoAttivoByUtente(utente);
                    if (abbonamento != null) {
                        if (abbonamento instanceof Abbonamento ) {
                            Abbonamento abb = (Abbonamento) abbonamento;
                            System.out.println("Abbonamento attivo fino al: " + abb.getDataScadenza());
                        }
                    } else {
                        System.out.println("Nessun abbonamento attivo trovato.");
                    }
                }
                case "6" -> {
                    System.out.println("Quale tratta vuoi percorrere? ");
                    List<Tratta> tratte = trattaDAO.listaTratte();
                    tratte.forEach(t -> System.out.println("ID: " + t.getId() + ", Nome: " + t.getNomeTratta()));
                    Long trattaId = Long.parseLong(scanner.nextLine());

                    Tratta tratta = trattaDAO.cercaTrattaById(trattaId);
                    if (tratta == null) {
                        System.out.println("Tratta non trovata, bro.");
                        break;
                    }

                    Mezzi mezzo = tratta.getMezzo();
                    if (mezzo == null) {
                        System.out.println("Nessun mezzo assegnato a questa tratta, sciagurato.");
                        break;
                    }

                    Tessera tessera = tesseraDao.cercaTesseraPerUtente(utente.getId());
                    boolean haAbbonamentoValido = false;
                    if (tessera != null && tessera.getAbbonamento() != null) {
                        Abbonamento abbonamento = tessera.getAbbonamento();
                        if (abbonamento.getDataScadenza().isAfter(LocalDate.now())) {
                            haAbbonamentoValido = true;
                        }
                    }

                    List<Biglietto> biglietti = utente.getBiglietti();
                    Biglietto bigliettoValido = null;
                    if (biglietti != null) {
                        for (Biglietto b : biglietti) {
                            if (b.getMezzo() == null) {
                                bigliettoValido = b;
                            }
                        }
                    }

                    if (haAbbonamentoValido) {
                        System.out.println("Accesso alla tratta consentito con abbonamento. Bravo, ottima scelta.");
                    } else if (bigliettoValido != null) {
                        bigliettoValido.setMezzo(mezzo);
                        bigliettoValido.setValidazione(true);
                        mezzo.setNumerovidimazioni(mezzo.getNumerovidimazioni() + 1);
                        mezziDao.update(mezzo);
                        titoloDao.update(bigliettoValido); // Assicurati che questo metodo esista nel DAO
                        System.out.println("Accesso consentito con biglietto. Hai preso il mezzo ID: " + mezzo.getId() + ". Buon viaggio, plebeo!");
                    } else {
                        System.out.println("Non hai titoli di viaggio validi. Acquista un biglietto o un abbonamento, capra.");
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
            System.out.println("1) Aggiungi un nuovo rivenditore");
            System.out.println("2) Aggiungi un nuovo mezzo");
            System.out.println("3) Crea una nuova tratta");
            System.out.println("4) Associa un mezzo a una tratta");
            System.out.println("5) Aggiorna lo stato del mezzo(Manutenzione/Servizio)");
            System.out.println("6) Cerca mezzi per una determinata tratta");
            System.out.println("7) Visualizza Numero biglietti e abbonamenti emessi in un giorno");
            System.out.println("8) Cerca biglietti vidimati per mezzo  ");
            System.out.println("0) Logout");
            System.out.print("Scelta: ");
            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> {
                    System.out.print("Vuoi creare un sito fisico (1) o una macchinetta (2)? ");
                    int scelta2 = Integer.parseInt(scanner.nextLine());

                    Rivenditore rivenditore;

                    if (scelta2 == 1) {
                        //chiediamo il nome del punto vendita al Patrizio

                        System.out.print("Come vuoi chiamare il punto vendita? ");
                        String nome = scanner.nextLine();

                        //chiediamo anche la posizione

                        System.out.print("Dove si trova questo nuovo punto vendita? ");
                        String posizione = scanner.nextLine();

                        // Creazione punto vendita e settaggio nome e posizione
                        PuntoVendita puntoVendita = new PuntoVendita();
                        puntoVendita.setNome(nome);
                        puntoVendita.setPosizione(posizione);


                        rivenditore = puntoVendita;

                        System.out.println("Punto vendita creato col nome " + nome + " in " + posizione);

                    } else if (scelta2 == 2) {



                        Macchinetta macchinetta = new Macchinetta();



                        rivenditore = macchinetta;

                        System.out.println("Nuova macchinetta creata") ;
                    } else {
                        System.out.println("Scelta non valida.");
                        return;
                    }

                    rivenditoreDao.save(rivenditore);
                    System.out.println("Rivenditore salvato correttamente!");


                }
                case "2" -> {

                    System.out.print("Tipo mezzo (1-TRAM, 2-AUTOBUS): ");
                    int sceltaStato = Integer.parseInt(scanner.nextLine());

                    TipoMezzo tipo = null;
                    if (sceltaStato == 1) {
                        tipo = TipoMezzo.TRAM;
                    } else if (sceltaStato == 2) {
                        tipo = TipoMezzo.AUTOBUS;
                    } else {
                        System.out.println("Scelta non valida.");
                        return;
                    }
                    System.out.print("Stato attuale (1-IN SERVIZIO 2-IN MANUTENZIONE): ");
                    int sceltaPeriodico = Integer.parseInt(scanner.nextLine());

                    TipoPeriodicoManutenzione nuovoStato = null;
                    if (sceltaPeriodico == 1) {
                        nuovoStato = TipoPeriodicoManutenzione.IN_SERVIZIO;
                    } else if (sceltaPeriodico == 2) {
                        nuovoStato = TipoPeriodicoManutenzione.IN_MANUTENZIONE;
                    } else {
                        System.out.println("Scelta non valida.");
                        return;
                    }
                    System.out.print("Posti disponibili: ");
                    int posti = Integer.parseInt(scanner.nextLine());

                    Mezzi mezzo = new Mezzi(tipo,nuovoStato,posti/*,vidimazioni*/);
                    mezziDao.save(mezzo);
                    System.out.println("Mezzo inserito con ID: " + mezzo.getId());


                }
                case "3" -> {

                    System.out.println("Come vuoi chiamare la tratta? ");
                    String nomeTratta = scanner.nextLine();

                    System.out.println("Da dove inizia questa nuova tratta? ");
                    String zonaPartenza = scanner.nextLine();

                    System.out.println("Dove finisce questa nuova tratta? ");
                    String zonaArrivo = scanner.nextLine();


                    System.out.println("Inserisci il tempo effettivo per la tratta (HH:mm): ");
                    String inputTempo = scanner.nextLine();

                    LocalTime tempoPrevisto = null;
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        tempoPrevisto = LocalTime.parse(inputTempo, formatter);
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato orario non valido. Usa HH:mm, ad esempio 01:30 ");
                        System.out.println(e.getMessage());
                        return;
                    }


                    Tratta nuovaTratta = new Tratta(nomeTratta, zonaPartenza, tempoPrevisto, zonaArrivo);
                    trattaDao.save(nuovaTratta);
                    System.out.println("Tratta creata con successo ");

                }




                case "4" -> {
                    System.out.println("Quale tratta vuoi aggiornare?");
                    List<Tratta> tratte = trattaDao.listaTratte();
                    tratte.forEach(t -> System.out.println("ID: " + t.getId() + ", Nome: " + t.getNomeTratta()));

                    int trattaId = Integer.parseInt(scanner.nextLine());

                    System.out.println("A quale veicolo vuoi associare la tratta?");
                    List<Mezzi> mezzi = mezziDao.listaMezzi();
                    mezzi.forEach(m -> System.out.println("ID: " + m.getId() + ", Tipo: " + m.getTipoMezzo()
                            + ", Stato: " + m.getStatoAttuale()));

                    long mezzoId = Long.parseLong(scanner.nextLine());

                    Mezzi mezzo = mezziDao.getById(mezzoId);
                    if (mezzo == null) {
                        System.out.println("Mezzo non trovato.");
                        return;
                    }

                    trattaDao.aggiornaMezzoTratta(trattaId, mezzo);
                    System.out.println("Tratta aggiornata: ora assegnata al mezzo con ID " + mezzo.getId());

                }
                case "5" -> {
                    System.out.println("Inserisci l'ID del mezzo da aggiornare:");
                    //lista di mezzi
                    Long mezzoId = Long.parseLong(scanner.nextLine());

                    System.out.println("Nuovo stato del mezzo? 1 - IN_SERVIZIO, 2 - IN_MANUTENZIONE");
                    int sceltaStato = Integer.parseInt(scanner.nextLine());

                    TipoPeriodicoManutenzione nuovoStato = null;
                    if (sceltaStato == 1) {
                        nuovoStato = TipoPeriodicoManutenzione.IN_SERVIZIO;
                    } else if (sceltaStato == 2) {
                        nuovoStato = TipoPeriodicoManutenzione.IN_MANUTENZIONE;
                    } else {
                       System.out.println("Scelta non valida.");
                       return;
                    }
                    // aggiungi print al veicolo cambiato
                    mezziDao.aggiornaStatoMezzo(mezzoId, nuovoStato);
                }
                case "6" -> {

                    System.out.print("ID della tratta: ");
                    //print tratte
                    Long idTratta = Long.parseLong(scanner.nextLine());

                    List<Mezzi> mezzi = trattaDao.getMezziByTrattaId(idTratta);
                    if (mezzi.isEmpty()) {
                        System.out.println("Nessun mezzo assegnato a questa tratta.");
                    } else {
                        mezzi.forEach(m -> System.out.println("Mezzo ID: " + m.getId() + ", tipo: " + m.getTipoMezzo()));
                    }
                }



                case "7" -> {
                    System.out.print("Inserisci data (es: 2024-05-26): ");
                    LocalDate data = LocalDate.parse(scanner.nextLine());

                    long totale = titoloDiViaggioDao.countTitoliEmessiInData(data);
                    System.out.println("\nTotale titoli emessi il " + data + ": " + totale);

                    List<String> riepilogo = titoloDiViaggioDao.riepilogoTitoliPerRivenditoreETipo(data);
                    System.out.println("\nDettaglio per tipo di rivenditore e titolo:");
                    riepilogo.forEach(System.out::println);

                }
                case "8" -> {
                    System.out.println("Di quale mezzo vuoi controllare le vidimazioni? ");
                    List<Mezzi> mezzi = mezziDao.listaMezzi();
                    mezzi.forEach(m -> System.out.println("ID: " + m.getId() + ", Tipo: " + m.getTipoMezzo()
                            + ", Stato: " + m.getStatoAttuale()));

                    long mezzoId = Long.parseLong(scanner.nextLine());
                    System.out.println("Numero di biglietti vidimati per il mezzo selezionato: " + mezziDao.countBigliettiVidimatiPerMezzo(mezzoId));
                }



                case "0" -> continua = false;
                default -> System.out.println("Scelta non valida. ");
            }
        }
    }

}