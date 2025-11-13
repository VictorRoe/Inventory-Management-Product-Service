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
    <a href="https://deepwiki.com/VictorRoe/Inventory-Management-Product-Service"><strong>Explorar documentación »</strong></a>
    <br />
    <br />
    <a href="https://github.com/VictorRoe/Inventory-Management-Product-Service/issues/new?labels=bug&template=bug-report---.md">Reportar Bug</a>
    &middot;
    <a href="https://github.com/VictorRoe/Inventory-Management-Product-Service/issues/new?labels=enhancement&template=feature-request---.md">Solicitar Feature</a>
  </p>
</div>

---

## 📦 Acerca del Proyecto

El **Servicio de Productos** es un microservicio diseñado para gestionar toda la información relacionada con los
productos del inventario, como sus categorías, proovedores y transacciones (ventas, compras, devoluciones).

Este servicio forma parte del **Sistema de Gestión de Inventarios**, una solución basada en **microservicios**
construida con **Java 21, Spring Boot WebFlux y Angular**, desplegada en **AWS**.

### 🎯 Objetivos del Microservicio

- Gestionar los productos del inventario (CRUD).
- Administrar categorías, marcas y proovedores.
- Controlar existencias y movimientos de stock.
- Registrar ventas y compras de productos.
- Controlar devoluciones de productos.
- Emitir eventos hacia otros microservicios a través de **AWS SQS**.
- Mantener un código limpio y seguro supervisado por **SonarQube**.

---

## ⚙️ Arquitectura General

Este proyecto sigue una arquitectura **basada en microservicios** bajo principios de **Clean Architecture**
utilizando [Scaffold] y **Domain-Driven Design (DDD)**.

### 🧩 Stack de este Repositorio

- **Java 21**
- **Spring Boot 3 + WebFlux (reactivo)**
- **AWS SQS** (mensajería asíncrona)
- **PostgreSQL**
- **Docker** (contenedorización)
- **Grafana + Prometheus** (observabilidad)
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
   - gradle build
    ```
3. Implementa las variables de entorno

   ```bash
   DB_HOST -> Nombre del host de Base de datos. (Si estas en local seria localhost)
   DB_PORT -> Puerto de la base de datos que conecta con tu microservicio
   DB_NAME -> Nombre de la base de datos
   DB_SCHEMA -> Nombre del schema (normalmente seria public)
   DB_USER -> Usuario con el que ingresas a tu base de datos
   DB_PASSWORD -> Password para ingresar a tu base de datos
   JWT_SECRET -> Token que soporte HS256
   SQS_REGION -> Region de AWS donde estes utilizando el producto SQS
   SQS_SENDER -> URL del producto creado
    ```


4. Crear la Imagen (Docker o Podman):

   ```bash
    # Tienes que estar ubicado en la raiz del proyecto
    podman build -t product-service -f ./deployment/Dockerfile .
   
    ```
5. Crear la Imagen Postgres
    
    ```bash
   - podman pull postgres:16
   
    # Si deseas correr ya la base de datos utiliza este comando:
   - podman run -d --name db-product-prod -e POSTGRES_USER={tu usuario} -e POSTGRES_PASSWORD={tu password} -e POSTGRES_DB=db-auth -v auth_volume:/var/lib/postgresql/data postgres:16  
   ```
6. Ejecuta el microservicio desde podman

    ```bash
   
    - podman run -d --name product-ms -e DB_HOST=db-product-prod -e DB_NAME=db-product -e DB_PASSWORD={tu password} -e DB_SCHEMA=public -e DB_USER={tu usuario} -e JWT_SECRET={token HS256} -e SQS_REGION=us-east-1 -e SQS_SENDER={url} product-service
   
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
