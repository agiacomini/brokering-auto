Brokering-Auto
===============

## Contesto


Si immagini di essere un broker di automobili che compra 20 auto tutte diverse tra loro ogni giorno per rivenderle in opportuni mercati una volta selezionate.
SI richiede un'api rest che faccia l'immissione delle auto in modalità Bulk prelevando input da file csv.
Si richiede una o più api rest che restituiscano le auto inserite nel sistema secondo specifici filtri e raggruppamenti:
1. tutte le auto di anni 1 con cambio automatico
2. quelle più vecchie a gasolio
3. quelle con anzianità di tre anni, con fascia valore da 5k/10K e cambio automatico

tutti i filtri hanno un dato opzionale che richiede il 4x4 sì / no (default no) 

## Modello dei Dati

Auto

- 4x4: sì / no
- fascia di prezzo: 0 a 5K, da 5K a 10K, oltre i 10K € anzianità: 1,3 o 5 anni
- cambio automatico: sì / no
- alimentazione: benzina, gasolio o elettrica

## Istruzioni Preliminari

Per poter eseguire il running del progetto sarà necessario avere installato 
il Docker su cui girerà il database relazionale "postgreSql" e il "pgAdmin" rispettivamente in due distinti containers.

Inoltre per poter eseguire i test delle Web API si richiede l'installazione del Postman. Di seguito saranno
fornite le indicazioni per importare il file con la collection di chiamate REST già predisposte. 

## Running

Una volta scaricato il progetto l'unico passo preliminare che viene chiesto di fare è quello
di eseguire il file "docker-compose.yml". Questo file si trova nella root del progetto. E'
possibile eseguirlo all'interno del proprio IDE preferito attraverso gli appositi pulsanti
oppure da linea di comando posizionandosi nella root ed eseguendo il seguente comando:

```bash
$ docker-compose up
```

Questa attività crea come primo passo una rete interna attraverso la quale i due container,
quello del DB e quello del pgAdmin, potranno comunicare. In secondo luogo vengono scaricate due 
immagini docker, precisamente le ultime (:latest) a disposizione sul docker-hub.
Successivamente vengono creati due distinti container su cui gireranno le immagini precedentemente
scarticate.

### PgAdmin

Le credenziali per poter accedere al pgAdmin sono a disposizone nel file "docker-compose.yml".
Da browser collegarsi all'URL: http://localhost:80
Una volta fatto il log-in creare una nuova connessione:

1. Add New Server
2. Nel tab "Connection" inserire
   1. Host name/address: 182.21.0.2
   2. Port: 5432
   3. Maintenance Database: postgres
   4. Username: postgres
   5. Password: admin

Una volta fatto questo passaggio ed eseguito il progetto (all'interno dell' IDE oppure eseguendo
il file jar presente all'interno della cartella "target") si vedrà apparire all'interno dell'elenco 
dei database a disposizione un nuovo database, inizialmente vuoto e con una sola tabella "auto",
chiamato "brokering-auto-database".

## Testing Web API

Per poter testare le Web API messe a disposizione è possibile scegliere tra:

### Postman
Nella root del progetto si troverà la cartella "postman" che contiene il file
"brokering-auto.postman_collection.json" con la collection di chiamate REST già predisposte.
Questo file può essere importato all'interno del proprio client Postman.

### Swagger
Collegandosi all'URL http://localhost:5051/swagger-ui/index.html è sempre possibile testare
le Web API.
Quest'ultimo funge anche da documentazione delle API stesse.

N.B: Si consiglia di iniziare dall'API http://localhost:5051/api/v1/auto/insert/csv la quale
popola automaticamente la tabella auto leggendo da un file .csv di esempio. Da Swagger eseguire
l'operazione di POST: /api/v1/auto/insert/csv

## Unit Test

Sono stati predisposti due gruppi di test automatici. Il primo riferito allo strato di persistenza
dell'applicazione (repository) il secondo invece riferito allo strato di presentazione (controller).
Sono posizionati in due package diversi all'interno della cartella "src/test".
