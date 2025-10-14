[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center">Gestor de Inventario — Microservicio de Productos</h3>

  <p align="center">
    Microservicio responsable de la gestión de productos dentro del sistema de Inventario Empresarial.
    <br />
    <a href="https://github.com/VictorRoe/Inventory-Management-Product-Service"><strong>Explorar documentación »</strong></a>
    <br />
    <br />
    <a href="https://github.com/VictorRoe/Inventory-Management-Product-Service/issues/new?labels=bug&template=bug-report---.md">Reportar Bug</a>
    &middot;
    <a href="https://github.com/VictorRoe/Inventory-Management-Product-Service/issues/new?labels=enhancement&template=feature-request---.md">Solicitar Feature</a>
  </p>
</div>

---

## 📦 Acerca del Proyecto

El **Servicio de Productos** es un microservicio diseñado para gestionar toda la información relacionada con los productos del inventario, como sus categorías, marcas, existencias, unidades de medida y transacciones (ventas, compras, devoluciones).

Este servicio forma parte del **Sistema de Gestión de Inventarios**, una solución basada en **microservicios** construida con **Java 21, Spring Boot WebFlux y Angular**, desplegada en **AWS**.

### 🎯 Objetivos del Microservicio

- Gestionar los productos del inventario (CRUD).
- Administrar categorías, marcas y unidades de medida.
- Controlar existencias y movimientos de stock.
- Registrar ventas y compras de productos.
- Controlar devoluciones de productos.
- Emitir notificaciones o eventos hacia otros microservicios a través de **AWS SQS**.
- Monitorear rendimiento y errores con **New Relic**.
- Mantener un código limpio y seguro supervisado por **SonarQube**.

---

## ⚙️ Arquitectura General

Este proyecto sigue una arquitectura **basada en microservicios** bajo principios de **Clean Architecture** utilizando [Scaffold] y **Domain-Driven Design (DDD)**.

### 🧩 Stack de este Repositorio

- **Java 21**
- **Spring Boot 3 + WebFlux (reactivo)**
- **AWS SQS** (mensajería asíncrona)
- **PostgreSQL**
- **Docker** (contenedorización)
- **New Relic** (observabilidad)
- **SonarQube** (calidad del código)

---

## 🚀 Getting Started

### ✅ Prerrequisitos

Asegúrate de tener instaladas las siguientes herramientas:

- **Java 21**
- **Gradle 9.0.0(o la mas reciente)**
- **Docker o Podman**
- **SonarQube (plugin en tu IDE)**
- **Cuenta AWS (para SQS y despliegue opcional)**

### 🔧 Instalación y Ejecución Local

1. Clona el repositorio:

   ```bash
   git clone https://github.com/VictorRoe/Inventory-Management-Product-Service.git
   cd Inventory-Management-Product-Service
    ```

2. Descarga las dependencias que tiene este repositorio:

   ```bash
   gradle build
    ```
3. Implementa las variables de entorno

   ```bash
   [EN DESARROLLO]
    ```


4. Ejecuta el microservicio:

   ```bash
   gradle bootRun
    ```

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[forks-shield]: https://img.shields.io/github/forks/VictorRoe/Inventory-Management-Product-Service.svg?style=for-the-badge
[forks-url]: https://github.com/VictorRoe/Inventory-Management-Product-Service/network/members
[stars-shield]: https://img.shields.io/github/stars/VictorRoe/Inventory-Management-Product-Service.svg?style=for-the-badge
[stars-url]: https://github.com/VictorRoe/Inventory-Management-Product-Service/stargazers
[issues-shield]: https://img.shields.io/github/issues/VictorRoe/Inventory-Management-Product-Service.svg?style=for-the-badge
[issues-url]: https://github.com/VictorRoe/Inventory-Management-Product-Service/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/victorrangele
[Scaffold]: https://bancolombia.github.io/scaffold-clean-architecture/docs/intro