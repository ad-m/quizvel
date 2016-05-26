# java-quizvle

##Wymogi 

## Technologia

Java, HTTP, JSON

## Protokoł

```
msc {
  arcgradient="15";

  User,
  Client,
  Server;

  --- [label="Uwierzytelnianie nieprawidłowe"];
  User => Client [label="wprowadzenie danych logowania"];
  Client => Server [label="przesłanie danych logowania"];
  Server => Server [label="weryfikacja danych logowania"];
  Server => Client [label="odmowa uwierzytelnienia"];
  Client => User [label="wyświetlenie komunikat sukcesu"];
  --- [label="Uwierzytelnienie prawidłowe"];
  User => Client [label="wprowadzenie danych logowania"];
  Client => Server [label="przesłanie danych logowania"];
  Server => Client [label="potwierdzenie uwierzytelnienia"];
  Client => User [label="wyświetlenie komunikat błędu"];
  --- [label="Wypełnienie ankiety"];
  User => Client [label="rozpoczęcie wypełniania ankiety"];
  Client => Server [label="żądanie ankiety"];
  Server => Client [label="przesłanie pytań z ID i odpowiedzi"];
  Client => User [label="wyświetlenie pytań"];
  User => Client [label="wprowadzenie odpowiedzi"];
  Client => Server [label="przesłanie odpowiedzi"];
  Server => Server [label="weryfikacja odpowiedzi"];
  Server => Client [label="przesłanie wyniku"];
  Client => Server [label="wyświetlenie wyniku"];
  --- [label="C - dodanie pytania"];
  User => Client [label="wprowadzenie danych pytania"];
  Client => Server [label="przesłanie danych pytania"];
  Server => Client [label="potwierdzenie zapisu pytania"];
  --- [label="R - odczytanie bazy pytań"];
  User => Client [label="zażądanie bazy pytań"];
  Client => Server [label="zażądanie bazy pytań"];
  Server => Client [label="przesłanie listy pytań"];
  --- [label="U - aktualizacja pytania"];
  User box Server [label="odczytanie bazy pytań"];
  User => User [label="wybór pytania"];
  User => Client [label="wprowadzenie nowych danych pytania"];
  Client => Server [label="przesłanie danych pytania i ID"];
  Server => Client [label="potwierdzenie zapisu pytania"];
  --- [label="D - edycja [C] pytań"];
  User => Client [label="wprowadzenie danych pytania"];
  Client => Server [label="przesłanie danych pytania"];
  Server => Client [label="potwierdzenie zapisu pytania"];
}
```
Wygenerowanie diagramu możliwe poprzez [mscgen_js](http://mscgen.js.org)

## Wymogi 

### Podstawowe wymogi
Serwer powinien obsługiwać dwie grupy użytkowników: 
 * administratora (który tworzy testy)
 * testowanych (którzy je wypełniają).
Każdy użytkownik systemu powinien się zarejestrować. Testy są pamiętane w pliku (lub bazie danych).
Po wypełnieniu testu przez użytkownika, program powinien podsumować wyniki i na podstawie zadanych kryteriów wystawić ocenę.

### Rozszerzone wymogi
Program powinien posiadać możliwość edycji i usuwania  pytań i odpowiedzi.
