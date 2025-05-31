package org.lessons.java.milestone.eventi.Tester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.lessons.java.milestone.eventi.Concerto;
import org.lessons.java.milestone.eventi.Evento;
import org.lessons.java.milestone.eventi.ProgrammaEventi;

public class TestUser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------Creiamo un programma eventi!!!------------");

        System.out.println("Inserisci il titolo del tuo programma di eventi!");
        String nomeProgramma = scanner.nextLine();

        ProgrammaEventi programma = new ProgrammaEventi(nomeProgramma);

        System.out.println("Quanti eventi vuoi inserire?");
        int numeroEventi = Integer.parseInt(scanner.nextLine());

        // ciclo for per chiedere più volte all utenute di inserire eventi
        for (int i = 0; i < numeroEventi; i++) {
            System.out.println("Inserisci i dati per l'evento: " + (i + 1));

            try {
                System.out.println("E' un concerto? (si/no)");
                String rispostaTipologia = scanner.nextLine();

                System.out.println("Titolo evento: ");
                String titoloEvento = scanner.nextLine();

                System.out.println("Data dell'evento: (dd/mm/yyyy) ");
                String dataStr = scanner.nextLine();
                LocalDate data = LocalDate.parse(dataStr, Evento.getDataFormattata());

                System.out.print("Posti totali: ");
                int postiTotali = Integer.parseInt(scanner.nextLine());

                // controlli con eccezioni

                if (rispostaTipologia.equals("si")) {
                    System.out.println("Ora del conerto : (hh::mm)");
                    String oraStr = scanner.nextLine();
                    LocalTime oraConcerto = LocalTime.parse(oraStr, DateTimeFormatter.ofPattern("HH:mm"));

                    System.out.println("Inserisci il prezzo del biglietto : (##.##)");
                    BigDecimal prezzoTicketConcerto = new BigDecimal(scanner.nextLine());

                    Concerto conertino = new Concerto(titoloEvento, data, postiTotali, oraConcerto,
                            prezzoTicketConcerto);
                    programma.aggiungiEvento(conertino);

                } else {
                    Evento eventino = new Evento(titoloEvento, data, postiTotali);
                    programma.aggiungiEvento(eventino);
                }

            } catch (Exception e) {
                System.out.println("Errore nella creazione dell'evento " + e.getMessage());
                i--; //torna indietro nel ciclo
                System.out.println("Inserisci nuovamente l'evento");

            }
        }

        System.out.println("Programma : ");
        System.out.println(programma.stampaEventiPerData());

        scanner.close();
    }

}
