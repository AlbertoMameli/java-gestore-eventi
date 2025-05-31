package org.lessons.java.milestone.eventi;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Concerto extends Evento {

    private LocalTime oraConcerto;
    private BigDecimal prezzoConcerto;

    // Formatto ora e prezzo

    private static final DateTimeFormatter orarioFormattato = DateTimeFormatter.ofPattern("HH:mm");
    private static final DecimalFormat prezzoFormattato = new DecimalFormat("###,##0.00 €");

    public Concerto(String titoloEvento, LocalDate data, int postiTotali, LocalTime oraConcerto, BigDecimal prezzoConcerto) {
        super(titoloEvento, data, postiTotali);
        this.oraConcerto = oraConcerto;
        this.prezzoConcerto = prezzoConcerto;
    }

    public LocalTime getOraConcerto() {
        return oraConcerto;
    }

    public void setOraConcerto(LocalTime oraConcerto) { 
        this.oraConcerto = oraConcerto;
    }

    public BigDecimal getPrezzo() {
        return prezzoConcerto;
    }

    public void setPrezzo(BigDecimal prezzoConcerto) {
        this.prezzoConcerto = prezzoConcerto;
    }

    // Data e ora formattata
    public String getDataOraFormattata() {
        return getData().format(getDataFormattata()) + "-" + oraConcerto.format(orarioFormattato);
    }

    // prezzo formattato

    public String getPrezzoFormattato() {
        return prezzoFormattato.format(prezzoConcerto);
    }

    @Override
    public String toString() {
        return getDataOraFormattata() + "-" + getTitoloEvento() + "-" + getPrezzoFormattato();
    }

}
