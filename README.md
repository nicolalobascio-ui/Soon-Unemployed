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


## Dichiarazione sull'uso di strumenti di AI

Durante lo sviluppo sono stati utilizzati strumenti di AI come supporto per brainstorming. Le scelte architetturali, la modellazione del dominio, l'implementazione del codice e la validazione finale restano responsabilità dell'autore del progetto.
