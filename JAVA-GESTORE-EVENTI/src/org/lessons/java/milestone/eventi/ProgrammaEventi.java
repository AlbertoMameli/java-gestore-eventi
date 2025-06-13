package org.lessons.java.milestone.eventi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

import java.util.stream.Collectors;

public class ProgrammaEventi {
    private String titolo;
    private List<Evento> eventi; // lista interna di eventi

    public ProgrammaEventi(String titolo) {
        // Validazione iniziale del titolo nel costruttore
        if (titolo == null || titolo.isBlank()) {
            throw new IllegalArgumentException("Il titolo del programma non può essere vuoto o nullo.");
        }
        this.titolo = titolo;
        // Inizializza la lista come un nuovo ArrayList vuoto
        this.eventi = new ArrayList<>();
    }

    public void aggiungiEvento(Evento evento) {
        // Validazione per assicurarsi che l'evento da aggiungere non sia nullo.

        if (evento == null) {
            throw new IllegalArgumentException("L'evento da aggiungere non può essere nullo.");
        }
        eventi.add(evento); // Aggiunge l'evento alla lista interna
    }

    public List<Evento> getEventiInData(LocalDate data) {
        List<Evento> eventiInData = new ArrayList<>(); // Crea una nuova lista per i risultati filtrati
        for (Evento e : eventi) {
            if (e.getData().equals(data)) { // Confronta le date utilizzando .equals()
                eventiInData.add(e);
            }
        }
        return eventiInData; // Restituisce una nuova lista con i risultati.
    }

    public int getNumeroEventi() {
        return eventi.size(); // Delega all'ArrayList il conteggio degli elementi.
    }

    public void svuotaEventi() {
        eventi.clear(); // Utilizza il metodo clear() di ArrayList per svuotare la collezione.
    }

    // --- Getter e Setter ---

    public String getTitolo() {
        return this.titolo;
    }

    public void setTitolo(String titolo) {

        if (titolo == null || titolo.isBlank()) {
            throw new IllegalArgumentException("Il titolo del programma non può essere vuoto o nullo.");
        }
        this.titolo = titolo;
    }

    public List<Evento> getEventi() {
        // Utilizziamo Collections.unmodifiableList() per creare una "foto" non
        // modificabile della lista interna.
        // impedisce al codice esterno di modificare direttamente la lista degli eventi,

        return Collections.unmodifiableList(this.eventi);
    }

    public String stampaEventiPerData() {
        // Utilizza le API Stream per un'elaborazione concisa: (leggibile, più
        // sintetizzato il codice)
        return titolo + "\n" // Inizia con il titolo del programma
                + eventi.stream() // prende la lista eventi e la trasforma in uno strem, ma non esegue nulla, sta
                                  // solo preparando alle varie operazioniche si possono fare, tipo map sorted etc..
                        .sorted(Comparator.comparing(Evento::getData))// Ordina gli eventi in base alla loro data
                        .map(Evento::toString)// Trasforma ogni oggetto Evento in una "stringa"
                        // ora dopo aver eseguito le operazioni intermedie abbiamo bisogno di un
                        // operazione "terminale", cioè trasformiamo il nostro strem
                        // in ciò che gli abbamo chiesto di trasormarsi
                        .collect(Collectors.joining("\n")); // Unisce tutte le stringhe degli eventi in un'unica stringa
                                                            // e qui che avvengono le esecuzioni delle operazioni
                                                            // precedemti

                                                            /*Il metodo stampaEventiPerData restituisce una stringa formattata con il titolo 
                                                            del programma e tutti gli eventi ordinati per data, uno per riga.
Uso le Stream API per migliorare leggibilità e concisione: trasformo la lista di eventi in uno stream, la ordino per data con Comparator.comparing, 
poi converto ogni evento in una stringa e unisco il tutto con Collectors.joining("\n").
Il risultato è una stringa facilmente stampabile o esportabile, adatta ad esempio per una console, una mail o un file di log. */
    }
}