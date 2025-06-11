package org.lessons.java.milestone.eventi.Tester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.lessons.java.milestone.eventi.Concerto;
import org.lessons.java.milestone.eventi.Evento;
import org.lessons.java.milestone.eventi.ProgrammaEventi;
import org.lessons.java.milestone.eventi.Eccezioni.*;


public class TestUser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------Creiamo un programma eventi!!!------------");

        System.out.println("Inserisci il titolo del tuo programma di eventi!");
        String nomeProgramma = scanner.nextLine();

        ProgrammaEventi programma = new ProgrammaEventi(nomeProgramma);

        int numeroEventi = 0;
        boolean inputValido = false;
        while (!inputValido) {
            try {
                System.out.println("Quanti eventi vuoi inserire?");
                numeroEventi = Integer.parseInt(scanner.nextLine());
                inputValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Input non valido. Inserisci un numero intero per il numero di eventi.");
            }
        }


        // ciclo for per chiedere più volte all'utente di inserire eventi
        for (int i = 0; i < numeroEventi; i++) {
            System.out.println("\n--- Inserisci i dati per l'evento: " + (i + 1) + " ---");

            try {
                System.out.println("E' un concerto? (si/no)");
                String rispostaTipologia = scanner.nextLine().trim().toLowerCase(); // Normalizza l'input

                System.out.println("Titolo evento: ");
                String titoloEvento = scanner.nextLine();

                System.out.println("Data dell'evento: (dd/mm/yyyy) ");
                String dataStr = scanner.nextLine();
                LocalDate data = LocalDate.parse(dataStr, Evento.dataFormattataNow());

                System.out.print("Posti totali disponibili: ");
                int postiTotali = Integer.parseInt(scanner.nextLine());

                Evento eventoCorrente; // Variabile per l'evento creato

                if (rispostaTipologia.equals("si")) {
                    System.out.println("Ora del concerto: (hh:mm)"); // Corretto il formato
                    String oraStr = scanner.nextLine();
                    LocalTime oraConcerto = LocalTime.parse(oraStr, DateTimeFormatter.ofPattern("HH:mm"));

                    BigDecimal prezzoTicketConcerto = null;
                    boolean prezzoValido = false;
                    while(!prezzoValido) {
                        try {
                            System.out.println("Inserisci il prezzo del biglietto: (es. 29.99)");
                            // Sostituisce la virgola con il punto per BigDecimal
                            prezzoTicketConcerto = new BigDecimal(scanner.nextLine().replace(',', '.'));
                            prezzoValido = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Formato prezzo non valido. Usa il formato ##.##");
                        }
                    }

                    // Il costruttore di Concerto lancia eccezioni controllate per il prezzo
                    eventoCorrente = new Concerto(titoloEvento, data, postiTotali, oraConcerto, prezzoTicketConcerto);

                } else {
                    // Il costruttore di Evento potrebbe lanciare eccezioni (es. data passata)
                    eventoCorrente = new Evento(titoloEvento, data, postiTotali);
                }

               

                // --- Richiesta di prenotazione posti ---
                boolean prenotazioniComplete = false;
               int postiDaPrenotare = 0;
                while (!prenotazioniComplete) {
                    try {
                        System.out.println("Quanti posti vuoi prenotare per questo evento?");
                        postiDaPrenotare = Integer.parseInt(scanner.nextLine());

                        if (postiDaPrenotare > 0) {
                            for (int x = 0; x < postiDaPrenotare; x++) {
                                // Questo metodo dovrebbe gestire la logica di prenotazione e lanciare eccezioni
                                // se i posti non sono disponibili o l'evento è passato.
                                eventoCorrente.prenotaPosto();
                            }
                            System.out.println("Prenotati " + postiDaPrenotare + " posti.");
                        } else if (postiDaPrenotare == 0) {
                            System.out.println("Nessun posto prenotato per questo evento.");
                        }
                        prenotazioniComplete = true; // Usciamo dal ciclo di prenotazione se l'input è valido o 0
                    } catch (NumberFormatException e) {
                        System.out.println("Input non valido. Inserisci un numero intero per i posti da prenotare.");
                    } catch (RuntimeException e) { // Questo dovrebbe catturare eccezioni da prenotaPosto (es. posti esauriti, evento passato)
                        System.err.println("Errore durante la prenotazione: " + e.getMessage());
                        // Non incrementiamo i per far reinserire l'evento, ma permettiamo di riprovare a prenotare
                        // o di procedere con l'evento senza prenotazione.
                        System.out.println("Prova a prenotare un numero diverso di posti, o 0 per non prenotare.");
                    }
                    System.out.println("Hai prenotato " + postiDaPrenotare + "|" + postiTotali );                
                } 
                // Aggiungi l'evento al programma dopo che è stato creato con successo
                programma.aggiungiEvento(eventoCorrente);
                System.out.println("Evento aggiunto con successo!");

            } catch (NumberFormatException e) {
                System.out.println("Errore di formato. Assicurati di inserire numeri per posti, ora o prezzo.");
                i--; // Torna indietro nel ciclo per far reinserire l'evento corrente
                System.out.println("Inserisci nuovamente l'evento.");
            } catch (ExceptionPrezzoNull | ExceptionPrezzoNegativo e) { // Cattura le tue eccezioni di prezzo specifiche
                System.out.println("Errore relativo al prezzo: " + e.getMessage());
                i--; // Torna indietro nel ciclo
                System.out.println("Inserisci nuovamente l'evento.");
            } catch (Exception e) { // Cattura altre eccezioni dal costruttore o da Evento (es. data passata)
                System.out.println("Errore nella creazione dell'evento: " + e.getMessage());
                i--; // Torna indietro nel ciclo
                System.out.println("Inserisci nuovamente l'evento.");
            }
        }

        System.out.println("\n--- Riepilogo Programma Eventi ---");
        System.out.println(programma.stampaEventiPerData());

        scanner.close();
    }
}