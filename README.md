# Forkify Backend

RESTful API backend for the Forkify application built with Spring Boot and Java.

## Description

Forkify is a mobile application, developed with Flutter, that allows users to easily log the restaurants they visit. It provides an intuitive interface to add new entries, detail the dining experience, and most importantly, generate insightful statistics based on the recorded visits. Whether you want to know which type of cuisine you eat most often or how many new restaurants you've discovered this month, this app is for you!

## Technologies Used

- **Java 21**
- **Spring Boot 3.4.1**
- **Spring Data JPA** - Data management
- **Spring Web** - REST API
- **Spring Security** and **Firebase** - Authentication and authorization
- **PostgreSQL** - Database

## Installation

1. Clone the repository
```bash
git clone https://github.com/your-username/forkify-backend.git
cd forkify-backend
```
2. Install dependencies
```bash
mvn clean install
```
3. Create your PostgreSQL database and update database informations in [application.properties](https://github.com/YBallanger/forkify-backend/blob/main/src/main/resources/application.properties)
4. Run the application
```bash
mvn spring-boot:run
```



