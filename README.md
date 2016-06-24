# java-quizvle
# Aplikacja Quizvel

## Założenia
Serwer potrafi obsługiwać dwie grupy użytkowników: 
 * administratora (który tworzy testy)
 * testowanych (którzy je wypełniają).
Każdy użytkownik systemu rejestruje się. Testy są pamiętane w pliku. Po wypełnieniu testu przez użytkownika, program powinien podsumować wyniki i wystawia ocenę poprzez uznanie wyniku < 3 punktów za niedostateczny, a pozostałe jako bardzo dobre.
Program posiada możliwość edycji i usuwania  pytań i odpowiedzi.

## Specyfikacja protokołu
###Informacje ogólne
Protokół aplikacji Quizvel bazuje na szeroko stosownym i opisanym w literaturze bezstanowy protokole HTTP/1.1.

Protokół HTTP to protokół poziomu aplikacji dla hipermedialnych systemów informatycznych. W obu wersjach jest to protokół bezstanowy, tzn. ani serwer (ani klient) nie przechowuje informacji o tym, jakie były wcześniej zapytania pomiędzy określonym serwerem i klientem oraz nie posiada stanu wewnętrznego. Wykorzystuje wyłącznie protokół TCP do komunikacji klient-serwer. W najczęstszej konfiguracji działa na porcie 80. 

Protokół HTTP jest protokołem żądania i odpowiedzi. Klient wysyła żądanie do serwera w postaci linii żądania zawierającej informacje metody żądania, URI i wersji protokołu, a następnie komunikatu podobnego do wiadomości MIME  (ang. *Multipurpose Internet Mail Extensions*) zawierającego modyfikatory żądania, metainformacje o docelowym serwerze i  kliencie (dalej: *nagłówki*, ang. *headers*) oraz ewentualnie - oddzielone pustym wierszem – zawartość ciała (ang.*body*). W odpowiedzi otrzymuje kod statusu odpowiedzi, metainformacje i ciało oddzielone poprzez pusty wiersz.

W przypadku mojego protokołu przyjąłem częściową implementacje protokołu HTTP, ale kompatybilną z popularnymi klientami tego protokołu, a ponadto zakładam, że ciało ma zawsze postać struktury JSON.

Przykładowe żądanie bez dodatkowych informacji przedstawia:
    GET /sciezka HTTP/1.1
    User-Agent: curl/7.47.0

gdzie:

 - ```GET``` – metoda  HTTP wykorzystywaną do komunikacji, co w przypadku mojego protokołu oznacza wartość
   POST, PUT, GET lub DELETE,   
 - ```/sciezka``` – adres punktu wejścia dla danego
   żądania (ang. entry point),
 - ```HTTP/1.1``` – wersja protokołu HTTP,

W przypadku zamiaru przekazania ciała żądania żądania należy wykorzystać nagłówek `Content-Length` dla określenia rozmiaru ciala, a następnie po przesłaniu nagłówków przesłać dodatkowy enter i przesłać ciało w formie tekstowej.

Do uwierzytelniania użytkowników wykorzystano mechanizm „Basic Access Authentication” przedstawiony w RFC2617. Możliwość zachowania stanów pomiędzy żądaniami zapewniono poprzez powiązanie określonych danych z konkretnym użytkownikiem po stronie aplikacji. 

