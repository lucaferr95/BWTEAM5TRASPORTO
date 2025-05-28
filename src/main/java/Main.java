import Dao.*;
import Entities.TitoloDiViaggio;
import Entities.Utente;
import Enumeration.TipoUtente;
import Exceptions.UsernameEsistenteException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.InputMismatchException;
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


                            // menu
















                        }
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
}
