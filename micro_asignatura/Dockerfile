# Usar una imagen base con Java
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY target/micro_asignatura-0.0.1-SNAPSHOT.jar /app/micro_asignatura.jar

# Exponer el puerto en el que la aplicación escuchará (puedes modificar el puerto según tu aplicación)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "micro_asignatura.jar"]
