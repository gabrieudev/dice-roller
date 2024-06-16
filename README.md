# REST API for Dice Rolling

![Java](https://img.shields.io/badge/Java-21-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green) ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green) [![LinkedIn](https://img.shields.io/badge/Connect%20on-LinkedIn-blue)](https://www.linkedin.com/in/gabrieudev) ![GPL License](https://img.shields.io/badge/License-GPL-blue)

Welcome to my **REST API for Dice Rolling** project.

Please select your preferred language:

- [English](README.md)
- [Português (Brasil)](README.pt-br.md)

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Contributions](#contributions)
- [Contact](#contact)

## Introduction

This project was created to facilitate dice rolling in RPG sessions, whether in person or online. Additionally, the project implements authentication with JWTs and authorization via roles for users, utilizing the best and most up-to-date practices in the market to ensure the integrity of sensitive data.

## Features

- Dice rolling including advantage, disadvantage, modifier, and any number of sides as long as the number is positive.
- Access to roll history.
- Email confirmation for user registration.
- Paginated searches.
- Documentation with endpoints using Swagger.
- User login with JWT authentication.
- Role-based authorization for access control to different API endpoints.
- Password encryption following industry best practices.
- Integration with PostgreSQL database.

## Technologies

- ![Java](https://img.shields.io/badge/Java-21-orange): Programming language.
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green): Framework for building applications.
- ![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green): Security framework for Spring applications.
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue): Relational database.

## Getting Started

Follow these steps to run the project on your machine:

1. Clone the repository: `git clone https://github.com/gabrieudev/dice-roller.git`
2. Navigate to the project directory: `cd <path>`
3. Update the general settings in `application.properties`.
4. Build the project: `./mvnw clean install` (for Windows: `mvnw.cmd clean install`)
5. Run the application: `./mvnw spring-boot:run` (for Windows: `mvnw.cmd spring-boot:run`)

## Configuration

- Update the `application.properties` file with all the necessary information.

## Usage

1. When starting the project, an admin user is automatically inserted into the database in `AdminDataLoader.java`. Its information can be changed both there and in `application.properties`.
2. Use an admin user to access the protected endpoints.

## Endpoints

Roll:

- `BASIC Role` `POST /rolls`: Save a roll and receive the result.
- `BASIC Role` `GET /rolls/{id}`: Get a roll by ID.
- `ADMIN Role` `GET /rolls`: Get all rolls.
- `BASIC Role` `DELETE /rolls/{id}`: Delete a roll.
- `BASIC Role` `GET /rolls/history/{userId}`: Get a user's roll history.

User:

- `POST /users/register`: Register a user.
- `POST /login`: Log in and receive a JWT.
- `ADMIN Role` `GET /users`: Get all users.
- `BASIC Role` `PUT /users/update-password`: Update a user's password.
- `POST /users/check/{userId}/{verificationId}`: Verify if the verification code sent to the email is correct.

Access the complete documentation at the `/swagger-ui.html` endpoint.

## Contributions

Contributions are very welcome! If you would like to contribute, fork the repository and create a pull request.

## Contact

If you have any suggestions or questions, contact me on [LinkedIn](https://www.linkedin.com/in/gabrieudev).

---

**License:** This project is licensed under the terms of the [GNU General Public License (GPL)](LICENSE).