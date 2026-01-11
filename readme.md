# BriefBot

A brief description of your project, what it does, and the problem it solves.

---

## 🛠️ Prerequisites

Before you begin, ensure you have the following tools installed:

* [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) (or newer)
* [Apache Maven](https://maven.apache.org/download.cgi)
* [Docker](https://www.docker.com/products/docker-desktop/) and Docker Compose

---

## ▶️ Run: Backend + Frontend (standalone)

Run the backend and frontend as two separate processes. Choose your database profile first.

Profiles quick matrix:
- H2 (in-memory): Maven `dev` + Spring `local,h2` (no Docker required)
- Postgres (Docker): Maven `prod` + Spring `local,dev-postgres` (requires `docker-compose up -d`)

Default ports:
- Frontend: http://localhost:3000
- Backend: http://localhost:8080

### Local application profile (OAuth & redirect)

In addition to the DB profile, always enable the Spring profile `local`. This profile holds your local OAuth and redirect settings.

1) Create or edit the file `app/src/main/resources/application-local.yml` and fill in your values:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: <your_google_oauth_client_id>
            client-secret: <your_google_oauth_client_secret>

security:
  redirect:
    default-success-url: http://localhost:3000
```

3) Start the backend with Maven profile `prod` and Spring profiles `local,dev-postgres`
```bash
./mvnw -pl app -am spring-boot:run -Pprod -Dspring-boot.run.profiles=local,dev-postgres
```

Verify:
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Frontend: http://localhost:3000

Notes:
- CORS: The React dev server (3000) calls the API on port 8080; ensure CORS for http://localhost:3000 is enabled in Spring.
- Stop services: press Ctrl+C in terminals; for Postgres, stop with `docker-compose down` (data persists unless volumes are removed).
- Packaged mode: If you prefer serving the built frontend from Spring Boot, use the Maven build that copies `frontend/dist` into `app/src/main/resources/static/` via the configured Maven plugins. That flow is separate from this standalone mode.
