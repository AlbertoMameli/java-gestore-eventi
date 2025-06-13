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

    public static void main(String[] args) { // Metodo principale, punto di ingresso dell'applicazione
        Scanner scanner = new Scanner(System.in); // Crea un oggetto Scanner per leggere l'input dell'utente
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Definisce il formato della data
                                                                                 // (giorno/mese/anno)

        try { // Inizia un blocco try per catturare potenziali eccezioni durante la creazione
              // dell'evento
            System.out.println("Inserisci il titolo dell'evento:"); // Chiede all'utente di inserire il titolo
            String titolo = scanner.nextLine(); // Legge il titolo inserito dall'utente

            // Valida il titolo: non deve essere nullo o vuoto (dopo aver rimosso spazi
            // bianchi iniziali/finali)
            if (titolo == null || titolo.trim().isEmpty()) {
                throw new ExceptionTitoloNonValido("Il titolo dell'evento non può essere vuoto."); // Lancia l'eccezione
                                                                                                   // se il titolo non è
                                                                                                   // valido
            }

            LocalDate data = null; // Inizializza la variabile data a null

            // Ciclo per assicurarsi che la data inserita sia valida e nel futuro
            while (data == null) {
                System.out.println("Inserisci la data (gg/mm/yyyy):"); // Chiede all'utente di inserire la data
                try {
                    data = LocalDate.parse(scanner.nextLine(), formatter); // Tenta di convertire la stringa in un
                                                                           // oggetto LocalDate con il formato
                                                                           // specificato
                    if (data.isBefore(LocalDate.now())) { // Controlla se la data inserita è precedente alla data
                                                          // odierna
                        throw new ExceptionDataFormat("La data non può essere nel passato."); // Lancia l'eccezione se
                                                                                              // la data è nel passato
                    }
                } catch (DateTimeParseException e) { // Cattura l'eccezione se il formato della data non è corretto
                    System.out.println("Formato data non valido. Riprova (gg/mm/yyyy)."); // Stampa un messaggio di
                                                                                          // errore
                    // Non è necessario lanciare ExceptionDataFormat qui, la stampa è sufficiente
                    // per far ripetere il ciclo
                } catch (ExceptionDataFormat e) { // Cattura l'eccezione personalizzata se la data è nel passato
                    System.out.println(e.getMessage()); // Stampa il messaggio dell'eccezione
                    data = null; // Reimposta data a null per far ripetere il ciclo di input
                }
            }

            int postiTotali = 0; // Inizializza la variabile per il numero totale di posti

            // Ciclo per assicurarsi che il numero di posti totali sia valido (positivo)
            while (postiTotali <= 0) {
                System.out.println("Inserisci il numero di posti totali (positivo):"); // Chiede all'utente i posti
                                                                                       // totali
                try {
                    postiTotali = Integer.parseInt(scanner.nextLine()); // Tenta di convertire l'input in un intero
                    if (postiTotali <= 0) { // Controlla se il numero di posti è minore o uguale a zero
                        throw new ExceptionNumeroNegativo("Il numero di posti totali deve essere maggiore di 0."); // Lancia
                                                                                                                   // l'eccezione
                                                                                                                   // se
                                                                                                                   // non
                                                                                                                   // è
                                                                                                                   // positivo
                    }
                } catch (NumberFormatException e) { // Cattura l'eccezione se l'input non è un numero intero
                    System.out.println("Input non valido. Inserisci un numero intero."); // Stampa un messaggio di
                                                                                         // errore
                } catch (ExceptionNumeroNegativo e) { // Cattura l'eccezione personalizzata per numero negativo
                    System.out.println(e.getMessage()); // Stampa il messaggio dell'eccezione
                }
            }

            // Creazione dell'oggetto Evento dopo che tutti i dati sono stati validati
            Evento evento = new Evento(titolo, data, postiTotali);
            System.out.println(evento.getInfoEvento()); // Stampa le informazioni iniziali dell'evento

            // Chiamata ai metodi per gestire prenotazioni e disdette
            gestisciPrenotazioni(evento, scanner); // Chiama il metodo per gestire le prenotazioni

            // Chiama le disdette solo se ci sono prenotazioni
            if (evento.getPostiPrenotati() > 0) {
                gestisciDisdette(evento, scanner);
            } else {
                System.out.println("Nessuna prenotazione effettuata.");
            }

            System.out.println("Ecco le info aggiornate: " + evento.getInfoEvento()); // Stampa le informazioni
                                                                                      // aggiornate dell'evento
            System.out.println(evento.toString()); // Stampa la rappresentazione stringa dell'evento

        } catch (ExceptionTitoloNonValido e) { // Cattura l'eccezione se il titolo non è valido
            System.err.println("Errore nella creazione dell'evento: " + e.getMessage()); // Stampa l'errore sul flusso
                                                                                         // di errore
        } catch (ExceptionDataFormat e) { // Cattura l'eccezione se c'è un problema con il formato/validità della data
            System.err.println("Errore nella data dell'evento: " + e.getMessage()); // Stampa l'errore sul flusso di
                                                                                    // errore
        } catch (ExceptionNumeroNegativo e) { // Cattura l'eccezione se i posti totali sono negativi o zero
            System.err.println("Errore nei posti totali: " + e.getMessage()); // Stampa l'errore sul flusso di errore
        } catch (Exception e) { // Cattura qualsiasi altra eccezione generica non prevista
            System.err.println(
                    "Si è verificato un errore imprevisto durante la creazione dell'evento: " + e.getMessage()); // Stampa
                                                                                                                 // un
                                                                                                                 // messaggio
                                                                                                                 // di
                                                                                                                 // errore
                                                                                                                 // generico
        } finally { // Blocco finally che viene eseguito sempre, indipendentemente dalle eccezioni
            scanner.close(); // Chiude l'oggetto Scanner per rilasciare le risorse
        }
    }

    // Metodo per Gestire le Prenotazioni
    public static void gestisciPrenotazioni(Evento evento, Scanner scanner)
            throws ExceptionDataFormat, ExceptionPostiPrenotabili, ExceptionNumeroNegativo, ExceptionNumeroEccessivo {
        String risposta;
        // utilizzo do-wile cosida creare un loop finche non iserisce si o no
        do {
            System.out.println("Vuoi prenotare dei posti? (si/no)"); // Chiede all'utente se vuole prenotare
            risposta = scanner.nextLine().trim(); // Legge la risposta e rimuove spazi bianchi
            if (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no")) {
                System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
            }
        } while (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no"));
        // --- FINE MODIFICA ---

        if (risposta.equalsIgnoreCase("si")) { // Se l'utente risponde "si"
            // Calcola i posti attualmente disponibili (posti totali meno posti già
            // prenotati)
            int postiDisponibili = evento.getPostiTotali() - evento.getPostiPrenotati();

            // ... (resto del codice per le prenotazioni) ...
            int numPosti = 0; // Variabile per immagazzinare il numero di posti da prenotare

            // Ciclo per ottenere un numero valido di posti da prenotare
            while (true) {
                System.out.println("Posti disponibili attuali: " + postiDisponibili); // Mostra i posti disponibili
                System.out.println("Quanti posti vuoi prenotare?"); // Chiede quanti posti prenotare
                try {
                    numPosti = Integer.parseInt(scanner.nextLine()); // Tenta di convertire l'input in un intero

                    // Controlli di validazione e lancio delle eccezioni personalizzate
                    if (numPosti <= 0) { // Se il numero è minore o uguale a zero
                        throw new ExceptionNumeroNegativo("Inserisci un numero positivo per la prenotazione."); // Lancia
                        // l'eccezione
                    } else if (numPosti > postiDisponibili) { // Se il numero supera i posti disponibili
                        throw new ExceptionNumeroEccessivo(
                                "Non puoi prenotare più di " + postiDisponibili + " posti disponibili."); // Lancia
                        // l'eccezione
                    } else {
                        break; // Se l'input è valido, esce dal ciclo
                    }
                } catch (NumberFormatException e) { // Cattura l'eccezione se l'input non è un numero
                    System.out.println("Input non valido. Inserisci un numero intero."); // Stampa un messaggio di
                    // errore
                } catch (ExceptionNumeroNegativo e) { // Cattura l'eccezione per numero negativo
                    System.out.println(e.getMessage()); // Stampa il messaggio dell'eccezione
                } catch (ExceptionNumeroEccessivo e) { // Cattura l'eccezione per numero eccessivo
                    System.out.println(e.getMessage()); // Stampa il messaggio dell'eccezione
                }
            }

            // Esegue le prenotazioni una per una, ora che il numero è valido
            for (int i = 0; i < numPosti; i++) {
                try {
                    evento.prenotaPosto(); // Chiama il metodo per prenotare un singolo posto nell'oggetto Evento
                } catch (ExceptionPostiPrenotabili e) { // Cattura l'eccezione se non ci sono più posti prenotabili
                    System.out.println("Errore prenotazione: " + e.getMessage()); // Stampa il messaggio d'errore
                    break; // Interrompe il ciclo di prenotazioni se un errore si verifica (es. evento
                    // pieno)
                } catch (Exception e) { // Cattura qualsiasi altra eccezione generica
                    System.out.println("Errore imprevisto durante la prenotazione: " + e.getMessage()); // Stampa un
                    // messaggio di
                    // errore
                    // generico
                    break; // Interrompe il ciclo in caso di errore imprevisto
                }
            }
            // Stampa lo stato aggiornato delle prenotazioni
            System.out.println("Posti prenotati: " + evento.getPostiPrenotati()
                    + ". Posti disponibili rimanenti: " + (evento.getPostiTotali() - evento.getPostiPrenotati()));
        }
    }

    // Metodo per Gestire le Disdette

    // Metodo per Gestire le Disdette
    public static void gestisciDisdette(Evento evento, Scanner scanner)
            throws ExceptionPostiPrenotabili, ExceptionDataFormat, ExceptionNumeroNegativo, ExceptionNumeroEccessivo,
            ExceptionNessunaPrenotazione {
        String risposta;

        do {
            System.out.println("Vuoi disdire delle prenotazioni? (si/no)"); // Chiede all'utente se vuole disdire
            risposta = scanner.nextLine().trim(); // Legge la risposta e rimuove spazi bianchi
            if (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no")) {
                System.out.println("Risposta non valida. Inserisci 'si' o 'no'.");
            }
        } while (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no"));
        // --- FINE MODIFICA ---

        if (risposta.equalsIgnoreCase("si")) { // Se l'utente risponde "si"
            int maxDisdette = evento.getPostiPrenotati(); // Il numero massimo di disdette è pari ai posti già prenotati
            // ... (resto del codice per le disdette) ...
            int numPosti = 0; // Variabile per immagazzinare il numero di posti da disdire

            // Ciclo per ottenere un numero valido per le disdette
            while (true) {
                System.out.println("Posti prenotati attuali: " + maxDisdette); // Mostra i posti attualmente prenotati
                System.out.println("Quanti posti vuoi disdire?"); // Chiede quanti posti disdire
                try {
                    numPosti = Integer.parseInt(scanner.nextLine()); // Tenta di convertire l'input in un intero

                    // Controlli di validazione e lancio delle eccezioni personalizzate
                    if (numPosti <= 0) { // Se il numero è minore o uguale a zero
                        throw new ExceptionNumeroNegativo("Inserisci un numero positivo per le disdette."); // Lancia
                        // l'eccezione
                    } else if (numPosti > maxDisdette) { // Se il numero supera i posti prenotati attuali
                        throw new ExceptionNumeroEccessivo(
                                "Non puoi disdire più di " + maxDisdette + " posti già prenotati."); // Lancia
                        // l'eccezione
                    } else {
                        break; // Se l'input è valido, esce dal ciclo
                    }
                } catch (NumberFormatException e) { // Cattura l'eccezione se l'input non è un numero
                    System.out.println("Input non valido. Inserisci un numero intero."); // Stampa un messaggio di
                    // errore
                } catch (ExceptionNumeroNegativo e) { // Cattura l'eccezione per numero negativo
                    System.out.println(e.getMessage()); // Stampa il messaggio dell'eccezione
                } catch (ExceptionNumeroEccessivo e) { // Cattura l'eccezione per numero eccessivo
                    System.out.println(e.getMessage()); // Stampa il messaggio dell'eccezione
                }
            }

            // Esegue le disdette una per una, ora che il numero è valido
            for (int i = 0; i < numPosti; i++) {
                try {
                    // Chiama il metodo 'disdiciPrenotazione()' dell'oggetto Evento
                    // Questo metodo nella classe Evento dovrebbe diminuire i posti prenotati
                    // e lanciare un'eccezione se non ci sono posti da disdire
                    evento.disdiciPrenotazione();
                } catch (ExceptionNessunaPrenotazione e) { // Cattura la nuova eccezione se non ci sono prenotazioni da
                    // disdire
                    System.out.println("Errore disdetta: " + e.getMessage()); // Stampa il messaggio d'errore
                    break; // Interrompe il ciclo di disdette se un errore si verifica
                } catch (Exception e) { // Cattura qualsiasi altra eccezione generica
                    System.out.println("Errore imprevisto durante la disdetta: " + e.getMessage()); // Stampa un
                    // messaggio di
                    // errore generico
                    break; // Interrompe il ciclo in caso di errore imprevisto
                }
            }
            // Stampa lo stato aggiornato delle disdette
            System.out.println("Disdette effettuate. Posti prenotati rimanenti: " + evento.getPostiPrenotati());
        }
    }
}