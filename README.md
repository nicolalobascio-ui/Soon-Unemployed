# Soon Unemployed

Soon Unemployed è un gioco di ruolo a turni ambientato nel contesto lavorativo contemporaneo. Il giocatore interpreta un impiegato che deve superare tre incontri consecutivi nella giornata di venerdì, gestendo salute mentale, pazienza e rischio di licenziamento.

**Package:** `it.unicam.cs.mpgc.rpg125656`

## Obiettivo del gioco

Completare tre livelli sequenziali azzerando l'autorità degli avversari e mantenendo sotto controllo lo stato del personaggio.

### Statistiche

| Risorsa | Intervallo | Effetto |
|---------|------------|---------|
| Salute mentale (HP) | 0–100 | A 0 la partita termina per esaurimento |
| Pazienza (MP) | 0–50 | Consumata o recuperata in base alla risposta scelta |
| Autorità nemico | 0–max | Portarla a 0 per vincere lo scontro |
| Irritazione nemico | 0–100 | Nel livello finale, a 100% causa il licenziamento |

### Livelli

1. **10:00** — Collega fastidioso (autorità 50)
2. **14:00** — Project manager (autorità 80)
3. **17:30** — Grande capo (autorità 100, può licenziare)

### Risposte disponibili

- **Gentile** — recupera MP, riduce irritazione, aumenta lo stress
- **Passivo-aggressiva** — costa MP, efficace e relativamente sicura
- **Maleducata** — non costa MP, molto danno, aumenta molto l'irritazione

## Tecnologie

- Java 25
- Gradle
- JavaFX 25 (interfaccia grafica desktop)

## Esecuzione

Dalla root del progetto bastano due comandi:

```bash
./gradlew build
./gradlew run
```

Su Windows PowerShell:

```powershell
.\gradlew.bat build
.\gradlew.bat run
```

## Persistenza dei dati

La persistenza è gestita su file di testo tramite un'architettura a porte, separata dal dominio di gioco.

### Architettura

```
GameSession
    └── GamePersistencePort (interfaccia)
            └── GamePersistenceService (implementazione file)
                    └── GameStateSnapshot (serializzazione)
```

- **`GamePersistencePort`** — contratto per salvare, caricare, verificare ed eliminare un salvataggio
- **`GamePersistenceService`** — scrive e legge il file su disco
- **`GameStateSnapshot`** — converte `GameState` in formato persistibile e viceversa
- **`GameSession`** — orchestra quando salvare o caricare durante la partita

### Dove viene salvato

Il file di salvataggio si trova in:

```
<user.home>/.soon-unemployed/savegame.txt
```

Su Windows, ad esempio: `C:\Users\<nome-utente>\.soon-unemployed\savegame.txt`

Il percorso è fisso e non dipende dalla cartella da cui viene avviata l'applicazione.

### Formato del file

Una singola riga di testo con 11 campi separati da `|`:

| Campo | Contenuto |
|-------|-----------|
| 1 | Nome giocatore (Base64) |
| 2 | Salute mentale |
| 3 | Pazienza |
| 4 | Livello corrente |
| 5 | Orario (Base64) |
| 6 | Nome nemico (Base64) |
| 7 | Autorità massima nemico |
| 8 | Autorità corrente nemico |
| 9 | Irritazione nemico |
| 10 | Nemico può licenziare (`true`/`false`) |
| 11 | Esito partita (`IN_PROGRESS`, `VICTORY`, `BURNOUT`, `FIRED`) |

Le stringhe testuali sono codificate in Base64 per evitare conflitti con il separatore `|`.

### Quando viene salvato

| Evento | Azione |
|--------|--------|
| Nuova partita | Salvataggio automatico |
| Ogni turno giocato | Salvataggio automatico |
| Pulsante **Salva partita** | Salvataggio manuale |
| **Riavvia partita** / **Nuova partita** | Il salvataggio precedente viene eliminato |
| **Torna al menù** | Il salvataggio resta su disco |

### Caricamento

Dal menù principale, **Carica partita** legge il file, ricostruisce lo stato di gioco e riprende la sessione. Se il file non esiste o è corrotto, viene mostrato un messaggio di errore.

## Estendibilità

Il progetto è strutturato per supportare future estensioni (altre piattaforme, nuove funzionalità):

- **Porte di servizio** (`GameServicePort`, `BattleServicePort`, `GamePersistencePort`) permettono di sostituire le implementazioni senza modificare la logica di sessione
- **Separazione UI / logica** — `MainWindowController` gestisce solo l'interfaccia, `GameSession` coordina il flusso di gioco
- **Snapshot indipendente** — il formato di persistenza è isolato in `GameStateSnapshot`, sostituibile con JSON, database o API senza toccare il dominio

## Dichiarazione sull'uso di strumenti di AI

Durante lo sviluppo sono stati utilizzati strumenti di AI come supporto per brainstorming. Le scelte architetturali, la modellazione del dominio, l'implementazione del codice e la validazione finale restano responsabilità dell'autore del progetto.
