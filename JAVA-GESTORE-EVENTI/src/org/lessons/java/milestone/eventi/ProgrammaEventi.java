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
        if (!Evento.isEventoValido(evento.getData())) {
        throw new IllegalArgumentException("Non puoi aggiungere eventi con data passata.");
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
       
        return titolo + "\n" 
                + eventi.stream() 
                        .sorted(Comparator.comparing(Evento::getData))
                        .map(Evento::toString)
                        .collect(Collectors.joining("\n")); 
                                                            /*Il metodo stampaEventiPerData restituisce una stringa formattata con il titolo 
                                                            del programma e tutti gli eventi ordinati per data, uno per riga.
Uso le Stream API per migliorare leggibilità.. trasformo la lista di eventi in uno stream, la ordino per data con Comparator.comparing, 
poi converto ogni evento in una stringa e unisco il tutto con Collectors.joining.
*/
    }
}