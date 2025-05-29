package org.lessons.java.milestone.eventi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            System.out.println("Inserisci il titolo dell'evento:");
            String titolo = scanner.nextLine();

            LocalDate data = null;
            while (data == null) {
                System.out.println("Inserisci la data (gg/MM/yyyy):");
                try {
                    data = LocalDate.parse(scanner.nextLine(), formatter);
                    if (data.isBefore(LocalDate.now())) {
                        System.out.println("La data non può essere nel passato.");
                        data = null;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Formato data non valido. Riprova.");
                }
            }

            int postiTotali = 0;
            while (postiTotali <= 0) {
                System.out.println("Inserisci il numero di posti totali (positivo):");
                try {
                    postiTotali = Integer.parseInt(scanner.nextLine());
                    if (postiTotali <= 0) {
                        System.out.println("Il numero deve essere maggiore di 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido.");
                }
            }

            Evento evento = new Evento(titolo, data, postiTotali);
            System.out.println(evento.getInfoEvento());

            gestisciPrenotazioni(evento, scanner);
            gestisciDisdette(evento, scanner);

            System.out.println("Ecco le info : " + evento.getInfoEvento());
            System.out.println(evento.toString());

        } catch (Exception e) {
            System.out.println("Errore imprevisto: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static void gestisciPrenotazioni(Evento evento, Scanner scanner) {
        System.out.println("Vuoi prenotare dei posti? (si/no)");
        String risposta = scanner.nextLine();

        if (risposta.equalsIgnoreCase("si")) {
            int postiDisponibili = evento.getPostiTotali() - evento.getPostiPrenotati();
            int numPosti = 0;

            while (true) {
                System.out.println("Posti disponibili: " + postiDisponibili);
                System.out.println("Quanti posti vuoi prenotare?");
                try {
                    numPosti = Integer.parseInt(scanner.nextLine());
                    if (numPosti <= 0) {
                        System.out.println("Inserisci un numero positivo.");
                    } else if (numPosti > postiDisponibili) {
                        System.out.println("Non puoi prenotare più di " + postiDisponibili + " posti.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido.");
                }
            }

            for (int i = 0; i < numPosti; i++) {
                try {
                    evento.prenotaPosto();
                } catch (RuntimeException e) {
                    System.out.println("Errore prenotazione: " + e.getMessage());
                    break;
                }
            }

            System.out.println("Prenotazioni effettuate. Posti prenotati: " + evento.getPostiPrenotati());
        }
    }

    public static void gestisciDisdette(Evento evento, Scanner scanner) {
        System.out.println("Vuoi disdire delle prenotazioni? (si/no)");
        String risposta = scanner.nextLine();

        if (risposta.equalsIgnoreCase("si")) {
            int maxDisdette = evento.getPostiPrenotati();
            int numPosti = 0;

            while (true) {
                System.out.println("Posti prenotati attuali: " + maxDisdette);
                System.out.println("Quanti posti vuoi disdire?");
                try {
                    numPosti = Integer.parseInt(scanner.nextLine());
                    if (numPosti <= 0) {
                        System.out.println("Inserisci un numero positivo.");
                    } else if (numPosti > maxDisdette) {
                        System.out.println("Non puoi disdire più di " + maxDisdette + " posti.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido.");
                }
            }

            for (int i = 0; i < numPosti; i++) {
                try {
                    evento.disdiciPrenotazione();
                } catch (RuntimeException e) {
                    System.out.println("Errore disdetta: " + e.getMessage());
                    break;
                }
            }

            System.out.println("Disdette effettuate. Posti rimanenti: " + evento.getPostiPrenotati());
        }
    }
}
