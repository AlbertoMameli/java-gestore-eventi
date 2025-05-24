package org.lessons.java.milestone.eventi;

import java.time.LocalDate;

public class Testing {

    public static void main(String[] args) {
        Evento concertoVasco = new Evento("ConcertoVasco", LocalDate.of(2025, 6, 25), 65558);
        System.out.println(concertoVasco);

        concertoVasco.prenotaPosto();
        System.out.println(concertoVasco.getPostiPrenotati());
        concertoVasco.prenotaPosto();
        System.out.println(concertoVasco.getPostiPrenotati());

        concertoVasco.disdiciPrenotazione();
        System.out.println(concertoVasco.getPostiPrenotati());
        concertoVasco.disdiciPrenotazione();
        System.out.println(concertoVasco.getPostiPrenotati());
        concertoVasco.disdiciPrenotazione();
        System.out.println(concertoVasco.getPostiPrenotati());
    }

    
}
