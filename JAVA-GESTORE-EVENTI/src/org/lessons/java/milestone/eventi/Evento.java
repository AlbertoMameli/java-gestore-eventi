package org.lessons.java.milestone.eventi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.lessons.java.milestone.eventi.Eccezioni.ExceptionDataPassata;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionNessunaPrenotazione;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionNumeroNegativo;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionPostiPrenotabili;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionTitoloNonValido;

public class Evento {

    // Variabili di istanza private
    private String titoloEvento;
    private final int POSTI_TOTALI;
    private int postiPrenotati;
    private LocalDate data;
    private static final DateTimeFormatter DATA_FORMATTATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Evento(String titoloEvento, LocalDate data, int POSTI_TOTALI)
            throws ExceptionNumeroNegativo, ExceptionTitoloNonValido, ExceptionDataPassata, ExceptionNessunaPrenotazione {
        // Usa i setter per inizializzare e validare
        setTitoloEvento(titoloEvento);
        setData(data);

        if (isCapienzaValida(POSTI_TOTALI)) {
            this.POSTI_TOTALI = POSTI_TOTALI;
        } else {

            throw new ExceptionNumeroNegativo(
                    "Il numero di posti " + POSTI_TOTALI + " non è valido, verificare che sia maggiore di 0.");
        }

        this.postiPrenotati = 0;
    }

    public static boolean isTitoloEventoValido(String titoloEvento) {
        return titoloEvento != null && !titoloEvento.isBlank(); // .trim().isempty()
    }

    public static boolean isEventoValido(LocalDate data) {

        return !data.isBefore(LocalDate.now());
    }

    public static boolean isCapienzaValida(int POSTI_TOTALI) {
        return POSTI_TOTALI > 0;
    }

    public static DateTimeFormatter dataFormattataNow() {
        return DATA_FORMATTATA;
    }

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
            throw new ExceptionPostiPrenotabili("Il numero di posti deve essere minore dei posti totali e non può essere inferiore a 0.");
        }
    }

    public LocalDate getData() {
        return this.data;
    }

    public void setData(LocalDate data)throws ExceptionDataPassata {
        if (isEventoValido(data)) {
            this.data = data;
        } else{
            throw new ExceptionDataPassata("La data " + data.format(DATA_FORMATTATA)
                    + " non è valida, verifica che non sia precedente alla data odierna!");
        }
    }

    private boolean isEventoPassato() {

        return LocalDate.now().isAfter(this.data);
    }

    public void prenotaPosto() throws ExceptionPostiPrenotabili, ExceptionDataPassata {
        if (isEventoPassato()) {

            throw new ExceptionDataPassata("L'evento è già passato, non è possibile prenotare.");
        }
        if (this.postiPrenotati >= this.POSTI_TOTALI) {
            throw new ExceptionPostiPrenotabili("Posti esauriti, non è possibile prenotare");
        }
        this.postiPrenotati++;
    }

    public void disdiciPrenotazione() throws ExceptionNessunaPrenotazione, ExceptionDataPassata {
        if (isEventoPassato()) {
            throw new ExceptionDataPassata("L'evento è già passato, non puoi disdire.");
        }
        if (this.postiPrenotati <= 0) {
            throw new ExceptionNessunaPrenotazione("Non è possibile disdire, devi prima prenotare.");
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