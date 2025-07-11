<div align="center">
  <br />
    <a href="#" target="_blank">
      <img src="https://github.com/user-attachments/assets/c12e740e-d4cb-4d06-a11b-5fd30c009347" alt="Project Banner">
    </a>
  <br />
  <br />
  <div>
    <img src="https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
    <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=Spring-Boot&logoColor=white" alt="SpringBoot" />
    <img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white" alt="Apache Maven" />
    <img src="https://img.shields.io/badge/JUnit5-25A162.svg?style=for-the-badge&logo=JUnit5&logoColor=white" alt="JUnit 5" />
    <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
    <img src="https://img.shields.io/badge/NeoVim-%2357A143.svg?&style=for-the-badge&logo=neovim&logoColor=white" alt="Neovim" />
  </div>
<br/><br/>
 
  <h1 align="center">Bookiero</h1>

   <div align="center">
      A Book Management System
   </div>
</div>



## 📋 <a name="table">Sumary</a>

1. 🚀 [Introduction](#introduction)
2. ⚙️ [Tech Stack](#tech-stack)
3. 🔋 [Features](#features)
4. 💻 [Quick Start](#quick-start)
5. 💾 [Environment Variables](#envs)
6. 📅 [Releases](#versions)
7. 🤝 [Contributing](#contributing)
8. 👥 [Authors](#authors)



## <a name="introduction">🚀 Introduction</a>

&nbsp;Simply put, **Bookiero** enables a simple way of borrow books anytime, anywhere.


## <a name="features">🔋 Features</a>

- Login and Signup to use the application
- Register and Retrieve info about books and availability
- Handle book borrowing and devolution


## <a name="tech-stack">⚙️ Tech Stack</a>

- Java + Spring Boot 3, for handling requests
- PostgreSQL, for data persistence
- Spring Security, for handling authentication/authorization
- Maven, for build/dependency management
- Springdoc/Swagger, to document the api
- JUnit5, to ensure features well function with tests
- Docker, for better development and deployment


## <a name="quick-start">💻 Quick Start</a>

Follow these steps to set up the project locally on your machine.

**00 - Prerequisites**

To use this project you must have previously setup the following:

- [Git](#)
- [Java JDK 21 or newer](#)
- [Maven (Optional)](#)
- [Docker](#)
- [PoatgreSQL if not confident using docker](#)

**01 - Cloning the Repository**

```bash
git clone https://github.com/salomovs95/bookiero
cd bookiero
```

**02 - Installation**

Install/Update the project dependencies using maven:

```bash
mvn clean install
#or
./mvnw clean install
```

**03 - Running the Project**

```bash
mvn clean spring-boot:run
#or
./mvnw clean -Dmaven.test.skip spring-boot:run
```

Open [localhost:8080/swagger-ui/index.html](localhost:8080/swagger-ui/index.html) in your browser to view the project to test the api.
Please, check the port.

## <a name="envs">💾 Environment Variables</a>

<details>
<summary><code>Make sure these variables are available before start the application</code></summary>

```yaml
# Database Relatedd - Development Profile Only
DATABASE_URL=CHANGE_ME_LATER
DATABASE_USERNAME=CHANGE_ME_LATER
DATABASE_PASSWORD=CHANGE_ME_LATER

# CORS Related
ALLOWED_ORIGINS=CHANGE_ME_LATER

# User Related
ENCODER_SECRET=CHANGE_ME_LATER

# Jwt Related
JWT_SECRET=CHANGE_ME_LATER
JWT_ISSUER=CHANGE_ME_LATER

SPRING_PROFILE=CHANGE_LATER_IF_DEPLOY
```

</details>

## <a name="versions">📅 Release History</a>

* 0.0.1
    * CHANGE: Update README.md
    * ADD: Create entities, repositories, services and controllers
* 0.0.56
    * FEAT:
        - User registration
        - User authentication
        - Resources authorization
        - Jwt Issuing and validating
        - Books creation, listing, borrowing and returning
        - Api documentation with springdoc/swagger-ui
* 0.0.69:
    * CHORE:
        - First deployment
        - Tests passing
* 0.0.97:
    * UPDATE:
        - Code enhancement/refactoring and minor adjustments
* 0.1.11:
    * FEAT:
        - Can now retrieve most popular books/authors ranking
* 0.1.21:
    * FIX:
        - Password hashing taking too long
    * FEAT:
        - Book listing can now be paginated
        - Book listing can now be filtered
* 0.1.26:
    * ANALYTICS:
       - Admin can retrieve dashboard data, such as borrows/returns per period as well as other core data centralized.
* 0.1.40:
    * UPDATE:
       - Refactors and minor adjustments

## <a name="contributing">🤝 Contributing</a>

Contributions, issues, and feature requests are welcome!

1. Fork it (<https://github.com/salomovs95/bookiero>)
2. Create your feature branch (`git switch -c feature/fooBar`)
3. Add your changes to the stage (`git add CHANGEDFILES`)
4. Commit your changes (`git commit -m 'Add some fooBar'`)
5. Push to the branch (`git push origin feature/fooBar`)
6. Create a new Pull Request


## <a name="authors">👥 Authors</a>

<table style="border-collapse: collapse; table-layout: auto text-align: left;">

  <tbody>
    <tr>
      <td style="padding: 10px; border: 1px solid #ddd;">
        <img src="https://avatars.githubusercontent.com/u/170432574?v=4" width="60" style="border-radius: 50%; display: block; margin: 0 auto;">
      </td>
      <td style="padding: 10px; border: 1px solid #ddd;">Salomao Souza</td>
      <td style="padding: 10px; border: 1px solid #ddd;">
        <a href="linkedin.com/in/salomovs95" target="_blank">LinkedIn</a> |
        <a href="https://github.com/salomovs95" target="_blank">GitHub</a>
      </td>
    </tr>
  </tbody>
</table>