###Przykładowa transmisja
```
+ curl localhost:2000/user --http1.0 -v -X POST -d '{"username":"xyz","password":"xyz"}' -v
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 2000 (#0)
> POST /user HTTP/1.0
> Host: localhost:2000
> User-Agent: curl/7.47.0
> Accept: */*
> Content-Length: 35
> Content-Type: application/x-www-form-urlencoded
> 
* upload completely sent off: 35 out of 35 bytes
< HTTP/1.1 200 OK
< Content-Length: 24
< 
* Connection #0 to host localhost left intact
{"id":1,"status":"FAIL"}
+ curl localhost:2000/user --http1.0 -v
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 2000 (#0)
> GET /user HTTP/1.0
> Host: localhost:2000
> User-Agent: curl/7.47.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< Content-Length: 43
< 
* Connection #0 to host localhost left intact
{"users":[{"admin":true,"username":"xyz"}]}

+ curl xyz:xyz@localhost:2000/user/~promote --http1.0 -X POST -v -d '{"secret":"Hero"}'
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 2000 (#0)
* Server auth using Basic with user 'xyz'
> POST /user/~promote HTTP/1.0
> Host: localhost:2000
> Authorization: Basic eHl6Onh5eg==
> User-Agent: curl/7.47.0
> Accept: */*
> Content-Length: 17
> Content-Type: application/x-www-form-urlencoded
> 
* upload completely sent off: 17 out of 17 bytes
< HTTP/1.1 200 OK
< Content-Length: 15
< 
* Connection #0 to host localhost left intact
{"status":"OK"}

+ curl localhost:2000/user --http1.0 -v
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 2000 (#0)
> GET /user HTTP/1.0
> Host: localhost:2000
> User-Agent: curl/7.47.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< Content-Length: 44
< 
* Connection #0 to host localhost left intact
{"users":[{"admin":false,"username":"xyz"}]}

+ curl xyz:xyz@localhost:2000/~save --http1.0 -v
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 2000 (#0)
* Server auth using Basic with user 'xyz'
> GET /~save HTTP/1.0
> Host: localhost:2000
> Authorization: Basic eHl6Onh5eg==
> User-Agent: curl/7.47.0
> Accept: */*
> 
< HTTP/1.1 200 OK
< Content-Length: 23
< 
* Connection #0 to host localhost left intact
{"status":"Admin-only"}
```

###Obiekty
Celem uporządkowania i poprawienia czytelności niniejszej dokumentacji wyodrębniono obiekty stanowiące struktury danych, o których mowa poniżej. Każdy nagłówek sekcji określa metodę i adres w formacie wyrażenia regularnego.
 
####User
Obiekt typu „User” gromadzi informacje o użytkowniku. Jest to jednocześnie obiekt słownika JSON zawierający klucze:

 - ```username``` (łańcuch znaków) – nazwa użytkownika  (łańcuch znaków),
 - ```admin``` (łańcuch znaków)  – status administratora (łańcuch znaków), 

#### Question
Obiekt typu „Question” gromadzi informacje o indywidualnym pytaniu. Jest to jednocześnie obiekt słownika JSON zawierający klucze:

 - ```text``` (łańcuch znaków) – treść pytania  (łańcuch znaków),
 - ```correctId``` (łańcuch znaków) – numer poprawnej odpowiedzi (wartość liczbowa),
 - ```choices``` – lista obiektów typu „Choice”,

#### Choice
Obiekt typu „Choice” gromadzi informacje o indywidualnej opcji wyboru dla zadanego pytania. Jest to jednocześnie obiekt JSON zawierający klucze:

 - ```text``` (łańcuch znaków)  – treść odpowiedzi  (łańcuch znaków).

### Koncepcyjnie sposób transmisji
Koncepcyjnie można sposób działania niniejszej aplikacji dla poszczególnych elementów realizowanych przez aplikacje można przedstawić w formie diagramu.

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

### Wykaz punktów wejścia
#### GET — ^/
Punkt zwracający w każdym przypadku o powodzeniu transmisji służący do weryfikacji komunikacji z serwerem. Opcjonalny i nie jest wymagany do inicjalizacji. Wywołuje OKView.
#### POST — ^/user
Punkt odpowiedzialny za rejestracje użytkownika. Wywołuje UserRegisterView.
Wymaga przekazania dodatkowych informacji w postaci słownika o kluczach:

 - ```username``` (łańcuch znaków) – nazwa użytkownika (łańcuch znaków) ,
 - ```password``` (łańcuch znaków) – hasło użytkownika (łańcuch znaków) .

Zwraca informacje dodatkowe w postaci słownika o kluczach:

 - ```user_id```  (łańcuch znaków) – identyfikator użytkownika (int) ,
 - ```status``` (łańcuch znaków) – pole oznaczające status wykonania akcji przyjmuje wartości:
   -  ```FAIL``` (łańcuch znaków) dla niepowodzenia w tworzeniu użytkownika, 
   - ```OK```  (łańcuch znaków)  dla odpowiedniego i powodzenia.

#### POST — ^/user/~promote
Punkt odpowiedzialny za podniesienie uprawnień aktualnego użytkownika. Wymaga uwierzytelnienia. Wykorzystuje UserPromoteView.
Wymaga przekazania dodatkowych informacji w postaci słownika o kluczach:

 - ```secret``` (łańcuch znaków) – hasło ustawione podczas budowy aplikacji  (łańcuch znaków) ,

