# SpringBoot-AML

If everything is setup already:
- Open the project in an IDE (I've used IntelliJ) and run
- In Postman:
      - Send requests to **localhost:8080/api/v1/book**
      - Format is **json**

Set up or re-run docker container:
- If you've never created the container on your machine:
        - use statement: **docker run --name postgres-spring -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres:alpine**
- Prepare the port using command: **docker port postgres-spring**
- To list exited containers: **docker ps -a**
- Using the id of the desired container, you can start it: **docker start 5ac16c3148b8**
- Using the id of the desired container, yoi can execute the app: **docker exec -it 5ac16c314 bin/bash**
- To connect to Postgres: **psql -U postgres**

Setup and modify database within Postgres (with UUID support):
**- CREATE DATABASE <db-name>;
- \c <db-name>;
- CREATE EXTENSION "uuid-ossp";
- SELECT uuid_generate_v4();
- **(to add columns)** ALTER TABLE <table-name> <alteration> <column-name> <data-type>;
- **

Within Postgres (with db already setup):
- To list existing databases: **\l**
- View table: **\d <table-name>**
