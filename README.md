# Sistema de Personas

Trabajo Práctico 1 - Programación 3 - TUP UTN Extensión Áulica Necochea

Sistema de gestión de personas con autenticación y autorización.

## 🚀 Tecnologías

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap%205-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2-0000BB?style=for-the-badge&logo=h2&logoColor=white)

</div>

## ✨ Características

- 🔐 Login/Registro minimalista
- 📝 CRUD completo de personas
- 👥 Sistema de roles (ADMIN/USER)
- 🎨 Diseño oscuro y responsive
- 🔒 Autenticación y autorización segura

## 🛠️ Requisitos previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+ (opcional, puede usar H2)

## 🚀 Cómo ejecutar

```bash
# Clonar el repositorio
git clone https://github.com/Tomas2439/SistemaDePersonas.git

# Navegar al directorio
cd SistemaDePersonas

# Ejecutar con Maven
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## ⚙️ Configuración

### Base de datos MySQL

Edita el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_personas
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### Base de datos H2 (desarrollo)

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
```

## 📁 Estructura del proyecto

```
SistemaDePersonas/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/sistema/personas/
│       │       ├── config/
│       │       ├── controller/
│       │       ├── exception/
│       │       ├── models/
│       │       ├── repository/
│       │       ├── service/
│       │       └── utils/
│       └── resources/
│           ├── templates/
│           │   ├── private/
│           │   ├── public/
│           └── application.properties
├── pom.xml
└── README.md
```

## 📝 Licencia

Este proyecto es parte de un trabajo práctico académico.

---

<div align="center">
  <img src="https://img.shields.io/badge/Desarrollado%20por Sanchez%20Kevin y Stutz%20Tomas-gray?style=flat-square" alt="Desarrollado por">
  <br>
  <sub>TUP UTN E.A Necochea 2024</sub>
</div>
