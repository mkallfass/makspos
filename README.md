# MaKsPOS

Simple Point of Sales system 

# Development

## Release Build

```shell script
mvn external.atlassian.jgitflow:jgitflow-maven-plugin:1.0-m5.1:release-start -DautoVersionSubmodules=true -DupdateDependencies=true -DreleaseVersion=1.0.0 -DdevelopmentVersion=1.1.0-SNAPSHOT
# locale branch release/[releaseVersion] created
# make changes for release, e.g. set versions
# local test in branch release/[releaseVersion]
mvn external.atlassian.jgitflow:jgitflow-maven-plugin:1.0-m5.1:release-finish -DautoVersionSubmodules=true -DnoDeploy=true -DupdateDependencies=true -DreleaseVersion=1.0.0 -DdevelopmentVersion=1.1.0-SNAPSHOT
git push
# new develop version is build on CI server
git checkout main
git push
# RELEASE version is build on CI server
git push --tags
```

# Notes - TO BE DELETED / MIGRATED

## Publish as Open Source
### TODOs

- [ ] Finalize README
     - [ ] Merge READMEs
     - [ ] Add description
     - [ ] German README?
- [ ] Remove private resources from project/POM
- [ ] Remove renovate
- [ ] Remove Jenkinsfile
- [ ] Add github actions
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
### [ ] Todos

- [ ] Change I18n for native build
- [ ] Security / Login
     - [Quarkus Security Getting Tutorial](https://quarkus.io/guides/security-getting-started-tutorial)
     - [Quarkus JWT Security Quickstart Project](https://github.com/quarkusio/quarkus-quickstarts/tree/main/security-jwt-quickstart)
     - [Quarkus Security Properties Guide](https://quarkus.io/guides/security-properties)

### [ ] Features

- [ ] Read-Only-View to show customer the actual state of the order in a separate browser
- [ ] Pre Order / Cart functionality
     1. Order View for Customer to create a Cart
     2. Create QR with direct link to the Cart
     3. Scan functionality for the cassier (Mobile out of box)
