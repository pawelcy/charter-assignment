# charter-assignment

## Getting started

### Goal

	Solution of coding tast

### System requirments
    1. Java 17
    2. PostgreSql
	4. Git
	
### How to download repository

git clone https://github.com/pawelcy/charter-assignment
	
### Properties and environment to set 
	Environment variable:
	JAVA_HOME - patht to java

	In application.yaml set follow property:
    1. spring.datasource.password - Login password of the database.
    2. spring.datasource.url - JDBC URL of the database.
    3. spring.datasource.username - Login username of the database.
	
### Build

	In source folder run command
	mvnw install
	
### Run

	java -jar target\charter-assignment-1.0.0
	
### Swagger documentation

	Link to swagger ui: http://localhost:9999/swagger-ui/index.html
	Link to swagger api docs:  http://localhost:9999/v3/api-docs
