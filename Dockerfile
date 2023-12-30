FROM bellsoft/liberica-openjdk-alpine:19
WORKDIR /
COPY ./src/main/resources/application.yml /
COPY ./build/libs/Camunda-0.0.1-SNAPSHOT.jar /app.jar
COPY ./src/main/resources/*.cer  /
RUN keytool -import -trustcacerts -file root.cer -alias zeebe -keystore $JAVA_HOME/lib/security/cacerts -noprompt \
    && keytool -import -trustcacerts -file subroot.cer -alias subzeebe -keystore $JAVA_HOME/lib/security/cacerts -noprompt
EXPOSE 8080
CMD java -jar app.jar --spring.config.location=file:./application.yml