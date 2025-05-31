package org.lessons.java.milestone.eventi;

import java.time.LocalDate;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il titolo del programma eventi");
        String titoloProgramma = scanner.nextLine().toUpperCase();
        ProgrammaEventi programma = new ProgrammaEventi(titoloProgramma);

        System.out.println("Quanti eventi vuoi inserire?");
        int numeroEventi = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numeroEventi; i++) {
            System.out.println("Evento : " + (i+1));

            System.out.println("Titolo evento: ");
            String titoloEvento = scanner.nextLine();

            System.out.println("Inserisci la data dell'evento : (dd/MM/yyyy)");
            String dataStr = scanner.nextLine();
            LocalDate data = LocalDate.parse(dataStr, Evento.getDataFormattata());

            System.out.println("Inserisci i posti totali");
            int postTotali = Integer.parseInt(scanner.nextLine());

            try {
                Evento eventuccio = new Evento(titoloEvento, data, postTotali);
                programma.aggiungiEvento(eventuccio);

            } catch (Exception e) {
                System.out.println("Errore nella crazione evento : " + e.getMessage());

            }
        }

        System.out.println("Ecco il programma : ");
        System.out.println(programma.stampaEventiPerData());
        scanner.close();
    }

}
