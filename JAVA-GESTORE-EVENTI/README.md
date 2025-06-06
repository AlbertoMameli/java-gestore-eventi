## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


Questa è la milestone Gestione eventi, qua andrò a scrivere ciò che ho fatto separando le classi che ho generato.
----------------------------------------------------------EVENTO------------------------------------------------------------------------------
In questa classe posso creare degli eventi avendo inserito delle varibili opportune alla sua creazione e a dei metodi utili per il controllo..

Variabili di istanza
-titoloEvento : Il nome dell'evento. Ad esempio, "Festa di fine anno" o "Concerto di XYZ". 
-POSTI_TOTALI : Una volta deciso, questo numero non può più cambiare (ecco perché c'è scritto final). Immagina sia la capienza massima della sala.
-postiPrenotati (Numero intero): Quante persone hanno già detto "Sì, vengo!". All'inizio, ovviamente, è zero.
-data (Data): Il giorno in cui si svolgerà l'evento.
-DATA_FORMATTATA (Formato Data): Un modo speciale per scrivere sempre le date nello stesso formato. È uno strumento condiviso da tutti gli eventi.

Queste variabili sono tutte private, in questo modo la variabile è accessibile e modificata solo dai metodi all'interno della stessa classe. Nessun codice esterno alla classe può vederla o modificarla direttamente.


--------------------------------Per creare un nuovo Evento utilizzo unostumento: il costruttore
Quando vuoi organizzare un nuovo evento, usi una parte speciale del codice chiamata costruttore. È come se compilassi il tuo foglio di istruzioni per la prima volta. 
Devi fornire:

-Il titolo dell'evento.
-La data in cui si terrà.
-Il numero totale di posti disponibili.
Ma attenzione!
Ho inserito dei controlli :

Il titolo non può essere vuoto: Devi dare un nome all'evento!
La data deve essere futura: Non possiamo organizzare un evento per ieri! Deve essere per oggi o per un giorno futuro.
Il numero di posti totali deve essere maggiore di zero: Non ha senso un evento con zero posti.
Se una di queste regole non viene rispettata lancia un'eccezione, che è come un segnale di errore.
Se tutto è ok, l'evento viene creato e i postiPrenotati vengono impostati a zero.

-------------------------------------------Controllare e Modificare le Informazioni (i Getter e Setter)
Una volta creato l'evento, potresti voler:
Leggere le informazioni: Tipo, "Come si chiama l'evento?" o "Quanti posti ci sono?".
Per questo ci sono i metodi getTitoloEvento(), getData(), getPostiTotali(), getPostiPrenotati(). Sono come delle finestrelle per guardare dentro le informazioni dell'evento.
Modificare alcune informazioni: Ad esempio, potresti voler cambiare il titolo o la data. Per questo ci sono i metodi setTitoloEvento(nuovoTitolo) e setData(nuovaData). Anche qui, il computer controlla che le nuove informazioni siano valide (il titolo non vuoto, la data non passata).
Non possiamo cambiare i POSTI_TOTALI dopo aver creato l'evento, perché li avevamo definiti come "final" (fissi).
Non puoi nemmeno cambiare direttamente i postiPrenotati con un set, perché quelli si modificano solo prenotando o disdicendo.


--------------------------------------------Azioni che si possono fare con l'Evento (i metodi)
prenotaPosto():
Una persona vuole venire al tuo evento? Chiami questo metodo!
Controlli:
L'evento non deve essere già passato. Non puoi prenotare per una festa già finita!
Ci devono essere ancora posti liberi (i postiPrenotati devono essere minori dei POSTI_TOTALI).
Se tutto è ok, il numero di postiPrenotati aumenta di 1.


disdiciPrenotazione() :

Qualcuno non può più venire? Chiami questo metodo.
Controlli:
L'evento non deve essere già passato.
Ci deve essere almeno una prenotazione da cancellare (i postiPrenotati devono essere maggiori di 0).
Se tutto è ok, il numero di postiPrenotati diminuisce di 1. 

isEventoPassato() :
Un piccolo aiutante che controlla se la data dell'evento è precedente a oggi.
getInfoEvento() 
Ti restituisce una frase che riassume l'evento, tipo: "Evento: Festa di fine anno | Data: 15/06/2025 | Posti prenotati: 10/100".

toString() (Descrizione breve dell'evento):
Un modo standard per trasformare l'evento in una breve scritta, utile se devi stamparlo o visualizzarlo rapidamente. 
Ad esempio: "15/06/2025 - FESTA DI FINE ANNO".
Aiutanti Speciali (metodi static)
Ci sono anche dei piccoli "aiutanti" generali che non dipendono da un singolo evento specifico, ma servono a tutti:

isTitoloEventoValido(titolo): Controlla se un titolo è valido (non vuoto).
isEventoValido(data): Controlla se una data è valida (non passata).
isCapienzaValida(posti): Controlla se un numero di posti è valido (> 0).
getDataFormattata(): Fornisce lo strumento per formattare le date.





----------------------------------------------------------MAIN--------------------------------------------------------------------------------------
Il public static void main(String[] args) è il punto da cui inizia tutto, come il "via!" di una gara.

Chiedere le informazioni :
Per prima cosa, il programma ha bisogno di un Scanner. Lo immagino come un blocchetto per gli appunti e una penna: serve per scrivere le risposte che diamo alle domande del computer.
Poi chiede:
"Inserisci il titolo dell'evento:" (es. "Festa di fine anno")
"Inserisci la data (gg/mm/yyyy):" (es. "10/06/2025")
Qui c'è un controllo! Il programma continua a chiedervi la data finché non ne mettete una valida. Non potete mettere una data già passata (non si può organizzare una festa per ieri!) e dovete scriverla nel formato giusto (giorno/mese/anno). Se sbagliate, vi dice "Formato data non valido" o "La data non può essere nel passato" e ve la chiede di nuovo.
"Inserisci il numero di posti totali (positivo):" (es. 100)
Anche qui, controllo! I posti devono essere più di zero (non si può fare una festa con zero posti, o peggio, -5 posti!). Se mettete un numero sbagliato, vi avvisa e ve lo richiede.

Creare l'Evento :
Una volta che ha tutte queste informazioni (titolo, data giusta, numero di posti positivo), crea un "oggetto" Evento. Pensate all'oggetto Evento come a un biglietto ufficiale per il vostro evento, che contiene tutti i dettagli.
Evento evento = new Evento(titolo, data, postiTotali);
Subito dopo, vi mostra un riassunto dell'evento creato.

Gestire Prenotazioni e Disdette :
A questo punto, il main chiama due "aiutanti" (metodi):
-gestisciPrenotazioni(evento, scanner);: Questo si occupa di farvi prenotare i posti.
-gestisciDisdette(evento, scanner);: Quest'altro si occupa di farvi cancellare le prenotazioni, se necessario.
Dopo che questi due hanno finito il loro lavoro, il programma ci mostra di nuovo le informazioni aggiornate sull'evento (quanti posti sono stati prenotati, quanti ne rimangono).


E se qualcosa va storto?  (try-catch):
Tutta questa logica di chiedere informazioni e creare l'evento è dentro un blocco try. È come dire: "Provo a fare tutte queste cose".
Se durante queste operazioni succede un errore imprevisto (qualcosa che non avevamo considerato), il programma non si blocca di colpo, ma salta al blocco catch e vi dice: "Errore imprevisto: " seguito dal motivo dell'errore.
Infine, c'è un blocco finally. Questo pezzetto di codice viene eseguito SEMPRE, sia che le cose siano andate bene, sia che ci sia stato un errore. In questo caso, scanner.close(); chiude il "blocchetto degli appunti", per dire che abbiamo finito di usarlo. È buona educazione mettere a posto gli strumenti dopo averli usati!


gestisciPrenotazioni: L'Addetto alle Prenotazioni
Questo metodo (public static void gestisciPrenotazioni(Evento evento, Scanner scanner)) fa una cosa specifica:

Domanda Intenzionale: Vi chiede: "Vuoi prenotare dei posti? (si/no)"
Se dite "si":
Vi fa vedere quanti posti sono ancora liberi.
Vi chiede: "Quanti posti vuoi prenotare?"
Controlla che il numero che inserite sia:
Positivo (non potete prenotare 0 o -2 posti).
Non superiore ai posti disponibili (non potete prenotare 10 posti se ne sono rimasti solo 5).
Se il numero non va bene, ve lo richiede finché non è corretto.
Una volta che ha un numero valido, usa un ciclo (for) per prenotare i posti uno alla volta, chiamando il metodo evento.prenotaPosto() (che è dentro la classe Evento e fa il lavoro sporco di segnare un posto come occupato).
Alla fine, ci dice: "Prenotazioni effettuate. Posti prenotati: [numero]/[totale iniziale che era disponibile]".
gestisciDisdette: 
Questo metodo (public static void gestisciDisdette(Evento evento, Scanner scanner)) è il gemello di quello delle prenotazioni, ma fa il contrario:

Domanda Intenzionale: Vi chiede: "Vuoi disdire delle prenotazioni? (si/no)"
Se dite "si":
Vi fa vedere quanti posti avete prenotato.
Vi chiede: "Quanti posti vuoi disdire?"
Controlla che il numero che inserite sia:
Positivo.
Non superiore ai posti che avevate effettivamente prenotato, se il numero non va bene, lo richiede.
Una volta che ha un numero valido, usa un ciclo (for) per disdire i posti uno alla volta, chiamando il metodo evento.disdiciPrenotazione() (che è sempre dentro la classe Evento e libera un posto).
Alla fine  dice: "Disdette effettuate. Posti rimanenti: [nuovo numero di posti prenotati]".





------------------------------------------------------------CONCERTO------------------------------------------------------------------------------


extends Evento significa che un Concerto è un tipo di Evento. Eredita tutte le sue caratteristiche (titolo, data, posti totali, posti prenotati, ecc.) e in più aggiunge qualcosa di suo.

Un Concerto ha due informazioni in più rispetto a un evento generico:

-private LocalTime oraConcerto;
Questa è l'ora precisa in cui inizia il concerto (es. 21:00). LocalTime è un tipo di dato fatto apposta per le ore.

-private BigDecimal prezzoConcerto;
Questo è il prezzo del biglietto del concerto. BigDecimal è un tipo di dato che si usa per i numeri con la virgola quando serve molta precisione, come per i soldi, per evitare arrotondamenti strani.


----------------------------------------------------Formattare Ora e Prezzo


-private static final DateTimeFormatter ORARIO_FORMATTATO = DateTimeFormatter.ofPattern("HH:mm");
Questo dice: "Quando devo scrivere l'ora, la voglio nel formato Ore:Minuti (es. 20:30)".
-private static final DecimalFormat PREZZO_FORMATTATO = new DecimalFormat("###,##€ 0.00");
Questo dice: "Quando devo scrivere il prezzo, lo voglio con il simbolo dell'euro e due cifre dopo la virgola (es. 25,50€ o 100,00€)".



---------------------------------------------------------Per creare un Concerto: Il Costruttore

public Concerto(String titoloEvento, LocalDate data, int postiTotali, LocalTime oraConcerto, BigDecimal prezzoConcerto) throws ... {
    super(titoloEvento, data, postiTotali); // Chiama il costruttore di Evento
    setOraConcerto(oraConcerto);
    setPrezzo(prezzoConcerto);
}
Quando crei un Concerto, devi dare tutte le informazioni base di un Evento (titolo, data, posti totali) E le informazioni specifiche del concerto (ora e prezzo).
super(titoloEvento, data, postiTotali);
Questa riga è come dire: "Ehi, papà Evento, prendi queste informazioni (titolo, data, posti) e fai la tua parte di costruzione!".
setOraConcerto(oraConcerto); e setPrezzo(prezzoConcerto);
Poi, usa dei metodi speciali (che vediamo tra poco) per impostare l'ora e il prezzo del concerto, applicando dei controlli.
Vedere e Modificare Ora e Prezzo (Getters e Setters)
Questi metodi ci permettono di leggere o cambiare l'ora e il prezzo, ma con delle regole!

-----------------------Ora del Concerto:

-public LocalTime getOraConcerto(): Ti fa leggere l'ora del concerto.
-public void setOraConcerto(LocalTime oraConcerto): Ti permette di cambiare l'ora. Ma attenzione!
Non puoi cambiare l'ora se il concerto è già passato! Sarebbe come cambiare l'orario di inizio di una partita già finita.
L'ora non può essere vuota (nulla). Un concerto deve avere un orario!
Se provi a fare una di queste cose proibite, il programma lancia un RuntimeException (un "allarme errore!").
Prezzo del Concerto:

-public BigDecimal getPrezzo(): Ti fa leggere il prezzo.
-public void setPrezzo(BigDecimal prezzoConcerto): Ti permette di cambiare il prezzo. Ma anche qui, occhio!
Il prezzo non può essere vuoto (nullo). Se lo è, lancia un ExceptionPrezzoNull.
Il prezzo non può essere negativo (non puoi pagare la gente per venire al concerto, almeno non con un prezzo negativo!). Se lo è, lancia un ExceptionPrezzoNegativo.
prezzoConcerto.compareTo(BigDecimal.ZERO) < 0 è un modo un po' tecnico per dire "il prezzo è minore di zero".



-------------------------------------------------------------Metodi per le Informazioni Formattate
Questi metodi preparano le informazioni in un formato leggibile e carino:

public String getDataOraFormattata():
Combina la data (presa dalla classe Evento e già formattata) e l'ora del concerto (formattata con ORARIO_FORMATTATO) in una singola stringa, tipo: dd/MM/yyyy-HH:mm.
public String getPrezzoFormattato():
Prende il prezzoConcerto e lo formatta usando PREZZO_FORMATTATO, così appare bello ordinato con il simbolo dell'euro.


------------------------------------------Il Metodo toString() Speciale


@Override
public String toString() {
    return super.toString() + "-" + getPrezzoFormattato();
}
@Override: Questa etichetta significa: "Sto fornendo una versione migliore del metodo toString() che ho ereditato".
super.toString(): Prima chiede alla "parte Evento" di descriversi.
+ "-" + getPrezzoFormattato(): Poi, a quella descrizione, aggiunge un trattino e il prezzo del concerto ben formattato.
Quindi, se chiedi a un oggetto Concerto di descriversi, ti dirà qualcosa come:
"Data - Titolo - PrezzoFormattato" (es. "10/06/2025 - Concerto Rock Pazzesco - 25,00€").

In sintesi, la classe Concerto è un Evento più specifico, con in più un orario e un prezzo, e con dei metodi per gestire e visualizzare queste informazioni in modo corretto.


---------------------------------------------------------PROGRAMMA_EVENTI---------------------------------------------------------------------------------


Questa classe è progettata per agire come un contenitore logico, responsabile della gestione di una collezione di eventi.

--------Attributi

-private String titolo: Memorizza l'identificativo del programma di eventi.
-private List<Evento> eventi: È la lista che contiene tutti gli oggetti di tipo Evento.
Ricordiamo che il modificatore private assicura che questi dati siano incapsulati, ovvero accessibili e modificabili solo attraverso i metodi della classe stessa, garantendo un controllo completo.

---------Il Costruttore
Il costruttore è il metodo speciale che viene chiamato per creare una nuova istanza della classe. Il suo compito è assicurarsi che ogni nuovo oggetto nasca in uno stato valido. Per questo, esegue una validazione immediata del titolo per impedire che sia nullo o vuoto. In caso affermativo, lancia un'eccezione per segnalare l'uso scorretto. Inizializza inoltre la lista interna come una collezione vuota, pronta per essere popolata.

---------------Getter e setter
-getEventiInData: Esegue una ricerca all'interno della lista. Restituisce una nuova collezione contenente unicamente gli eventi che corrispondono alla data specificata.
-getTitolo e setTitolo: Permettono rispettivamente di leggere e di modificare il titolo dell'oggetto. Il metodo setTitolo include la stessa logica di validazione del costruttore per mantenere la coerenza dei dati.
-getEventi: Fornisce l'accesso alla lista di eventi. Per proteggere l'integrità della collezione interna, non restituisce la lista originale, ma una sua vista non modificabile. Questo impedisce modifiche accidentali dall'esterno.
Formattazione dell'Output

--------I Metodi
I metodi definiscono l'interfaccia pubblica, cioè le azioni che un oggetto ProgrammaEventi può eseguire.

-aggiungiEvento: Inserisce un nuovo evento nella lista, previa una verifica che l'oggetto da aggiungere non sia nullo.
-getNumeroEventi: Restituisce il numero totale di eventi attualmente presenti nella collezione.
-svuotaEventi: Rimuove tutti gli eventi dalla lista, riportandola allo stato vuoto.
-stampaEventiPerData: Genera una rappresentazione testuale dell'intero programma. Utilizza l'API Stream per creare una pipeline di elaborazione: prima ordina gli eventi per data, poi trasforma ogni evento in una stringa e infine li unisce in un unico testo formattato e leggibile.

L'API Strem(Application Programming Interface) è uno strumento che mi consente di ricevere informazioni sempre aggiornate senza che io debba richiedere al server se c'è qualcosa di nuovo








