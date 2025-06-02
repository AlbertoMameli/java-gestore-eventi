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

            //inizio il mio ciclo cosi mi assicuro che la data sia valida 
            while (data == null) {
                System.out.println("Inserisci la data (gg/mm/yyyy):");
                try {
                    data = LocalDate.parse(scanner.nextLine(), formatter); //formato la stringa in data
                    if (data.isBefore(LocalDate.now())) { //controlla se la data è nel passato
                        System.out.println("La data non può essere nel passato.");
                        data = null;  //ritorna null per ripetere il mio input
                    }
                } catch (DateTimeParseException e) { // lancia ecezione se il formato data non è nel formato previsto
                    System.out.println("Formato data non valido. Riprova.");
                }
            }

            int postiTotali = 0;

            //ciclo per verificare che i posti siano 0+
            while (postiTotali <= 0) {
                System.out.println("Inserisci il numero di posti totali (positivo):");
                try {
                    postiTotali = Integer.parseInt(scanner.nextLine());  //trasforma la stringa in un intero
                    if (postiTotali <= 0) {
                        System.out.println("Il numero deve essere maggiore di 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido.");
                }
            }

            //creazione oggetto

            Evento evento = new Evento(titolo, data, postiTotali);
            System.out.println(evento.getInfoEvento()); 

            //chiamo i metodi di gestione e sto dicendo..
            //ehi java, per eseguire quessti metodi prendi gli oggetti evento e scanner

            gestisciPrenotazioni(evento, scanner); 
            gestisciDisdette(evento, scanner);

            System.out.println("Ecco le info : " + evento.getInfoEvento());
            System.out.println(evento.toString());

            //nel caso in cui dovesse verificarsi un errore imprevisto lancio questo messaggio

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

            //creo variabile per immaganizzare i numeri di posti
            int numPosti = 0;

            while (true) { 
                System.out.println("Posti disponibili: " + postiDisponibili);
                System.out.println("Quanti posti vuoi prenotare?");
                try {
                    numPosti = Integer.parseInt(scanner.nextLine());

                    // condizione di validazione dell input
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

            //ora con il ciclo for eseguo le prenotazione 

            for (int i = 0; i < numPosti; i++) {
                try {
                    evento.prenotaPosto(); // evoco il metodo prenotaposto per incrementare i posti prenotati e verifica eventuali condizioni
                } catch (RuntimeException e) {
                    System.out.println("Errore prenotazione: " + e.getMessage());
                    break;
                }
            }

            System.out.println("Prenotazioni effettuate. Posti prenotati: " + evento.getPostiPrenotati() + "/" +  postiDisponibili);
        }
    }

    public static void gestisciDisdette(Evento evento, Scanner scanner) {
        System.out.println("Vuoi disdire delle prenotazioni? (si/no)");
        String risposta = scanner.nextLine();

        if (risposta.equalsIgnoreCase("si")) {
            int maxDisdette = evento.getPostiPrenotati(); //numero max disdette = numero posti prenotati
            //variabile per immagazzinare il numero di posti da disdire 
            int numPosti = 0;

            //ciclo finche ottengo un numero valido per le disdette

            while (true) {
                System.out.println("Posti prenotati attuali: " + maxDisdette);
                System.out.println("Quanti posti vuoi disdire?");
                try {
                    numPosti = Integer.parseInt(scanner.nextLine());

                    //controlli 
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

            //ciclo le disdette una per una

            for (int i = 0; i < numPosti; i++) {
                try {
                     // Chiama il metodo 'disdiciPrenotazione()' dell'oggetto Evento
                // Questo metodo all'interno della classe Evento si occuperà di diminuire i posti prenotati
                // e di verificare eventuali condizioni erratte...
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
