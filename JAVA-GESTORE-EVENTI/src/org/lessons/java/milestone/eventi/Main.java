package org.lessons.java.milestone.eventi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        

        System.out.println("Inserisci il titolo dell'evento: ");
        String titolo = scanner.nextLine();

        System.out.println("Inserisci la data del tuo evento gg/mm/yyyy: ");
        String dataString = scanner.nextLine();
        LocalDate data = LocalDate.parse(dataString, formatter);

        System.out.println("Inserisci il numero dei posti totali: ");
        int postiTotali = Integer.parseInt(scanner.nextLine());


       Evento  evento = new Evento(titolo, data, postiTotali);
       System.out.println(evento.getInfoEvento());
       

       //prenotazioni
       System.out.println("Vuoi effetturare delle prenotazioni? (si o no) ");
       String rispostaPrenotazione = scanner.nextLine();
       if (rispostaPrenotazione.equalsIgnoreCase("si")){
        System.out.println("Quanti posti voui prenotare? ");
        int postiDaPrenotare = Integer.parseInt(rispostaPrenotazione);
       }


        scanner.close();
    }
    
}
