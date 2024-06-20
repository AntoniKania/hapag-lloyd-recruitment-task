# Account Management CRUD Application
This is a simple CRUD (Create, Read, Update, Delete) application for account management. It is built using Spring Boot and Java 17, with PostgreSQL as the database. Docker and Docker Compose are used to manage the PostgreSQL instance.

## Requirements
Before you start, make sure you have the following installed on your local machine:

- Java 17
- Docker
- Docker Compose

## How to run
Clone and cd to the directory with the repo, then run follwing commands:

- starting postgresql in the docker container
```bash
docker-compose up -d
```
- building the application
```bash
./gradlew build
```
- running the application
```bash
./gradlew bootRun
```
- running tests
```bash
./gradlew test
```

## Screenshots
- Creating account
![Screenshot_20240621_002223](https://github.com/AntoniKania/hapag-lloyd-recruitment-task/assets/87483058/d3b2ec9d-0646-4cbf-88c1-47aa5fc718d2)
- Creating account with the same username
![Screenshot_20240621_002928](https://github.com/AntoniKania/hapag-lloyd-recruitment-task/assets/87483058/ccc5afc1-225c-4ce2-b8a7-543bd786aea9)
- Getting account's info
![Screenshot_20240621_002342](https://github.com/AntoniKania/hapag-lloyd-recruitment-task/assets/87483058/998140a4-d72c-4ce8-aa40-392fb11bc572)
- Updating account's info
![Screenshot_20240621_002414](https://github.com/AntoniKania/hapag-lloyd-recruitment-task/assets/87483058/5edbc660-3668-4796-9631-71dad33da50a)
- Deleting account
![Screenshot_20240621_002838](https://github.com/AntoniKania/hapag-lloyd-recruitment-task/assets/87483058/d925abc1-8fd8-41be-8393-33d78640252e)
- Getting deleted account
![Screenshot_20240621_002855](https://github.com/AntoniKania/hapag-lloyd-recruitment-task/assets/87483058/e9fc08d9-fb97-45ba-b8f0-2e9c09765640)
- Getting accounts with pagination
![Screenshot_20240621_002739](https://github.com/AntoniKania/hapag-lloyd-recruitment-task/assets/87483058/363ffd95-041b-4fd6-932b-16a8f7dc32ec)


