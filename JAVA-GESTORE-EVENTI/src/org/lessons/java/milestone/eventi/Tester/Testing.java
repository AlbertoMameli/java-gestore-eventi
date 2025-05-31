package org.lessons.java.milestone.eventi.Tester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import org.lessons.java.milestone.eventi.Concerto;
import org.lessons.java.milestone.eventi.Evento;
import org.lessons.java.milestone.eventi.ProgrammaEventi;

public class Testing {
    public static void main(String[] args) {

        Concerto vasco = new Concerto("Vasco", LocalDate.of(2025, 06, 12), 6000, LocalTime.of(21, 30), new BigDecimal(22.33));
        System.out.println(vasco);
       
}
}
