package org.lessons.java.milestone.eventi.Tester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.lessons.java.milestone.eventi.Concerto;
import org.lessons.java.milestone.eventi.Evento;
import org.lessons.java.milestone.eventi.ProgrammaEventi;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionPrezzoNull;

public class Testing {
    public static void main(String[] args) {
        try {
            Concerto c = new Concerto("Vasco on the road", LocalDate.of(2025, 8, 12), 54555, LocalTime.of(21, 0),
                    new BigDecimal(29.99));
            ProgrammaEventi lista = new ProgrammaEventi("Lista Eventi");
            lista.aggiungiEvento(c);
            System.out.println("numero eventi " + lista.getNumeroEventi());
            System.out.println(lista.stampaEventiPerData());
            Evento eventuccio = new Evento("Concerto di natale", LocalDate.of(2025, 12, 25), 555);
            eventuccio.prenotaPosto();
            System.out.println(eventuccio.getInfoEvento());
            lista.aggiungiEvento(eventuccio);
            System.out.println("numero eventi " + lista.getNumeroEventi());
            System.out.println(lista.stampaEventiPerData());
            c.prenotaPosto();
            System.out.println(c.getInfoEvento());
            c.disdiciPrenotazione();
            System.out.println(c.getInfoEvento());

        } catch (ExceptionPrezzoNull e) {
            System.err.println("Hai inserito prezo non valido");
        } catch (Exception e) {
            System.err.println("Inserisci bene il prezzo");
        }

    }
}
