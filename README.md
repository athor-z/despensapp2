﻿# DespensApp 2.0
**Funcionalidades del Sistema**

**Gestión de Productos y Despensas**: Permite a los usuarios gestionar los productos en sus despensas, incluyendo la adición, eliminación y actualización de productos.

**Gestión de Recetas**: Los usuarios pueden crear, ver, actualizar y eliminar recetas. Las recetas incluyen detalles como ingredientes, descripción, dificultad y restricciones alimentarias.

**Control de Ingredientes**: La aplicación facilita el seguimiento de los ingredientes necesarios para las recetas, permitiendo a los usuarios verificar la disponibilidad de los ingredientes en su despensa.

**Autenticación y Autorización de Usuarios**: Los usuarios pueden registrarse, iniciar sesión y gestionar sus cuentas. El sistema utiliza Spring Security para manejar la autenticación y la autorización, asegurando que solo los usuarios autenticados puedan acceder a ciertas funcionalidades.

**Características de Seguridad**

**Spring Security**: Utilizado para la autenticación y autorización, proporcionando un mecanismo seguro para el manejo de sesiones de usuario y restricción de acceso basada en roles.

**Manejo de Contraseñas**: Las contraseñas de los usuarios son codificadas antes de almacenarse en la base de datos, aumentando la seguridad de los datos de usuario.

**Protección contra CSRF (Cross-Site Request Forgery)**: Spring Security ofrece protección contra ataques CSRF, asegurando que las solicitudes al servidor son legítimas y provienen del usuario correcto.

**Paquetes y Clases**

**Model**: Contiene clases de entidad como User, Recipe, Product, Pantry, y ProductPantry, cada una mapeada a una tabla correspondiente en la base de datos.

**Controller**: Define controladores para la interacción del usuario con la aplicación, incluyendo RecipeWebController, HomeWebController, y AuthController.

**Services**: Incluye servicios como RecipeService, ProductService, UserService, y ProductPantryService, proporcionando la lógica de negocio para operaciones CRUD y más.

**Repository**: Interfaces que extienden JpaRepository, permitiendo operaciones de base de datos para las entidades, como RecipeRepository, UserRepository, y ProductPantryRepository.

**DTO (Data Transfer Objects)**: Clases como ProductPantryDTO e IngredientProductDTO para transferir datos entre sub-sistemas de la aplicación.

**Security**: Configuraciones de seguridad, incluyendo SecurityConfig y CustomUserDetailsService para manejar la autenticación y autorización.

El sistema está estructurado siguiendo el patrón MVC (Modelo-Vista-Controlador), utilizando Spring Boot como framework principal para el desarrollo de aplicaciones web, Spring Security para la seguridad, y JPA (Java Persistence API) para la persistencia de datos.
