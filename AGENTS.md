# AGENTS.md

This file provides guidance to coding agents (Claude Code and other AGENTS.md-aware tools) working with code in this repository.

## Project Overview

MaKs Point of Sale (MaKsPOS) is a lightweight, file-based POS system built with Quarkus and Kotlin. It provides a REST API for product management, order processing, and sales statistics, combined with a Vue.js frontend served via Quarkus Quinoa.

**Key design choice**: All data (products, orders, config) is persisted in JSON files on disk — no database required.

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.3.10 |
| Framework | Quarkus 3.32.2 |
| Java target | Java 25 |
| Frontend | Vue.js (via Quarkus Quinoa 2.7.2) |
| REST | Quarkus REST (RESTEasy Reactive) + Jackson |
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

# Run integration tests (requires running instance)
./mvnw verify -DskipITs=false

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
- **HTTP test files**: `src/test/requests/` contains `.http` files for manual API testing.
- Integration tests are skipped by default (`skipITs=true`); enable with `-DskipITs=false`.

## Code Conventions

- **Kotlin only** — no Java source files
- Follow Kotlin idioms (data classes, extension functions, `lateinit` for injected fields)
- JAX-RS endpoints use `@Path`, `@ApplicationScoped` for services (all-open plugin configured)
- All REST resources include MicroProfile OpenAPI annotations (`@Operation`, `@APIResponse`, `@Schema`)
- Validation via Jakarta Bean Validation annotations on domain classes

## CI/CD

- **GitHub Actions**: `.github/workflows/maven-build.yml` — builds and tests on every push/PR
- **Distribution**: Artifacts published to GitHub Packages (`https://maven.pkg.github.com/mkallfass/makspos`)