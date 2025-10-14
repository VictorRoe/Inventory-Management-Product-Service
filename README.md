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

Este proyecto sigue una arquitectura **basada en microservicios** bajo principios de **Clean Architecture** y **Domain-Driven Design (DDD)**.

### 🧩 Stack Tecnológico

- **Java 21**
- **Spring Boot 3 + WebFlux (reactivo)**
- **AWS SQS** (mensajería asíncrona)
- **PostgreSQL**
- **Docker** (contenedorización)
- **New Relic** (observabilidad)
- **SonarQube** (calidad del código)
- **Angular** (front-end del ecosistema)

---

## 🚀 Getting Started

### ✅ Prerrequisitos

Asegúrate de tener instaladas las siguientes herramientas:

- **Java 21**
- **Maven 3.9+**
- **Docker**
- **Node.js 18+**
- **SonarQube (plugin en tu IDE)**
- **Cuenta AWS (para SQS y despliegue opcional)**

### 🔧 Instalación y Ejecución Local

1. Clona el repositorio:

   ```bash
   git clone https://github.com/VictorRoe/Inventory-Management-Product-Service.git
   cd Inventory-Management-Product-Service


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/VictorRoe/Inventory-Management-Product-Service/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/VictorRoe/Inventory-Management-Product-Service/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/VictorRoe/Inventory-Management-Product-Service/issues
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/victorrangele
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
