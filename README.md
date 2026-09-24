# oauth-jwt-mock-api

Spring Boot 3.3.1 / Java 21 demo API that issues mock OAuth JWTs and exposes a probabilistic mock submit endpoint for testing client resilience (retries, error handling, auth flows).

Maven artifact: `com.demo:oauth-jwt-mock-api:0.0.1-SNAPSHOT`  
Main class: `com.demo.mockapi.MockApiApplication`  
Default port: **8080** (`application.yml`)

**Demo only — not for production.**

## Prerequisites

- JDK 21+
- Maven 3.9+ (no Maven Wrapper in this repo)

## Run

```bash
mvn spring-boot:run
```

Optional packaged run:

```bash
mvn -DskipTests package && java -jar target/oauth-jwt-mock-api-0.0.1-SNAPSHOT.jar
```

Server listens on `http://localhost:8080`.

## Endpoints

### Auth

| Path | Auth | Notes |
|------|------|-------|
| `/api/auth/**` | `permitAll` | Public auth routes |
| Everything else | Bearer JWT required | |

**`POST /api/auth/token`**

Request body:

```json
{
  "clientId": "demo-client",
  "clientSecret": "anything"
}
```

- The server only checks that `clientId` is non-blank.
- `clientSecret` is present on the DTO but is **not checked today**.

Response:

```json
{
  "accessToken": "<jwt>",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

Example:

```bash
curl -s -X POST http://localhost:8080/api/auth/token \
  -H 'Content-Type: application/json' \
  -d '{"clientId":"demo-client","clientSecret":"ignored"}'
```

### Mock submit

**`POST /api/mock/submit`** — requires `Authorization: Bearer <accessToken>`

Request body:

```json
{
  "transactionId": "tx-001",
  "payload": "hello"
}
```

Probabilistic responses (approximate):

- ~90% → `200 OK`
- ~5% → `401 Unauthorized` (simulated)
- ~5% → `500 Internal Server Error` (simulated)

Example:

```bash
TOKEN="<accessToken from /api/auth/token>"

curl -s -X POST http://localhost:8080/api/mock/submit \
  -H "Authorization: Bearer $TOKEN" \
  -H 'Content-Type: application/json' \
  -d '{"transactionId":"tx-001","payload":"hello"}'
```

## Configuration

See `src/main/resources/application.yml`:

- `server.port` (default `8080`)
- `jwt.secret` — signing key for demo JWTs (do not use the committed demo value in real environments)
- `jwt.expiration-ms` — token lifetime in milliseconds (default aligns with `expiresIn: 3600`)

## Project layout (optional)

```
src/main/java/com/demo/mockapi/
  MockApiApplication.java
  config/          # security / JWT setup
  controller/      # AuthController, MockController
  dto/
  exception/
  service/
src/main/resources/
  application.yml
```
