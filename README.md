## This is spellsOfEnglish backend

## Prerequisite

JAVA >= 17

Maven >= 3.6

Docker

Docker-compose

## Build

` docker-compose build . `

## Run

Copy example.env to the dev.env and change it.

` cp example.env dev.env `

> :warning: dev.env contain all sensetive env variables like a database username and password. Please don't share it.
> This file excluded from repository via .gitignore and .dockerignore

` docker-compose up -d `

## Clear

`docker-compose down && docker system prune -a --volumes`

## Swagger
For use swagger write address  `http://localhost:8080/swagger-ui.html`
