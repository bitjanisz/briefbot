# BriefBot

A brief description of your project, what it does, and the problem it solves.

---

## 🛠️ Prerequisites

Before you begin, ensure you have the following tools installed:

* [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) (or newer)
* [Apache Maven](https://maven.apache.org/download.cgi)
* [Docker](https://www.docker.com/products/docker-desktop/) and Docker Compose

---

## 🚀 Getting Started (First Time Setup)

Follow these steps to configure and run the project locally.

### Step 1: Configure the Database (Docker)

The PostgreSQL database runs in a Docker container. <br>Its configuration (user, password, database name) is loaded from
an `.env` file that you must create locally.

1. In the project's **root directory** (next to `docker-compose.yml`), create a file named `.env`.
2. Paste the following content into it and fill in your values:

   ```
   # This file is ignored by Git (.gitignore)
   
   # Credentials for the Postgres container
   POSTGRES_USER=your_db_user
   POSTGRES_PASSWORD=your_secure_password
   POSTGRES_DB=your_db_name
   ```

### Step 2: Run the Database

With the `.env` file in place, start the database container in detached mode:

```bash
  docker-compose up -d
```

### Step 3: Configure the Application (Spring Boot)

The Spring Boot application needs its own configuration to connect to the database. <br>We use a dev-credentials.yml
file for this, which is ignored by Git.

1. Navigate to the src/main/resources/ directory.
2. Create a new file named dev-credentials.yml.
3. Paste the following content into it and fill in your values:

```# This file is ignored by Git (.gitignore)
# It contains the developer's private credentials

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/use the one from .env file
    username: use the one from .env file
    password: use the one from .env file
```

http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs