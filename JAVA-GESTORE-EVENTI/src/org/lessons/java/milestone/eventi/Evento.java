package org.lessons.java.milestone.eventi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento {
    // Variabili d'istanza
    private String titoloEvento;
    private int postiTotali;
    private int postiPrenotati;
    private LocalDate data;
    private DateTimeFormatter dataFormattata = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // formatto la data in giorno
                                                                                          // mese e anno con il pattern
                                                                                          // nostro..

    // validatori del mio evento

    public static boolean isTitoloEventoValido(String titoloEvento) {
        return titoloEvento != null && !titoloEvento.isBlank();
    }

    private boolean isEventoPassato() { // verifica se l'evento è gia passato o no
        LocalDate giornoSeguente = LocalDate.now(); // data
        return giornoSeguente.isAfter(this.data);
    }

    public static boolean isEventoValido(LocalDate data) { // verifica se l'evento trasformato in parametro si svolge in
                                                           // futuro
        LocalDate giornoSeguente = LocalDate.now(); // data di oggi
        return data.isAfter(giornoSeguente); // confronto con data ricevuta
    }

    public static boolean isCapienzaValida(int postiTotali) { // numeri postitotali deve essere maggiore di 0
        return postiTotali > 0;
    }

    // costruttore
    public Evento(String titoloEvento, LocalDate data, int postiTotali) throws RuntimeException {
        if (isTitoloEventoValido(titoloEvento)) {
            this.titoloEvento = titoloEvento;
        } else {
            throw new RuntimeException("Inserire il titolo!");
        }

        if (isEventoValido(data)) {
            this.data = data;
        } else {
            DateTimeFormatter formatta = DateTimeFormatter.ofPattern("dd/MM/YYYY");
            throw new RuntimeException("La data " + data.format(formatta)
                    + " non è valida, verifica che non sia precedente alla data odierna!");
        }

        if (isCapienzaValida(postiTotali)) {
            this.postiTotali = postiTotali;

        } else {
            throw new RuntimeException(
                    "Il numero di posti " + postiTotali + " non è valido, verificare che sia maggiore di 0.");

        }

        this.postiPrenotati = 0;

    }

    public String getTitoloEvento() {
        return this.titoloEvento;
    }

    public void setTitoloEvento(String titoloEvento) throws RuntimeException { // controllo cosa inserisce l'utente nel
                                                                               // mentre che si esegue il codice
        if (isTitoloEventoValido((titoloEvento))) {
            this.titoloEvento = titoloEvento;
        } else {
            throw new RuntimeException("Inserisci il titolo");
        }

    }

    public int getPostiTotali() {
        return this.postiTotali;
    }

    public void setPostiTotali(int postiTotali) {
        this.postiTotali = postiTotali;
    }

    public int getPostiPrenotati() {
        return this.postiPrenotati;
    }

    public void setPostiPrenotati(int postiPrenotati) throws RuntimeException {
        if (postiPrenotati <= postiTotali && postiPrenotati >= 0) {
            this.postiPrenotati = postiPrenotati;
        } else {
            throw new RuntimeException(
                    "Il numero di posti deve essere minore dei posti totali e non può essere infeririore a 0.");
        }

    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) throws RuntimeException {
        if (isEventoValido(data)) {
            this.data = data;
        } else {
            throw new RuntimeException("La data " + data.format(dataFormattata)
                    + " non è valida, verifica che non sia precedente alla data odierna!");
        }

    }

    public DateTimeFormatter getDataFormattata() {
        return this.dataFormattata;
    }

    public void setDataFormattata(DateTimeFormatter dataFormattata) {
        this.dataFormattata = dataFormattata;
    }

    // metodi

    public void prenotaPosto() { // se soddisfa le condizioni allora si può aggiungere una prenotazione
        if (isEventoPassato()) {
            throw new RuntimeException("L'evento è già passato, non è possibile prenotare.");
        }
        if (postiPrenotati >= postiTotali) {
            throw new RuntimeException("Posti esauriti, non è possibile prenotare");
        }
        postiPrenotati++;
    }

    public void disdiciPrenotazione() {
        if (isEventoPassato()) {
            throw new RuntimeException("L'evento è gia passato, non puoi disdire.");
        }
        if (postiPrenotati <= 0) {
            throw new RuntimeException("Non è possibile disdire, devi prima prenotare.");
        }
        postiPrenotati--;

    }

    @Override
    public String toString() {
        return data.format(dataFormattata) + "-" + titoloEvento;
    }
}
