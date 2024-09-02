# MaKsPOS

Simple Point of Sales system for clubs, street festivals and events that do not require a professional POS system.

Inventory management, electronic payment or tax functions are currently not implemented.

## Technology stack
- Backend
  - [Kotlin](https://kotlinlang.org/)
  - [Quarkus](https://quarkus.io/)
- Frontend
  - [Vue](https://vuejs.org/)
  - [PrimeVue](https://primevue.org/)
  - [TailwindCSS](https://tailwindcss.com/)

# Setup from Source

## Prerequisites

- [Java 21](https://openjdk.org/)

## Build
```shell script
./mvnw package
```
## Run Application

```shell script
java -jar target/quarkus-app/quarkus-run.jar
```
Open [MaksPOS UI](http://localhost:8080) ([http://localhost:8080](http://localhost:8080))

## Configuration

### Products (`<data>/products.json`)

JSON configurationfile with all products.

File format:
```json
[
  {
    "id": "1001",
    "name": "Coke", 
    "description": "0.33l, Bottle", 
    "price": 3.00 
  },
... 
]
```

### UI configuration (`<data>/config.properties`)

Localizable configuration of the Webapp include all labels/texts displayed in the UI.

### Quarkus application properties (`src/main/resources/application.properties`)
The Quarkus [application.properties](./src/main/resources/application.properties) contains the configuration of the Quarkus backend.

See: [Quarkus configuration guide](https://quarkus.io/guides/config)


# Development

## Running the application in dev mode for live coding

```shell script
./mvnw compile quarkus:dev
```

- [MaksPOS UI](http://localhost:8080)
- [Quarkus Dev UI](http://localhost:8080/q/dev/)
- [REST API (SwaggerUI)](http://localhost:8080/q/swagger-ui/)


## Release Build

```shell script
mvn external.atlassian.jgitflow:jgitflow-maven-plugin:1.0-m5.1:release-start -DautoVersionSubmodules=true -DupdateDependencies=true -DreleaseVersion=1.0.0 -DdevelopmentVersion=1.1.0-SNAPSHOT
# locale branch release/[releaseVersion] created
# make changes for release, e.g. set versions
# local test in branch release/[releaseVersion]
mvn external.atlassian.jgitflow:jgitflow-maven-plugin:1.0-m5.1:release-finish -DautoVersionSubmodules=true -DnoDeploy=true -DupdateDependencies=true -DreleaseVersion=1.0.0 -DdevelopmentVersion=1.1.0-SNAPSHOT
git push
git checkout main
git push
git push --tags
```

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/makspos-*-runner`

## Quarkus Guides

- [Kotlin](https://quarkus.io/guides/kotlin)
- [REST](https://quarkus.io/guides/rest)
- [OpenAPI/SwaggerUI](https://quarkus.io/guides/openapi-swaggerui)
- [Quinoa/Frontend](https://docs.quarkiverse.io/quarkus-quinoa/dev/index.html)

# Notes - TO BE DELETED / MIGRATED

## Publish as Open Source
### TODOs

- [ ] Finalize README
     - [ ] Add Screenshots
     - [ ] Merge READMEs
     - [ ] Add description
     - [ ] German README?
- [x] Remove private resources from project/POM
- [x] Remove renovate
- [ ] Adjust demo data
- [ ] Remove Jenkinsfile
- [ ] Add github actions
- [ ] Add github distribution management to project POM
- [ ] Add Backlog to github project
- [ ] Add Badges 
     - from https://shields.io/

### Infos
- [Open Source Guide](https://opensource.guide/starting-a-project/)
- [Linux Foundation](https://www.linuxfoundation.org/blog/hosting-open-source-projects-on-github-nine-things-you-need-to-know#:~:text=GitHub%20is%20an%20excellent%20platform,developers%20to%20adopt%20and%20contribute.)
- [Github Docs](https://docs.github.com/de)
- [Free Code Camp](https://www.freecodecamp.org/news/how-to-start-an-open-source-project-on-github-tips-from-building-my-trending-repo/)
- [Github: Your First OpenSource Project](https://github.com/Your-First-Open-Source-Project/start-here)
- [Github project: Open Source Handbook](https://github.com/shainakrumme/open-source-handbook)
- [Egghead Course](https://egghead.io/courses/how-to-contribute-to-an-open-source-project-on-github)

## READMEs
- [Quarkus README](./README_quarkus.md)
- [Vue README](./README_vue.md)

## Backlog
### Todos

- [ ] Change I18n for native build
- [ ] Security / Login
     - [Quarkus Security Getting Tutorial](https://quarkus.io/guides/security-getting-started-tutorial)
     - [Quarkus JWT Security Quickstart Project](https://github.com/quarkusio/quarkus-quickstarts/tree/main/security-jwt-quickstart)
     - [Quarkus Security Properties Guide](https://quarkus.io/guides/security-properties)

### Features

- [ ] Read-Only-View to show customer the actual state of the order in a separate browser
- [ ] Pre Order / Cart functionality
     1. Order View for Customer to create a Cart
     2. Create QR with direct link to the Cart
     3. Scan functionality for the cassier (Mobile out of box)
