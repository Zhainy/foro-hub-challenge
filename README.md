# Foro Hub Challenge 🚀

[![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.11-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-Wrapper-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/Auth-JWT-000000?logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?logo=swagger&logoColor=black)](https://swagger.io/)

API REST para la gestion de topicos, usuarios y respuestas del challenge Foro Hub (ONE + Alura), con autenticacion JWT y documentacion Swagger.

## 🧭 Tabla de contenido

- [📝 Descripcion](#descripcion)
- [✨ Caracteristicas](#caracteristicas)
- [🛠️ Stack tecnologico](#stack-tecnologico)
- [📁 Estructura del proyecto](#estructura-del-proyecto)
- [✅ Requisitos previos](#requisitos-previos)
- [⚙️ Configuracion](#configuracion)
- [▶️ Ejecucion local](#ejecucion-local)
- [📖 Documentacion de API](#documentacion-de-api)
- [🔐 Autenticacion](#autenticacion)
- [🧭 Endpoints principales](#endpoints-principales)
- [🧪 Coleccion de Insomnia](#coleccion-de-insomnia)

<a id="descripcion"></a>
## 📝 Descripcion

Foro Hub permite publicar topicos, responder discusiones y administrar usuarios mediante una API segura basada en tokens JWT.

<a id="caracteristicas"></a>
## ✨ Caracteristicas

- Login con `POST /login` y entrega de JWT.
- CRUD de topicos en `/topicos`.
- Alta, listado y borrado de usuarios en `/usuarios`.
- CRUD de respuestas en `/respuestas`.
- Seguridad stateless con Spring Security.
- Migraciones de base de datos con Flyway.

<a id="stack-tecnologico"></a>
## 🛠️ Stack tecnologico

- Java 17
- Spring Boot 3.5.11
- Spring Web
- Spring Security
- Spring Data JPA
- Bean Validation
- Flyway
- MySQL
- Java JWT (`com.auth0:java-jwt`)
- Springdoc OpenAPI

<a id="estructura-del-proyecto"></a>
## 📁 Estructura del proyecto

```text
src/
  main/
    java/com/alura/foro_hub_challenge/
      controller/
      domain/
      infra/
    resources/
      application.properties
      db/migration/
```

<a id="requisitos-previos"></a>
## ✅ Requisitos previos

- JDK 17 o superior
- MySQL en ejecucion
- Maven (o usar `mvnw` incluido)
- Variables de entorno:
  - `DB_MYSQL_PASSWORD`
  - `JWT_SECRET` (opcional)

<a id="configuracion"></a>
## ⚙️ Configuracion

Archivo `src/main/resources/application.properties` (datos principales):

```properties
spring.application.name=foro-hub-challenge
server.port=8081
spring.datasource.url=jdbc:mysql://localhost/foro_hub_challenge
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=${DB_MYSQL_PASSWORD}
api.security.token.secret=${JWT_SECRET:123456}
```

<a id="ejecucion-local"></a>
## ▶️ Ejecucion local

```powershell
cd "D:\ONE\Foro-Hub Challenge\foro-hub-challenge"
.\mvnw.cmd clean spring-boot:run
```

Base URL local:

`http://localhost:8081`

<a id="documentacion-de-api"></a>
## 📖 Documentacion de API

- Swagger UI: `http://localhost:8081/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8081/v3/api-docs`

<a id="autenticacion"></a>
## 🔐 Autenticacion

1. Inicia sesion con `POST /login`.
2. Copia el token de la respuesta.
3. Envia el token en endpoints protegidos:

```http
Authorization: Bearer <tu_token>
```

<a id="endpoints-principales"></a>
## 🧭 Endpoints principales

| Recurso | Metodo | Ruta |
|---|---|---|
| 🔑 Auth | POST | `/login` |
| 🧵 Topicos | GET | `/topicos` |
| 🧵 Topicos | GET | `/topicos/{id}` |
| 🧵 Topicos | POST | `/topicos` |
| 🧵 Topicos | PUT | `/topicos/{id}` |
| 🧵 Topicos | DELETE | `/topicos/{id}` |
| 👤 Usuarios | GET | `/usuarios` |
| 👤 Usuarios | POST | `/usuarios` |
| 👤 Usuarios | PUT | `/usuarios` |
| 👤 Usuarios | DELETE | `/usuarios/{id}` |
| 💬 Respuestas | GET | `/respuestas` |
| 💬 Respuestas | GET | `/respuestas/{id}` |
| 💬 Respuestas | POST | `/respuestas` |
| 💬 Respuestas | PUT | `/respuestas/{id}` |
| 💬 Respuestas | DELETE | `/respuestas/{id}` |

<a id="coleccion-de-insomnia"></a>
## 🧪 Coleccion de Insomnia

El archivo `insomnia/Insomnia_request.yaml` esta incluido para que puedas importar una coleccion lista y probar la API rapidamente con requests preconfiguradas.

Pasos rapidos:

1. Abre Insomnia.
2. Ve a **Create > Import**.
3. Selecciona el archivo `insomnia/Insomnia_request.yaml`.
4. Ajusta `base_url` y el token `bearer` segun tu entorno.

---

## 👥 Autor

Desarrollado por **Nicole Fernández** como parte de *Foro-Hub Challenge del Programa ONE & Alura LATAM*.

---

<div align="center">

**⭐ Si este proyecto te fue útil, considera darle una estrella ⭐**

Hecho con ❤️ usando *Spring Boot*

</div>