# Usa una imagen base con Java 21
FROM openjdk:21-jdk-slim
# Define un argumento para el archivo .jar
ARG JAR_FILE=target/alumnado-0.0.1.jar
# Copia el archivo .jar al contenedor
COPY ${JAR_FILE} app_alumnado.jar
# Expone el puerto de la aplicación
EXPOSE 8080
# Comando para ejecutar el .jar
ENTRYPOINT ["java", "-jar", "app_alumnado.jar"]
