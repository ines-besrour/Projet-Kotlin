# Kotlin MVVM ToDo App 

This is a simple ToDo application built using the MVVM architecture pattern in Kotlin, with an external API for data storage.


## Table of Contents

- [Features](#features)
- [Architecture Overview](#architecture-overview)
- [Getting Started](#getting-started)
  - [Configuration](#configuration)
  - [Screenshots](#screenshots)


## Features

- User authentication.
- ToDo list management with add, delete, update functionalities.
- Multiple UI screens including Login, List of todos, details of each todo

## Architecture Overview

The application follows the MVVM (Model-View-ViewModel) architecture pattern:

- **Models (data):** Contains data classes representing the application entities.
- **Views (ui):** User interface components, including activities, fragments, and adapters.
- **ViewModels:** Houses the business logic, communicates with the data layer, and exposes data to the UI using LiveData.

## Getting Started

- **Models:** A class representing the data: Todo.kt
- **DTO:** Allow structuring data during authentication and task management operations: AuthentificationDTO, CreateTodoDTO, LoginDTO, UpdateDTO
- **ViewModels:** Responsible for business logic and data management for fragments, contain methods to retrieve the list of todos from the API, add a new todo, etc...

### screenshots


![Splash Screen](https://github.com/ines-besrour/Projet-Kotlin/assets/83375466/b4a1aa47-1a1e-4ef9-a660-a44557b8df45) ![sign in](https://github.com/ines-besrour/Projet-Kotlin/assets/83375466/06772753-eac8-4f23-8374-266d10ec8550)





![sign in]()


**Sign Up:**

![sign up](https://github.com/ines-besrour/Projet-Kotlin/assets/83375466/184a2428-0bc4-489c-ba1f-415299110afd)


**Home Screen:**

![home screen](https://github.com/ines-besrour/Projet-Kotlin/assets/83375466/af5c72b8-edb0-4583-b22e-8579291028f4)


**Home Screen - All todos:**

![home page-all todos](https://github.com/ines-besrour/Projet-Kotlin/assets/83375466/cf09dbd6-9cc3-44ab-9fac-0e384e591c9a)


**Create Todo:**

![create](https://github.com/ines-besrour/Projet-Kotlin/assets/83375466/5287220e-6c3d-43fc-a93d-d71bf9fa0113)


**Update Todo:**

![update todo](https://github.com/ines-besrour/Projet-Kotlin/assets/83375466/3574c13c-f7d5-40e4-8fad-4d936bb22784)


**Detail Todo**
**Logout:**




