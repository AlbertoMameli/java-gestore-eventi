package org.lessons.java.milestone.eventi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// Importo tutte le classi di eccezione personalizzate 
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionDataFormat;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionNumeroEccessivo;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionNumeroNegativo;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionPostiPrenotabili;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionNessunaPrenotazione;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionTitoloNonValido;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//------------------------------------------------------------titolo
        try {
            String titolo = null;
            do {
                System.out.println("Inserisci il titolo dell'evento:");
                titolo = scanner.nextLine().trim();
                try {
                    if (!Evento.isTitoloEventoValido(titolo)) {
                        throw new ExceptionTitoloNonValido("Titolo non valido. Riprova.");

                    }
                } catch (ExceptionTitoloNonValido e) {
                    System.out.println(e.getMessage());
                    titolo = null;
                }
//------------------------------------data
            } while (titolo == null);

            LocalDate data = null;
            while (data == null) {
                System.out.println("Inserisci la data (gg/mm/yyyy):");
                try {
                    data = LocalDate.parse(scanner.nextLine(), formatter);
                    if (!Evento.isEventoValido(data)) {
                        throw new ExceptionDataFormat("La data non può essere nel passato.");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Formato data non valido. Riprova (gg/mm/yyyy).");
                } catch (ExceptionDataFormat e) {
                    System.out.println(e.getMessage());
                    data = null;
                }
            }
//-----------------------------------------posti totali
            int postiTotali = 0;

            while (postiTotali <= 0) {
                System.out.println("Inserisci il numero di posti totali (positivo):");
                try {
                    postiTotali = Integer.parseInt(scanner.nextLine());
                    if (postiTotali <= 0) {
                        throw new ExceptionNumeroNegativo("Il numero di posti totali deve essere maggiore di 0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input non valido. Inserisci un numero intero.");
                } catch (ExceptionNumeroNegativo e) {
                    System.out.println(e.getMessage());
                }
            }
//---------------------------creazione nuovo evento 
            Evento evento = new Evento(titolo, data, postiTotali);
            System.out.println(evento.getInfoEvento());

            gestisciPrenotazioni(evento, scanner);

            if (evento.getPostiPrenotati() > 0) {
                gestisciDisdette(evento, scanner);
            } else {
                System.out.println("Nessuna prenotazione effettuata.");
            }

            System.out.println("Ecco le info aggiornate: " + evento.getInfoEvento());
            System.out.println(evento.toString());

        } catch (ExceptionTitoloNonValido e) {
            System.err.println("Errore nella creazione dell'evento: " + e.getMessage());

        } catch (ExceptionDataFormat e) {
            System.err.println("Errore nella data dell'evento: " + e.getMessage());

        } catch (ExceptionNumeroNegativo e) {
            System.err.println("Errore nei posti totali: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(
                    "Si è verificato un errore imprevisto durante la creazione dell'evento: " + e.getMessage());
        } finally { //
            scanner.close();
        }
    }

    //--------------------------------------------- Metodo per Gestire le Prenotazioni
    public static void gestisciPrenotazioni(Evento evento, Scanner scanner)
            throws ExceptionDataFormat, ExceptionPostiPrenotabili, ExceptionNumeroNegativo, ExceptionNumeroEccessivo {
        String risposta;

        do {
            System.out.println("Vuoi prenotare dei posti? (si/no)");
            risposta = scanner.nextLine().trim();
            if (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no")) {
                System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
            }
        } while (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no"));

        if (risposta.equalsIgnoreCase("si")) {

            int postiDisponibili = evento.getPostiTotali() - evento.getPostiPrenotati();

            int numPosti = 0;

            while (true) {
                System.out.println("Posti disponibili attuali: " + postiDisponibili);
                System.out.println("Quanti posti vuoi prenotare?");
                try {
                    numPosti = Integer.parseInt(scanner.nextLine());

                    if (numPosti <= 0) {
                        throw new ExceptionNumeroNegativo("Inserisci un numero positivo per la prenotazione.");

                    } else if (numPosti > postiDisponibili) {
                        throw new ExceptionNumeroEccessivo(
                                "Non puoi prenotare più di " + postiDisponibili + " posti disponibili.");

                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input non valido. Inserisci un numero intero.");
                    // errore
                } catch (ExceptionNumeroNegativo e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionNumeroEccessivo e) {
                    System.out.println(e.getMessage());
                }
            }

            for (int i = 0; i < numPosti; i++) {
                try {
                    evento.prenotaPosto();
                } catch (ExceptionPostiPrenotabili e) {
                    System.out.println("Errore prenotazione: " + e.getMessage());
                    break;

                } catch (Exception e) {
                    System.out.println("Errore imprevisto durante la prenotazione: " + e.getMessage());
                    break;
                }
            }

            System.out.println("Posti prenotati: " + evento.getPostiPrenotati()
                    + ". Posti disponibili rimanenti: " + (evento.getPostiTotali() - evento.getPostiPrenotati()));
        }
    }

    //--------------------------------------------------------- Metodo per Gestire le Disdette
    public static void gestisciDisdette(Evento evento, Scanner scanner)
            throws ExceptionPostiPrenotabili, ExceptionDataFormat, ExceptionNumeroNegativo, ExceptionNumeroEccessivo,
            ExceptionNessunaPrenotazione {
        String risposta;

        do {
            System.out.println("Vuoi disdire delle prenotazioni? (si/no)");
            risposta = scanner.nextLine().trim();
            if (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no")) {
                System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
            }
        } while (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no"));
        

        if (risposta.equalsIgnoreCase("si")) {
            int maxDisdette = evento.getPostiPrenotati();
            int numPosti = 0;

            // ---------------------------------Ciclo per ottenere un numero valido per le disdette
            while (true) {
                System.out.println("Posti prenotati attuali: " + maxDisdette);
                System.out.println("Quanti posti vuoi disdire?");
                try {
                    numPosti = Integer.parseInt(scanner.nextLine());

                    if (numPosti <= 0) {
                        throw new ExceptionNumeroNegativo("Inserisci un numero positivo per le disdette.");

                    } else if (numPosti > maxDisdette) {
                        throw new ExceptionNumeroEccessivo(
                                "Non puoi disdire più di " + maxDisdette + " posti già prenotati.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input non valido. Inserisci un numero intero.");

                } catch (ExceptionNumeroNegativo e) {
                    System.out.println(e.getMessage());
                } catch (ExceptionNumeroEccessivo e) {
                    System.out.println(e.getMessage());
                }
            }

            for (int i = 0; i < numPosti; i++) {
                try {
                    evento.disdiciPrenotazione();
                } catch (ExceptionNessunaPrenotazione e) {

                    System.out.println("Errore disdetta: " + e.getMessage());
                    break;
                } catch (Exception e) {
                    System.out.println("Errore imprevisto durante la disdetta: " + e.getMessage());

                    break;
                }
            }
            // Stampa lo stato aggiornato delle disdette
            System.out.println("Disdette effettuate. Posti prenotati rimanenti: " + evento.getPostiPrenotati());
        }
    }
}