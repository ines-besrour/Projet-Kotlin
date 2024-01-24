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

Models: Une classe qui represente les données: Todo.kt
DTO: permettent de structurer les données lors des opérations d'authentification et de gestion des tâches: AuthentificationDTO , CreateTodoDTO, LoginDTO, UpdateDTO
ViewModels: responsables de la logique métier et de la gestion des données pour les fragments, contenient des méthodes pour récupérer la liste des todos depuis l'API, ajouter une nouvelle todo etc...

### screenshots
