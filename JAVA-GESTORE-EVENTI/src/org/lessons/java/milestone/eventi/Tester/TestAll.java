package org.lessons.java.milestone.eventi.Tester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.lessons.java.milestone.eventi.Concerto;
import org.lessons.java.milestone.eventi.Evento;
import org.lessons.java.milestone.eventi.ProgrammaEventi;

public class TestAll {
    public static void main(String[] args) {
        System.out.println("=== TEST EVENTO ===");
        testEvento();

        System.out.println("=== TEST CONCERTO ===");
        testConcerto();

        System.out.println("=== TEST PROGRAMMA EVENTI ===");
        testProgrammaEventi();
    }

    public static void testEvento() {
        try {
            Evento e = new Evento("Concerto Jazz", LocalDate.of(2025, 7, 10), 100);
            System.out.println(e.getInfoEvento());

            e.prenotaPosto();
            e.prenotaPosto();
            System.out.println("hai eseguito tot prenotazioni: " + e.getInfoEvento());

            e.disdiciPrenotazione();
            System.out.println("Dopo una disdetta: " + e.getInfoEvento());
        } catch (Exception ex) {
            System.out.println("Errore nel test Evento: " + ex.getMessage());
        }
    }

    public static void testConcerto() {
        try {
            Concerto c = new Concerto("Metallica Live", LocalDate.of(2025, 9, 15), 5000, LocalTime.of(21, 0), new BigDecimal("89.99"));
            c.prenotaPosto();
            System.out.println(c.getInfoEvento());

            System.out.println(c);
            System.out.println("Data e ora: " + c.getDataOraFormattata());
            System.out.println("Prezzo biglietto: " + c.getPrezzoFormattato());
        } catch (Exception ex) {
            System.out.println("Errore nel test Concerto: " + ex.getMessage());
        }
    }

    public static void testProgrammaEventi() {
        try {
            ProgrammaEventi programma = new ProgrammaEventi("Eventi Estate 2025");

            Evento e1 = new Evento("Festa in Piazza", LocalDate.of(2025, 6, 20), 200);
            Evento e2 = new Evento("Cinema all'aperto", LocalDate.of(2025, 6, 20), 150);
            Evento e3 = new Evento("Teatro sotto le stelle", LocalDate.of(2025, 7, 5), 100);

            programma.aggiungiEvento(e1);
            programma.aggiungiEvento(e2);
            programma.aggiungiEvento(e3);

            System.out.println("Numero eventi: " + programma.getNumeroEventi());

            System.out.println("Eventi del 20/06/2025:");
            for (Evento e : programma.getEventiInData(LocalDate.of(2025, 6, 20))) {
                System.out.println(e);
            }

            System.out.println("Tutti gli eventi ordinati:");
            System.out.println(programma.stampaEventiPerData());

            programma.svuotaEventi();
            System.out.println("Dopo svuotamento: numero eventi = " + programma.getNumeroEventi());

        } catch (Exception ex) {
            System.out.println("Errore nel test ProgrammaEventi: " + ex.getMessage());
        }
    }
}
