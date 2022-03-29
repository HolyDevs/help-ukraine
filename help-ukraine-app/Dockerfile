FROM eclipse-temurin:17

ENV TZ="Europe/Warsaw"

COPY target/help-ukraine-app-0.0.0-SNAPSHOT.jar /usr/bin/help-ukraine-app/app.jar

WORKDIR /usr/bin/help-ukraine-app

EXPOSE 8080

CMD ["java", "-jar", "app.jar", "--add-opens", "java.base/java.lang=ALL-UNNAMED"]