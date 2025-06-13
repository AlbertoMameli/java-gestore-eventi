package org.lessons.java.milestone.eventi;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.lessons.java.milestone.eventi.Eccezioni.ExceptionPrezzoNull;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionOraLive;
import org.lessons.java.milestone.eventi.Eccezioni.ExceptionPrezzoNegativo;

public class Concerto extends Evento {

    private LocalTime oraConcerto;
    private BigDecimal prezzoConcerto;

    // Formatto ora e prezzo

    private static final DateTimeFormatter ORARIO_FORMATTATO = DateTimeFormatter.ofPattern("HH:mm");
    private static final DecimalFormat PREZZO_FORMATTATO = new DecimalFormat("###,##€ 0.00");

    public Concerto(String titoloEvento, LocalDate data, int postiTotali, LocalTime oraConcerto,
            BigDecimal prezzoConcerto) throws ExceptionPrezzoNull, ExceptionPrezzoNegativo, Exception {
        super(titoloEvento, data, postiTotali);
        setOraConcerto(oraConcerto);
        setPrezzo(prezzoConcerto);
    }

    public LocalTime getOraConcerto() {

        return oraConcerto;
    }

    public void setOraConcerto(LocalTime oraConcerto) throws ExceptionOraLive {
        if (getData().isBefore(LocalDate.now())) {
            throw new ExceptionOraLive("Errore: Non è possibile modificare l'ora di un concerto già passato.");
        }

        if (oraConcerto == null) {
            throw new ExceptionOraLive("Errore: L'ora del concerto non può essere nulla.");
        }
        this.oraConcerto = oraConcerto;
    }

    public BigDecimal getPrezzo() {
        return prezzoConcerto;
    }

    public void setPrezzo(BigDecimal prezzoConcerto) throws ExceptionPrezzoNull, ExceptionPrezzoNegativo {
        if (prezzoConcerto == null) {
            throw new ExceptionPrezzoNull("Errore: Il prezzo del concerto non può essere nullo.");

        }
        // 'compareTo(BigDecimal.ZERO)' restituisce:
        // < 0 se questo BigDecimal è minore di BigDecimal.ZERO
        // == 0 se questo BigDecimal è uguale a BigDecimal.ZERO
        // > 0 se questo BigDecimal è maggiore di BigDecimal.ZERO
        if (prezzoConcerto.compareTo(BigDecimal.ZERO) < 0) {
            throw new ExceptionPrezzoNegativo("Errore: Il prezzo del concerto non può essere negativo.");
        }
        this.prezzoConcerto = prezzoConcerto;
    }

    // Data e ora formattata
    public String getDataOraFormattata() {
        return getData().format(dataFormattataNow()) + "-" + oraConcerto.format(ORARIO_FORMATTATO);
    }

    // prezzo formattato

    public String getPrezzoFormattato() {
        return PREZZO_FORMATTATO.format(prezzoConcerto);
    }

    @Override
    public String toString() {
        return super.toString() + "-" + getPrezzoFormattato();
    }

}
