## Siema
## zeby odpalic backend lokalnie przez IDE trzeba dodac nastepujace argumenty:
### --add-opens java.base/java.lang=ALL-UNNAMED - zeby dzialal orika mapper
## mozna sie autentykowac userem tworzonym w data.sql:
### 1. strzał POST pod /auth/login -> body jako x-www-form-urlencoded (username - jan.lokaly@gmail.com, password - aaa)
### 2. dostajemy access_token (10 min waznosci) i refresh_token (30 min)
### 3. strzał pod endpoint musi miec header Authorization z wartoscia Bearer <access_token>
### 4. refresh tokenów -> strzał GET pod /auth/refresh plus header Authorization z wartoscia Bearer <refresh_token>
### -Dspring.profiles.active=local - dzieki temu uzywamy in-memory bazki ktorą inicjalizuje schema.sql i data.sql
##  mvn clean package
## kompilacja z utworzeniem obrazu dockera mvn clean install
## start kontenera docker run  -p 8080:8080 help-ukraine/help-ukraine 


docker tag help-ukraine/help-ukraine gcr.io/help-ukraine-344011/help-ukraine
docker push gcr.io/help-ukraine-344011/help-ukraine