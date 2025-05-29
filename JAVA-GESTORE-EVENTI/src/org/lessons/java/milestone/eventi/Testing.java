package org.lessons.java.milestone.eventi;

import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inserisci il titolo del programma di eventi: ");
        String titoloProgramma = scanner.nextLine();
        ProgrammaEventi programma = new ProgrammaEventi(titoloProgramma);

        scanner.close();

    }

}
