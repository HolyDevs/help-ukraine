# IDE
## zeby odpalic backend lokalnie przez IDE trzeba dodac nastepujace argumenty:
### --add-opens java.base/java.lang=ALL-UNNAMED - zeby dzialal orika mapper
### -Dspring.profiles.active=local - dzieki temu uzywamy in-memory bazki ktorą inicjalizuje schema.sql i data.sql
# AUTENTYKACJA LOKALNIE
 mozna sie autentykowac userem tworzonym w data.sql:
 1. strzał POST pod /auth/login -> body jako x-www-form-urlencoded (username - **jan.uciekinier@gmail.com** (**REFUGEE**), 
 **jan.pomocny@gmail.com** (**HOST**), password - **aaa**)
 2. dostajemy access_token (10 min waznosci) i refresh_token (30 min)
 3. strzał pod endpoint musi miec header Authorization z wartoscia Bearer <access_token>
 4. refresh tokenów -> strzał GET pod /auth/refresh plus header Authorization z wartoscia Bearer <refresh_token>
# KOMPILACJA & DOCKER
## kompilacja z utworzeniem obrazu dockerowego:
 - mvn clean install -Dprofile=local - obraz do odpalenia lokalnie na bazce h2
 - mvn clean install -Dprofile=dev-gcp - obraz do odpalenia na srodowisku (bazka GCP)
 - mvn clean install -Dprofile=dev-h2 - obraz do odpalenia na srodowisku (bazka h2 in memory)
## start kontenera lokalnie:
### docker run -p 8080:8080 help-ukraine/help-ukraine 
## tagowanie obrazu dockerowego:
### docker tag help-ukraine/help-ukraine gcr.io/help-ukraine-344011/help-ukraine
## dodanie nowej wersji obrazu na środowisko:
### docker push gcr.io/help-ukraine-344011/help-ukraine