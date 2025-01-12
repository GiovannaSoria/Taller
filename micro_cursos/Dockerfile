# Usa una imagen ligera de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR desde la carpeta target al contenedor
COPY micro_cursos-0.0.1-SNAPSHOT.jar app.jar


# Expone el puerto que utiliza la aplicación
EXPOSE 8002

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]
