FROM maven:3.5-jdk-8-alpine

WORKDIR /app

COPY . /app/

COPY /target/teduscheduler-0.0.1-SNAPSHOT.jar /teduscheduler-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/teduscheduler-0.0.1-SNAPSHOT.jar"]
