package org.lessons.java.milestone.eventi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.lessons.java.milestone.eventi.Eccezioni.ExceptionNessunaPrenotazione;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionNumeroNegativo;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionPostiPrenotabili;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionTitoloNonValido;

public class Evento {
    // (caratteristiche)
    // Variabili di istanza private
    private String titoloEvento;
    private final int POSTI_TOTALI;
    private int postiPrenotati;
    private LocalDate data;
    private static final DateTimeFormatter DATA_FORMATTATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // (azioni)
    // Costruttore che serve per inizializzare l'oggetto della classe (deve avere lo
    // stesso nome di essa)
    public Evento(String titoloEvento, LocalDate data, int POSTI_TOTALI)
            throws ExceptionNumeroNegativo, ExceptionTitoloNonValido {
        // Usa i setter per inizializzare e validare
        setTitoloEvento(titoloEvento);
        setData(data);

        // POSTI_TOTALI è final, quindi deve essere impostato direttamente dopo la
        // validazione.
        // La sua validazione non può essere spostata in un setter poiché non esiste un
        // setter per esso.

        if (isCapienzaValida(POSTI_TOTALI)) {
            this.POSTI_TOTALI = POSTI_TOTALI;
        } else {

            throw new ExceptionNumeroNegativo(
                    "Il numero di posti " + POSTI_TOTALI + " non è valido, verificare che sia maggiore di 0.");
        }

        this.postiPrenotati = 0;
    }

    // --- Validatori statici --- (cioè appartengono alla classe non al singolo
    // oggetto)
    public static boolean isTitoloEventoValido(String titoloEvento) {
        return titoloEvento != null && !titoloEvento.isBlank(); // .trim().isempty()
    }

    public static boolean isEventoValido(LocalDate data) {
        // Usa LocalDate.now() per ottenere la data odierna
        return !data.isBefore(LocalDate.now());
    }

    public static boolean isCapienzaValida(int POSTI_TOTALI) {
        return POSTI_TOTALI > 0;
    }

    public static DateTimeFormatter dataFormattataNow() {
        return DATA_FORMATTATA;
    }

    // --- Getter e Setter ---
    public String getTitoloEvento() {
        return this.titoloEvento;
    }

    public void setTitoloEvento(String titoloEvento) throws ExceptionTitoloNonValido {
        if (isTitoloEventoValido(titoloEvento)) {
            this.titoloEvento = titoloEvento;
        } else {
            throw new ExceptionTitoloNonValido("Inserire il titolo!");
        }
    }

    public int getPostiTotali() {
        return this.POSTI_TOTALI;
    }

    public int getPostiPrenotati() {
        return this.postiPrenotati;
    }

    public void setPostiPrenotati(int postiPrenotati) throws ExceptionPostiPrenotabili {
        if (postiPrenotati <= POSTI_TOTALI && postiPrenotati >= 0) {
            this.postiPrenotati = postiPrenotati;
        } else {
            throw new ExceptionPostiPrenotabili(
                    "Il numero di posti deve essere minore dei posti totali e non può essere inferiore a 0.");
        }
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data) {
        if (isEventoValido(data)) {
            this.data = data;
        } else {
            throw new IllegalArgumentException("La data " + data.format(DATA_FORMATTATA)
                    + " non è valida, verifica che non sia precedente alla data odierna!");
        }
    }

    private boolean isEventoPassato() {
        // Confronta con la data odierna
        return LocalDate.now().isAfter(this.data);
    }
    // --- Metodi ---

    public void prenotaPosto() throws ExceptionPostiPrenotabili {
        if (isEventoPassato()) {
            // Usa IllegalStateException per operazioni non valide in base allo stato
            // dell'oggetto
            throw new IllegalStateException("L'evento è già passato, non è possibile prenotare.");
        }
        if (this.postiPrenotati >= this.POSTI_TOTALI) {
            throw new IllegalStateException("Posti esauriti, non è possibile prenotare");
        }
        this.postiPrenotati++;
    }

    public void disdiciPrenotazione() throws ExceptionNessunaPrenotazione {
        if (isEventoPassato()) {
            throw new IllegalStateException("L'evento è già passato, non puoi disdire.");
        }
        if (this.postiPrenotati <= 0) {
            throw new IllegalStateException("Non è possibile disdire, devi prima prenotare.");
        }
        this.postiPrenotati--;
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