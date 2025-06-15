package org.lessons.java.milestone.eventi.Tester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.lessons.java.milestone.eventi.*;
import org.lessons.java.milestone.eventi.Eccezioni.*;

public class TestUser {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ExceptionDataPassata{
        System.out.println("-------Creiamo un programma eventi!!!------------");

        ProgrammaEventi programma = creaProgramma();
        int numeroEventi = richiediNumeroEventi();

        for (int i = 0; i < numeroEventi; i++){
            System.out.println("\n--- Evento " + (i + 1) + " ---");
            Evento evento = creaEvento();
            gestisciPrenotazioni(evento);
            gestisciDisdette(evento);
            programma.aggiungiEvento(evento);
        }

        System.out.println("--- Riepilogo Programma Eventi ---");
        System.out.println(programma.stampaEventiPerData());

        for (Evento evento : programma.getEventi()) {
            System.out.println(evento.getInfoEvento());
        }

        scanner.close();
    }

    private static ProgrammaEventi creaProgramma() {
        while (true) {
            System.out.println("Inserisci il titolo del tuo programma di eventi: ");
            String titolo = scanner.nextLine().trim();
            try {
                return new ProgrammaEventi(titolo);
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    private static int richiediNumeroEventi() {
        while (true) {
            try {
                System.out.println("Quanti eventi vuoi inserire? ");
                int numero = Integer.parseInt(scanner.nextLine());
                if (numero <= 0)
                    throw new ExceptionNumeroNegativo("Il numero di eventi deve essere positivo.");
                return numero;
            } catch (NumberFormatException e) {
                System.out.println("Errore: inserisci un numero intero valido per il numero di eventi.");
            } catch (ExceptionNumeroNegativo e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    private static Evento creaEvento() {
        while (true) {
            try {
                System.out.println("È un concerto? (si/no): ");
                String tipo = scanner.nextLine().trim().toLowerCase();
                if (!tipo.equals("si") && !tipo.equals("no")) {
                    throw new ExceptionRispostaNonValida(
                            "Errore nel fornire un informazione utile alla creazione dell'evento");
                }

                System.out.println("Titolo evento: ");
                String titolo = scanner.nextLine().trim();
                if (!Evento.isTitoloEventoValido(titolo)) {
                    throw new ExceptionTitoloNonValido("Titolo non valido.");
                }

                System.out.println("Data evento (dd/mm/yyyy): ");
                LocalDate data = LocalDate.parse(scanner.nextLine(), Evento.dataFormattataNow());
                if (!Evento.isEventoValido(data)) {
                    throw new ExceptionDataFormat("La data dell'evento non può essere nel passato.");
                }

                System.out.println("Posti totali: ");
                int postiTotali = Integer.parseInt(scanner.nextLine());
                if (postiTotali <= 0) {
                    throw new ExceptionNumeroNegativo("Il numero di posti deve essere positivo.");
                }

                if (tipo.equals("si")) {
                    System.out.println("Ora concerto (HH:mm): ");
                    LocalTime ora = LocalTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

                    System.out.println("Prezzo biglietto: ");
                    BigDecimal prezzo = new BigDecimal(scanner.nextLine().replace(",", "."));
                    if (prezzo.compareTo(BigDecimal.ZERO) < 0) {
                        throw new ExceptionPrezzoNegativo("Il prezzo non può essere negativo.");
                    }

                    return new Concerto(titolo, data, postiTotali, ora, prezzo);
                } else {
                    return new Evento(titolo, data, postiTotali);
                }

            } catch (ExceptionTitoloNonValido | ExceptionDataFormat | ExceptionNumeroNegativo | ExceptionPrezzoNull
                    | ExceptionPrezzoNegativo | ExceptionOraLive e) {
                System.out.println("Errore: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Errore: formato numerico non valido. Inserisci un numero corretto.");
            } catch (Exception e) {
                System.out.println("Errore imprevisto: " + e.getMessage());
            }
        }
    }

    private static void gestisciPrenotazioni(Evento evento)throws ExceptionDataPassata {
        while (true) {
            try {
                System.out.println("Quanti posti vuoi prenotare per questo evento? ");
                int posti = Integer.parseInt(scanner.nextLine());

                if (posti <= 0)
                    throw new ExceptionNumeroNegativo("Inserisci un numero positivo di posti.");
                if (posti > (evento.getPostiTotali() - evento.getPostiPrenotati())) {
                    throw new ExceptionNumeroEccessivo("Posti richiesti superiori alla disponibilità.");
                }

                for (int i = 0; i < posti; i++) {
                    evento.prenotaPosto();
                }
                System.out.println("Prenotazione completata. Posti prenotati: " + evento.getPostiPrenotati());
                return;

            } catch (NumberFormatException e) {
                System.out.println("Errore: inserisci un numero intero valido.");
            } catch (ExceptionNumeroNegativo | ExceptionNumeroEccessivo | ExceptionPostiPrenotabili e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    private static void gestisciDisdette(Evento evento) throws ExceptionDataPassata {
        if (evento.getPostiPrenotati() == 0)
            return;

        while (true) { 
            System.out.println("Vuoi disdire dei posti? (si/no): ");
            String risposta = scanner.nextLine().trim().toLowerCase();
            if (!risposta.equals("si") && !risposta.equals("no")) { 
                System.out.println("Errore: la risposta deve essere 'si' o 'no'.");
                continue; 
            }
            if (!risposta.equals("si")) 
                return;

            while (true) {
                try {
                    System.out.println("Quanti posti vuoi disdire? ");
                    int disdette = Integer.parseInt(scanner.nextLine());

                    if (disdette <= 0)
                        throw new ExceptionNumeroNegativo("Inserisci un numero positivo.");
                    if (disdette > evento.getPostiPrenotati()) {
                        throw new ExceptionNumeroEccessivo("Non puoi disdire più posti di quanti ne hai prenotati.");
                    }

                    for (int i = 0; i < disdette; i++) {
                        evento.disdiciPrenotazione();
                    }
                    System.out.println("Disdette effettuate. Posti prenotati rimanenti: " + evento.getPostiPrenotati());
                    return;

                } catch (NumberFormatException e) {
                    System.out.println("Errore: inserisci un numero intero valido.");
                } catch (ExceptionNumeroNegativo | ExceptionNumeroEccessivo | ExceptionNessunaPrenotazione e) {
                    System.out.println("Errore: " + e.getMessage());
                }
            }
        }
    }
}
