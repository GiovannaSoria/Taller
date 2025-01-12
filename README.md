# Taller
```markdown
# Proyecto Distribuido: micro_asignatura y micro_cursos

Este proyecto contiene **dos microservicios** (micro_asignatura y micro_cursos) que se conectan a una **base de datos MySQL** en contenedores Docker. La base de datos utilizada se llama `sisdb2025`.

---

## 1. Estructura de Carpetas

```
Taller/
├── docker-compose.yml              <-- Archivo Compose unificado para todos los servicios
├── micro_asignatura/
│   ├── Dockerfile
│   ├── pom.xml
│   └── target/
│       └── micro_asignatura-0.0.1-SNAPSHOT.jar
└── micro_cursos/
    ├── Dockerfile
    ├── pom.xml
    └── target/
        └── micro_cursos-0.0.1-SNAPSHOT.jar
```

- **docker-compose.yml**: Define los servicios de Docker (la base de datos `db`, y los microservicios `micro_asignatura` y `micro_cursos`).
- **micro_asignatura/**  
  - **Dockerfile** para construir la imagen de este microservicio.  
  - **target/micro_asignatura-0.0.1-SNAPSHOT.jar** (generado tras compilar con Maven, por ejemplo).
- **micro_cursos/**  
  - **Dockerfile** y su JAR (p. ej., `micro_cursos-0.0.1-SNAPSHOT.jar`).

---

## 2. Requisitos Previos

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) o Docker Engine instalado.
- [Docker Compose v2](https://docs.docker.com/compose/) (ya viene con Docker Desktop en versiones recientes).
- Maven o Gradle (para compilar los proyectos y generar los `.jar`).

---

## 3. Configuración de los Microservicios

### 3.1 micro_asignatura

Ejemplo de `application.properties`:

```properties
spring.application.name=micro_asignatura
server.port=8001

spring.datasource.url=jdbc:mysql://db:3306/sisdb2025?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql=TRACE
```

**Dockerfile** (dentro de `micro_asignatura/`):
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/micro_asignatura-0.0.1-SNAPSHOT.jar micro_asignatura.jar
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "micro_asignatura.jar"]
```

### 3.2 micro_cursos

Ejemplo de `application.properties`:

```properties
spring.application.name=micro_cursos
server.port=8002

spring.datasource.url=jdbc:mysql://db:3306/sisdb2025?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql=TRACE
```

**Dockerfile** (dentro de `micro_cursos/`):
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/micro_cursos-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8002
CMD ["java", "-jar", "app.jar"]
```

---

## 4. Archivo docker-compose.yml (Unificado)

Dentro de `Taller/`:

```yaml
services:
  db:
    image: mysql:8.0
    container_name: db
    environment:
      MYSQL_ROOT_PASSWORD: 123
      MYSQL_DATABASE: sisdb2025
    ports:
      - "3306:3306"
    networks:
      - micro_network

  micro_asignatura:
    build:
      context: ./micro_asignatura
    container_name: micro_asignatura
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/sisdb2025?useSSL=false&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 123
    ports:
      - "8001:8001"
    networks:
      - micro_network

  micro_cursos:
    build:
      context: ./micro_cursos
    container_name: micro_cursos
    depends_on:
      - db
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/sisdb2025?useSSL=false&allowPublicKeyRetrieval=true
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 123
    ports:
      - "8002:8002"
    networks:
      - micro_network

networks:
  micro_network:
    driver: bridge
