FROM eclipse-temurin:21 as build

WORKDIR /app

COPY ./.mvn ./.mvn
COPY ./mvnw ./mvnw
COPY ./pom.xml ./pom.xml
COPY ./src ./src

RUN ./mvnw -Dmaven.test.skip package

# ----------------------------------------------------- #

FROM eclipse-temurin:21

COPY --from=build /app/target/bookiero-0.0.56.jar app.jar
CMD [ "java", "-jar", "./app.jar" ]
