# PGR200 Hovedinnlevering

[![Build Status](https://travis-ci.com/Westerdals/pgr200-eksamen-jahnsrud.svg?token=zisdcsxqwPzFijME9CRW&branch=master)](https://travis-ci.com/Westerdals/pgr200-eksamen-jahnsrud)

Gruppe: 
* Markus Jahnsrud - jahmar17 - 704851
* Jørgen Aasan - aasjor17 - 705009

## Hva er dette?

En app for å holde oversikt over presentasjoner på en konferanse. Man kan legge til nye presentasjoner, og man kan selvsagt se hva som er på programmet

![](https://user-images.githubusercontent.com/4276097/48379518-a9248c00-e6d4-11e8-82e4-50867d4bd455.png)

## Hvordan?
Løsningen består av en server og en klient.

Slik kommer du i gang:
1. Åpne terminal og gå til prosjektmappen
2. Kjør 'mvn package'
3. Start serveren: java -jar innlevering-server/target/innlevering-server-1.0.0-SNAPSHOT.jar 
4. Start klienten: java -jar innlevering-client/target/innlevering-client-1.0.0-SNAPSHOT.jar
5. Etter ".jar " kan du skrive inn argumenter. Se listen under for en oversikt over hva som støttes

**NB:** innlevering.properties må endres for å tilpasse din PostgreSQLdatabase. Serveren kjører default på port 22022

**NB:** appen bruker emojis for å tydeliggjøre enkelt handlinger. Disse fungerer best på macOS.
 

### Client
> java -jar innlevering-client-1.0.0-SNAPSHOT.jar

```
* Vis alle presentasjoner
> list
> list -topic 'ønsket tema her'

* Legg til presentasjon (tittel, beskrivelse og tema)
> add 
> add -title 'ønsket tittel'

* Vis en presentasjon ved hjelp av id
> view 'id'

* Vis info om konferansen du er på
> About

* Vis tilgjengelige funksjoner 
> help

* Easter eggs: vi har lagt inn flere easter eggs, men her har dere en å begynne med. Lykke til med jakten 🕵
> hackerman
```

### Server
> java -jar innlevering-server-1.0.0-SNAPSHOT.jar 

```
* Nullstill databasen
> resetdb
```

## Hva kunne blitt løst bedre?

* Tester, tester og tester. CLI burde testes, serveren burde testes mer. Vi må rett og slett begynne å fokusere på testene på et mye tidligere tidspunkt
* Flyway ble nesten klart, men vi rakk ikke å bli ferdig
* Bedre feilhåndtering, spesielt av edge-cases
* Håndtering av requests i HttpServer burde vært *mye* ryddigere
* Sjekking av databasens status
* En mer brukervennlig CLI. Kanskje slik at man kunne skrevet "start", også ble man fanget i en app-opplevelse
* Kommentering av koden
* Implementere update talk
* Bruke .sql-fil(er) til å opprette databasen, ikke hardkodet i koden
* Mapper-funksjon
* Generics på DAO
* Serveren burde returnere at Content-Type er JSON
* Nullstilling av database fungerer ikke
* Flytte alt av JSON-parsing til JSONConverter
* Ignorere requests til serveren som spør om favicon (eller faktisk returnere det 😎)
* Langt bedre DAO-klasse som ikke gjentar seg selv så ofte
* *Mye* ryddigere parsing i HttpServer
* …og nevnte vi tester?


## Oppgave

Du har funnet en konferanse du er interessert i å gå på, men du har ikke råd til billetten. Men frykt ikke: etter at du tok kontakt med de som organiserer konferansen fikk du høre at du kunne få gratisbillett dersom du hjelper til å lage noe programvare for konferansen.

Oppgaven din: lag en server for appen som inneholder konferanseprogrammet i en database. Funksjonaliteten må som et minimum tillate at man legger inn og lister ut foredrag på konferansen. Du bruke datamodellen angitt under eller forenkle eller endre den slik du selv ønsker.

[…]

Fullstendig oppgavetekst finner du i OPPGAVE.md.