```

> **Nota**: En Docker Compose v2, ya no es obligatorio poner `version: "3.8"`.  
> Si te aparece una advertencia al usar `version:`, simplemente elimínala.

---

## 5. Pasos para Levantar el Proyecto

1. **Compilar los microservicios**  
   - En cada carpeta (`micro_asignatura/` y `micro_cursos/`), ejecuta:
     ```bash
     mvn clean package
     ```
     (o el comando de build que uses) para generar los `.jar` en `target/`.

2. **Ejecutar Docker Compose**  
   - Ubícate en la carpeta raíz (`Taller/`) donde se encuentra el `docker-compose.yml`.
   - Ejecuta:
     ```bash
     docker compose up -d --build
     ```
     Esto construirá las imágenes y levantará:
     - El contenedor `db` (MySQL con la base `sisdb2025`).
     - El contenedor `micro_asignatura` (en el puerto 8001).
     - El contenedor `micro_cursos` (en el puerto 8002).

3. **Verificar contenedores**  
   - Revisa con `docker ps`. Debes ver algo como:
     ```
     CONTAINER ID   IMAGE                      ...   STATUS   ...   NAMES
     abc123         taller-micro_asignatura    ...   Up ...
     def456         taller-micro_cursos        ...   Up ...
     ghi789         mysql:8.0                  ...   Up ...
     ```

4. **Probar la conexión**  
   - **micro_asignatura**: [http://localhost:8001](http://localhost:8001/)  
   - **micro_cursos**: [http://localhost:8002](http://localhost:8002/)  
   - Para la base de datos (puerto `3306`), se usa un cliente MySQL (WorkBench, etc.) o la consola dentro del contenedor:
     ```bash
     docker exec -it db bash
     mysql -u root -p
     # Contraseña: 123
     ```

---

## 6. Endpoints Principales

### micro_cursos
- `GET /api/cursos`  -> Lista todos los cursos.  
- `GET /api/cursos/{id}` -> Obtiene un curso por ID.  
- `POST /api/cursos`  -> Crea un nuevo curso (en JSON).  
- `PUT /api/cursos/{id}`  -> Modifica un curso existente.  
- `DELETE /api/cursos/{id}` -> Elimina un curso.

### micro_asignatura
- `GET /api/asignaturas` -> Lista todas las asignaturas.  
- `GET /api/asignaturas/{id}` -> Obtiene una asignatura por ID.  
- `POST /api/asignaturas` -> Crea una nueva asignatura.  
- `PUT /api/asignaturas/{id}` -> Modifica una asignatura.  
- `DELETE /api/asignaturas/{id}` -> Elimina una asignatura.

---

## 7. Inserción de Datos Ejemplo (Postman)

Para crear un nuevo curso, envía un POST a `http://localhost:8002/api/cursos` con **Body (raw JSON)**:

```json
{
  "nombre": "Calculo Integral",
  "descripcion": "Curso de matemáticas avanzadas sobre integrales",
  "creditos": 5
}
```

Para crear una nueva asignatura, envía un POST a `http://localhost:8001/api/asignaturas`:

```json
{
  "codigo": "MAT101",
  "nombre": "Matemáticas Básicas",
  "descripcion": "Introducción al álgebra y trigonometría",
  "creditos": 4,
  "nivel": 1
}
```
En ambos casos, incluye encabezado `Content-Type: application/json`.

---

## 8. Observaciones

- Si entras a `http://localhost:3306` en el navegador, recibirás un error, ya que MySQL no sirve contenido HTTP, sino su propio protocolo.
- Los endpoints de cada microservicio se basan en tus **controladores** (Controllers) y las rutas definidas con `@RequestMapping`.
- Si deseas mostrar algo en la ruta raíz (`/`), crea un `@RestController` con `@GetMapping("/")`.

---

## 9. Limpieza y Reinicio

Si en algún momento quieres **limpiar** los contenedores e iniciar de cero:

```bash
docker compose down -v
docker compose up -d --build
```

Esto elimina contenedores, volúmenes, y vuelve a crearlos. Ten cuidado si tienes datos importantes en la BD.

---

## 10. Conclusión

Con esta configuración, cada microservicio funciona en su propio contenedor, ambos se conectan a la misma base MySQL (`sisdb2025`), y exponen endpoints REST:

- **micro_asignatura** en `:8001`
- **micro_cursos** en `:8002`
- **MySQL** expuesto localmente en `:3306`

