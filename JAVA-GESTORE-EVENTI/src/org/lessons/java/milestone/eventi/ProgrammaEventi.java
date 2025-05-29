package org.lessons.java.milestone.eventi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ProgrammaEventi {
    private String titolo;
    private List<Evento> eventi;

    public ProgrammaEventi(String titolo) {
        this.titolo = titolo;
        this.eventi =new ArrayList<>();
    }

    public void aggiungiEvento (Evento evento){
        eventi.add(evento);
    }


    public List<Evento> getEventiInData(LocalDate data){
        List<Evento> eventiInData = new ArrayList<>();
        for (Evento e : eventi){
            if(e.getData().equals(data)){
                eventiInData.add(e);
            }
        }
        return eventiInData;
    }

    public int getNumeroEventi(){
        return eventi.size();
    }


    
    public void svuotaEventi(){
        eventi.clear();
    }
//getter e setter automatizzati
    public String getTitolo() {
        return this.titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public List<Evento> getEventi() {
        return this.eventi;
    }

    public void setEventi(List<Evento> eventi) {
        this.eventi = eventi;
    }

    // metodo che stampa gli eventi in ordine in base alla data

    public String stampaEventoPerData(){
      return titolo + "\n" + eventi.stream().sorted(Comparator.comparing(Evento::getData)).map(Evento::toString).collect(Collectors.joining("\n"));

    }

}
