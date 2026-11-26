# AGENTS.md

This file provides guidance to coding agents (Claude Code and other AGENTS.md-aware tools) working with code in this repository.

## Project Overview

MaKs Point of Sale (MaKsPOS) is a lightweight, file-based POS system built with Quarkus and Kotlin. It provides a REST API for product management, order processing, and sales statistics, combined with a Vue.js frontend served via Quarkus Quinoa.

**Key design choice**: All data (products, orders, config) is persisted in JSON files on disk — no database required.

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.4.20 |
| Framework | Quarkus 4.0.1 |
| Java target | Java 25 |
| Frontend | Vue.js (via Quarkus Quinoa 4.0.0) |
| REST | Quarkus REST (RESTEasy Reactive) + Jackson 3 (`tools.jackson`) |
| Validation | Hibernate Validator (Jakarta Validation) |
| OpenAPI | SmallRye OpenAPI |
| Health | SmallRye Health |
| Metrics | Micrometer + Prometheus registry |
| Container | Jib (quarkus-container-image-jib) |
| Testing | JUnit 5 + REST Assured (Kotlin extensions) + Cypress |
| Distribution | GitHub Packages |

## Repository Structure

```
.
├── pom.xml                          # Maven project (standalone, no parent POM)
├── data/
│   └── products.json                # Sample product data
├── src/
│   ├── main/
│   │   ├── kotlin/de/mkallfass/makspos/
│   │   │   ├── domain/              # Data classes (Product, Order, LineItem, Config, ...)
│   │   │   ├── rest/
│   │   │   │   ├── endpoint/        # JAX-RS resources (Products, Orders, Config)
│   │   │   │   └── model/           # Error response model
│   │   │   └── service/             # Services + JSON file repositories
│   │   └── webui/                   # Vue.js frontend (served via Quinoa)
│   └── test/
│       ├── kotlin/                  # Unit and integration tests
│       └── requests/                # HTTP client test files (.http)
```

## Development Commands

```bash
# Start dev mode with live reload (backend + frontend)
./mvnw quarkus:dev

# Run unit tests
./mvnw test

# Run integration tests (packages the app and launches it)
./mvnw verify -DskipITs=false

# Run frontend unit tests (from src/main/webui)
npm run test:unit          # single run, as used in CI
npm run test:unit:watch    # interactive watch mode

# Build
./mvnw clean package

# Native build
./mvnw package -Pnative

# Build container image via Jib
./mvnw package -Dquarkus.container-image.build=true
```

## Architecture

### Data Layer (File-based)

There is **no database**. All persistence is done through JSON files on disk:

- `ProductRepository` reads products from a JSON file configured via `data.directory` + `product.repository` properties.
- `OrderRepository` and `ConfigRepository` follow the same pattern.
- The data directory is created automatically if it does not exist.
- The repositories use their own Jackson mapper (`jacksonObjectMapper()`, Jackson 3). Since the
  Jackson 3 upgrade the order `date` is written as an ISO-8601 string; lines written before that
  carry a numeric epoch value. `orders.jsonl` is append-only, so both forms occur in one file and
  both stay readable — `OrderRepositoryMapperTest` pins that.

### REST API

All endpoints are under `/api/`:

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/products` | List all products |
| `POST` | `/api/orders` | Create an order |
| `GET` | `/api/orders/statistics` | Get order statistics |
| `GET` | `/api/config` | Get POS configuration |

OpenAPI UI is available at `/q/swagger-ui` in dev mode.

### Domain Model

- **`Product`** — id, name, description, price (extends `BaseProduct`)
- **`Order`** — id, date, lineitems, total; requires non-empty `lineitems`
- **`LineItem`** — references a product with quantity
- **`Config`** — currency, paymentPresets, labels (UI strings)
- **`OrderStatistics`** / **`ProductStatistics`** — aggregated reporting data

### Configuration Properties

Key `application.properties` entries:
```properties
data.directory=<path to data directory>
product.repository=products.json
```

### Frontend

Located in `src/main/webui/`. When running `./mvnw quarkus:dev`, the frontend is built and served automatically by Quinoa. The frontend can also be developed standalone using npm in that directory.

## Testing

- **Unit tests** (`*Test.kt`): Use `@QuarkusTest`, test individual endpoints in isolation.
- **Integration tests** (`*IT.kt`): Use `@QuarkusIntegrationTest`, run against the packaged application.
  They inherit their assertions from the matching `@QuarkusTest` class, so they need the same
  fixtures. The packaged artifact runs in the `prod` profile, where `%test.data.directory` does not
  apply — `ItDataDirectory` (a `QuarkusTestResourceLifecycleManager`) therefore copies
  `src/test/resources/data` into a temp directory and hands its path to the launched process as
  `-Ddata.directory=…`. Every `*IT.kt` needs `@WithTestResource(ItDataDirectory::class, scope =
  MATCHING_RESOURCES)`; without it the tests run against the demo catalog in `data/` and fail.
  That temp directory lives on the machine running the build, so the application has to be launched
  there too. `quarkus.test.integration-test-artifact-type` (a failsafe system property in `pom.xml`,
  `jar` by default and `native` in the `native` profile) pins that. Without it, a build that also
  creates the container image — `-Dquarkus.container-image.build=true`, which is how CI builds —
  produces the artifact type `jar-container`, Quarkus starts the application in a container, and the
  host path arrives there as an environment variable pointing at nothing: every endpoint that reads
  a file answers 500.
- **HTTP test files**: `src/test/requests/` contains `.http` files for manual API testing.
- Integration tests are skipped by default (`skipITs=true`); enable with `-DskipITs=false`.
  CI passes that flag, so the ITs do run on every push.
- **Container image**: because the ITs run against the jar, the image gets its own CI step after the
  push — it starts the image, waits for `/q/health/ready` and checks that `/` serves the frontend.
  It runs without a data directory, so it is a start-up check, not an API test.
- **Frontend unit tests** (Vitest + jsdom): specs live in `src/main/webui/src/**/__tests__/*.spec.ts`,
  shared fixtures in `src/__tests__/fixtures.ts`. They run in their own `webui-tests` CI job —
  Quinoa builds the frontend during the Maven build but does not run its tests.
- **Frontend E2E tests** (Cypress): `src/main/webui/cypress/`, run with `npm run test:e2e`.

## Code Conventions

- **Kotlin only** — no Java source files
- Follow Kotlin idioms (data classes, extension functions, `lateinit` for injected fields)
- JAX-RS endpoints use `@Path`, `@ApplicationScoped` for services (all-open plugin configured)
- All REST resources include MicroProfile OpenAPI annotations (`@Operation`, `@APIResponse`, `@Schema`)
- Validation via Jakarta Bean Validation annotations on domain classes

## CI/CD

- **GitHub Actions**: `.github/workflows/maven-build.yml` — builds and tests on every push/PR
- **Distribution**: Artifacts published to GitHub Packages (`https://maven.pkg.github.com/mkallfass/makspos`)