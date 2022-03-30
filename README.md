## Siema
## zeby odpalic backend lokalnie przez IDE trzeba dodac nastepujace argumenty:
### --add-opens java.base/java.lang=ALL-UNNAMED - zeby dzialal orika mapper
### -Dspring.profiles.active=local - dzieki temu uzywamy in-memory bazki ktorą inicjalizuje schema.sql i data.sql
##  mvn clean package
## kompilacja z utworzeniem obrazu dockera mvn clean install
## start kontenera docker run  -p 8080:8080 help-ukraine/help-ukraine 


docker tag help-ukraine/help-ukraine gcr.io/help-ukraine-344011/help-ukraine
docker push gcr.io/help-ukraine-344011/help-ukraine