Zwraca informacje dodatkowe w postaci słownika o kluczach:
 
 - ```status``` (łańcuch znaków) – pole oznaczające status wykonania akcji przyjmuje wartości:
   - ```OK```  (łańcuch znaków) – powodzenie,
   - ```FAIL PROMOTE```  (łańcuch znaków) – niepowodzenie.

#### GET — ^/user
Punkt odpwoeidzialny za przesłanie listy użytkowników. Wykorzystuje UserListView.
Zwraca informacje dodatkowe w postaci słownika o kluczach:

 - ```users``` (łańcuch znaków) – lista obiektów typu „User”.

#### GET — ^/user/~current 
Punkt odpwoeidzialny za przesłanie danych na temat aktualnie zalogowanego użytkownika. Wykorzystuje UserListView. Zwraca informacje dodatkowe w postaci obiektu typu „User”.

#### GET — ^/~save
Punkt odpowiedzialny za zapisanie baz danych do pliku. Ma charakter administracyjny dla zapewnienia trwałości gromadzonych danych przez aplikacje. Wykorzystuje SaveJSONView. 
Nie przyjmuje informacji dodatkowych.
Zwraca informacje dodatkowe w postaci słownika o kluczach:

 - ```status``` (łańcuch znaków) – jedna z poniższych wartości:
   - ```OK``` (łańcuch znaków) – baza danych została zapisana pomyślnie,
   - ```Admin-only``` (łańcuch znaków) – brak uwierzytelnienia na konto administratora.

#### POST — ^/question
Punkt odpowiedzialny za zapisanie pytania i odpowiadających mu odpowiedzi. Ma charakter administracyjny i wymaga uwierzytelnienia na konto administratora. Wykorzystuje QuestionCreateView.
Przyjmuje informacje dodatkowe w postaci obiektu typu „Question”.
Zwraca informacje dodatkowe w postaci słownika o kluczach:

 - ```status``` (łańcuch znaków) – wartość „OK”  (łańcuch znaków)  określający status zapisu danych,
 - ```id``` (łańcuch znaków) – identyfikator zapisanego pytania (wartość liczbowa).

#### GET — ^/question
Punkt odpowiedzialny za sporządzenie listy pytań. Ma charakter administracyjny i wymaga uwierzytelnienia na konto administratora. Wykorzystuje QuestionListView.
Zwraca infromacje dodatkowe w postaci słownika o kluczach:

 - ```questions``` (łańcuch znaków) – lista obiektów typu „Question”,

#### GET — ^/question/([0-9]+)
Punkt odpowiedzialny za sporządzenie listy pytań. W adresie przyjmuje numer pytania, które będzie pobrane. Ma charakter administracyjny i wymaga uwierzytelnienia na konto administratora. Wykorzystuje QuestionGetView.
Zwraca informacje dodatkowe w postaci obiektu typu „Question”.

#### POST — ^/question/([0-9]+)
Punkt odpowiedzialny za aktualizacje pytania. W adresie przyjmuje numer pytania, które będzie zaktualizowane. Ma charakter administracyjny i wymaga uwierzytelnienia na konto administratora.  Wykorzystuje QuestionUpdateView.
Zwraca informacje dodatkowe w postaci słownika o kluczach:

 - ```status``` (łańcuch znaków) – wartość „OK” określająca powodzenie aktualizacji pytania.

#### DELETE — ^/question/(0-9]+)
Punkt odpowiedzialny za usuwanie pytania. W adresie przyjmuje QuestionDeleteView.
Zwraca informacje dodatkowe w postaci słownika o kluczach:

 - ```status``` (łańcuch znaków) – wartość „OK” określająca powodzenie usuwania pytania.

#### GET — ^/survey
Punkt odpowiedzialny za przydzielenie pytań dla danego użytkownika. Wymaga uwierzytelnienia. Wykorzystuje SurveyGetView.
Zwraca informacje dodatkowe w postaci słownika o kluczach:

 - ```questions``` (łańcuch znaków) – listy zadanych obiektów typu „Question”.

#### POST — ^/survey
Punkt odpowiedzialny za wetyfikacje odpowiedzi dla danego użytkownika. Wykorzystuje SurveyCheckView.
Zwraca inforamcje dodatkowe w postaci słownika o kluczach:

 - ```point``` (łańcuch znaków) – liczba osiągniętych punktów (wartość liczbowa).
