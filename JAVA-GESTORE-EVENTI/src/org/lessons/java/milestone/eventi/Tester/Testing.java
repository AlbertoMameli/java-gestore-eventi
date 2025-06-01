package org.lessons.java.milestone.eventi.Tester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.lessons.java.milestone.eventi.Concerto;
import org.lessons.java.milestone.eventi.Evento;
import org.lessons.java.milestone.eventi.ProgrammaEventi;

public class Testing {
    public static void main(String[] args) {

        Concerto vasco = new Concerto("Vasco", LocalDate.of(2025, 06, 12), 6000, LocalTime.of(21, 30), new BigDecimal(22.33));
        System.out.println(vasco);
       ProgrammaEventi lista = new ProgrammaEventi("Lista Eventi");
       lista.aggiungiEvento(vasco);
       System.out.println(lista.stampaEventiPerData());
       Evento eventuccio = new Evento("Concerto di natale",LocalDate.of(2025, 12, 25), 555);
       lista.aggiungiEvento(eventuccio);
       System.out.println(lista.stampaEventiPerData());
       vasco.prenotaPosto();
       System.out.println(vasco.getInfoEvento());
       vasco.disdiciPrenotazione();
       System.out.println(vasco.getInfoEvento());
}
}
