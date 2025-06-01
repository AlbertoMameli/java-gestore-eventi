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
       setOraConcerto(oraConcerto);
       setPrezzo(prezzoConcerto);
    }

    public LocalTime getOraConcerto() {
         
        return oraConcerto;
    }

    public void setOraConcerto(LocalTime oraConcerto) { 
         if (getData().isBefore(LocalDate.now())) {
            throw new RuntimeException("Errore: Non è possibile modificare l'ora di un concerto già passato.");
        }
        
        if (oraConcerto == null) {
            throw new RuntimeException("Errore: L'ora del concerto non può essere nulla.");
        }
        this.oraConcerto = oraConcerto;
    }

    public BigDecimal getPrezzo() {
        return prezzoConcerto;
    }

    public void setPrezzo(BigDecimal prezzoConcerto) {
        if (prezzoConcerto == null) {
            throw new RuntimeException("Errore: Il prezzo del concerto non può essere nullo.");
        
    }
    // 'compareTo(BigDecimal.ZERO)' restituisce:
        //    < 0 se questo BigDecimal è minore di BigDecimal.ZERO
        //    == 0 se questo BigDecimal è uguale a BigDecimal.ZERO
        //    > 0 se questo BigDecimal è maggiore di BigDecimal.ZERO
    if (prezzoConcerto.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Errore: Il prezzo del concerto non può essere negativo.");
        }
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
        return super.toString() + "-" + getPrezzoFormattato();
    }

}
