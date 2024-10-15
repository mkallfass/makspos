# Publish as Open Source
## TODOs

- [x] Set version to 0.1.0-SNAPSHOT
- [ ] Finalize README
     - [x] Add Screenshots
     - [x] Merge READMEs
     - [x] Add description
     - [ ] German README?
- [x] Remove private resources from project/POM
- [x] Remove renovate
- [x] Adjust demo data
- [x] Remove Jenkinsfile
- [ ] Adjust application.properties
- [ ] Adjust docker files: Remove docker-registry.mkallfass.de
- [ ] Adjust package.json: de.mkallfass
- [ ] Add github actions
- [ ] Add github distribution management to project POM
- [ ] Add Backlog to github project
- [ ] Add Badges 
     - from https://shields.io/

## Infos
- [Open Source Guide](https://opensource.guide/starting-a-project/)
- [Linux Foundation](https://www.linuxfoundation.org/blog/hosting-open-source-projects-on-github-nine-things-you-need-to-know#:~:text=GitHub%20is%20an%20excellent%20platform,developers%20to%20adopt%20and%20contribute.)
- [Github Docs](https://docs.github.com/de)
- [Free Code Camp](https://www.freecodecamp.org/news/how-to-start-an-open-source-project-on-github-tips-from-building-my-trending-repo/)
- [Github: Your First OpenSource Project](https://github.com/Your-First-Open-Source-Project/start-here)
- [Github project: Open Source Handbook](https://github.com/shainakrumme/open-source-handbook)
- [Egghead Course](https://egghead.io/courses/how-to-contribute-to-an-open-source-project-on-github)

# READMEs
- [Quarkus README](./README_quarkus.md)
- [Vue README](./README_vue.md)

# Backlog
## Todos

- [ ] Change I18n for native build
- [ ] Security / Login
     - [Quarkus Security Getting Tutorial](https://quarkus.io/guides/security-getting-started-tutorial)
     - [Quarkus JWT Security Quickstart Project](https://github.com/quarkusio/quarkus-quickstarts/tree/main/security-jwt-quickstart)
     - [Quarkus Security Properties Guide](https://quarkus.io/guides/security-properties)

## Features

- [ ] Read-Only-View to show customer the actual state of the order in a separate browser
- [ ] Pre Order / Cart functionality
     1. Order View for Customer to create a Cart
     2. Create QR with direct link to the Cart
     3. Scan functionality for the cassier (Mobile out of box)