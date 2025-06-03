package org.lessons.java.milestone.eventi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento {
    // Variabili di istanza privati, in modo tale da non far accedere dirattamente, ma in lettura con i getter e in scrittura con i setter..
    private String titoloEvento;
    private final int POSTI_TOTALI; // final che non può cambiare una volta assegnato nel costruttore
    private int postiPrenotati;
    private LocalDate data;
    private static final DateTimeFormatter DATA_FORMATTATA = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // formatto la
                                                                                                        // data in
                                                                                                        // giorno mese e
                                                                                                        // anno con il
                                                                                                        // pattern
                                                                                                        // nostro..

    // costruttore.. prendiamo le nostre variabili e le passiamo al nostro costruttore in modo da instaziare il nostro oggetto
    public Evento(String titoloEvento, LocalDate data, int POSTI_TOTALI) {

        // metto delle condizioni per costrire il mio oggetto, in modo tale da crerare dei controlli utili per lanciare poi delle eccezioni 
        if (isTitoloEventoValido(titoloEvento)) {
            this.titoloEvento = titoloEvento;
        } else {
            throw new RuntimeException("Inserire il titolo!");
        }

        if (isEventoValido(data)) {
            this.data = data;
        } else {
            DateTimeFormatter formatta = DateTimeFormatter.ofPattern("dd/MM/YYYY"); // trasformo la stringa in un
                                                                                    // oggetto immutabile
            throw new RuntimeException("La data " + data.format(formatta)
                    + " non è valida, verifica che non sia precedente alla data odierna!");
        }

        if (isCapienzaValida(POSTI_TOTALI)) {
            this.POSTI_TOTALI = POSTI_TOTALI;

        } else {
            throw new RuntimeException(
                    "Il numero di posti " + POSTI_TOTALI + " non è valido, verificare che sia maggiore di 0.");

        }
        // inizializzo i posti prenotati

        this.postiPrenotati = 0;

    }
    // validatori del mio evento statici

    public static boolean isTitoloEventoValido(String titoloEvento) { // verifica che il titolo non sia vuoto o null
        return titoloEvento != null && !titoloEvento.isBlank(); // .trim().isempty()
    }

    public static boolean isEventoValido(LocalDate data) { // verifica se l'evento trasformato in parametro si svolge in
                                                           // futuro
        LocalDate giornoSeguente = LocalDate.now(); // data di oggi //
        return !data.isBefore(giornoSeguente); // confronto con data ricevuta
    }

    public static boolean isCapienzaValida(int POSTI_TOTALI) { // numeri postitotali deve essere maggiore di 0
        return POSTI_TOTALI > 0;
    }

    // Getter e Setter

    public String getTitoloEvento() {
        return this.titoloEvento;
    }

    public void setTitoloEvento(String titoloEvento) { // controllo cosa inserisce l'utente nel
                                                       // mentre che si esegue il codice
        if (isTitoloEventoValido((titoloEvento))) {
            this.titoloEvento = titoloEvento;
        } else {
            throw new RuntimeException("Inserisci il titolo");
        }

    }

    public int getPostiTotali() {
        return this.POSTI_TOTALI;
    }

    public int getPostiPrenotati() {
        return this.postiPrenotati;
    }

    public void setPostiPrenotati(int postiPrenotati) {
        if (postiPrenotati <= POSTI_TOTALI && postiPrenotati >= 0) {
            this.postiPrenotati = postiPrenotati;
        } else {
            throw new RuntimeException(
                    "Il numero di posti deve essere minore dei posti totali e non può essere infeririore a 0.");
        }

    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        if (isEventoValido(data)) {
            this.data = data;
        } else {
            throw new RuntimeException("La data " + data.format(DATA_FORMATTATA)
                    + " non è valida, verifica che non sia precedente alla data odierna!");
        }

    }

    // metodi

    // ottengo la mia data formattata utilizzabile in altre classi
    public static DateTimeFormatter getDataFormattata() {
        return DATA_FORMATTATA;
    }

    private boolean isEventoPassato() { // verifica se l'evento è gia passato o no... da implementare nei metodi prenota
                                        // e disdici
        LocalDate giornoSeguente = LocalDate.now();
        return giornoSeguente.isAfter(this.data);
    }

    public void prenotaPosto() { // se soddisfa le condizioni allora si può aggiungere una prenotazione
        if (isEventoPassato()) {
            throw new RuntimeException("L'evento è già passato, non è possibile prenotare.");
        }
        if (postiPrenotati >= POSTI_TOTALI) { // verifico se ci sono disponibili
            throw new RuntimeException("Posti esauriti, non è possibile prenotare");
        }
        //incrementiamo se le condizioni sono favorevoli
        postiPrenotati++;
    }

    public void disdiciPrenotazione() {
        if (isEventoPassato()) {
            throw new RuntimeException("L'evento è gia passato, non puoi disdire.");
        }
        if (postiPrenotati <= 0) {
            throw new RuntimeException("Non è possibile disdire, devi prima prenotare.");
        }
        // se le condizioni sono soddisfatte, diminuiamo il numero di prenotazioni
        postiPrenotati--;

    }

    public String getInfoEvento() {
        return "Evento: " + titoloEvento + " | Data: " + data.format(DATA_FORMATTATA) + " | Posti prenotati: "
                + postiPrenotati + "/" + POSTI_TOTALI;
    }

    @Override
    public String toString() {
        return data.format(DATA_FORMATTATA) + "-" + titoloEvento.toUpperCase();
    }
}
