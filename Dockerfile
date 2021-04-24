FROM  adoptopenjdk/openjdk11:alpine-jre
COPY target/k8s-project-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080/tcp
ENTRYPOINT [ "java", "-jar", "/app.jar" ]