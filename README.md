### Description
   Position management - Summary all the position by all the transactions

### Environment
   build: jdk1.8, maven3.6.0
   tools: IntelliJ IDEA or Eclipse
   database: h2database and postgresql

### Framework
   spring boot 2.0

### Installation
   install: mvn clean install -Dmaven.test.skip=true
   test: mvn clean test -Pdev
   package: mvn clean package -Pprod -Dmaven.test.skip=true

### Database
   for prod environment, install postgresql9.4 or latest the port default is 5432
   use the postgres user
   -- create role
   create role "position" login encrypted password 'position' inherit connection limit -1;
   -- create database
   create database position with owner = position encoding = 'UTF8';
   -- login by position, create schema
   create schema position authorization position;

   can use the init sql data-h2.sql to init prod environment database

### Junit
   mvn clean test -Pdev
   use the jacoco to generate the junit report
   visit 'http://localhost:8080/h2-console' to check the h2database
   visit 'file://{$projectFolder}/target/site/jacoco/index.html' to check the junit coverage
   Note: {$projectFolder} is the workspace full path
   When the project starts by dev, the database will be initialized by data-h2.sql automatically

### Swagger Api
   http://localhost:8080/swagger-ui.html