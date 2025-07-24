
# 🧩 Sistema de Gestión Empresarial - Arquitectura de Microservicios

Este repositorio contiene una solución basada en microservicios implementada con Spring Boot 3.4.3, diseñada para cubrir funcionalidades como autenticación, gestión de clientes, productos, inventario, ventas, contabilidad y frontend MVC.

---

## 🏗️ Arquitectura de Módulos

infra/
├── eureka-server # Servicio de descubrimiento
├── config-server # Configuración centralizada
├── gateway-service # API Gateway para enrutar peticiones

dominio/
├── cliente-service # Gestión de clientes
├── producto-service # Gestión de productos
├── stock-service # Gestión de inventario
├── venta-service # Gestión de ventas

servicios/
├── stock-core-service # Lógica de stock compartida
├── libro-contable-service # Lógica contable

seguridad/
└── auth-service # Autenticación (JWT via Keycloak)

mvc/
└── web-mvc # Aplicación Spring MVC + Thymeleaf (frontend)


---

## 🔐 Seguridad

- Autenticación vía **Keycloak (externo)** con validación de credenciales por LDAP.
- Spring Security configura `auth-service` para emitir JWT, que luego es validado por cada microservicio vía filtros.
- No se utiliza dependencia directa de Keycloak en los microservicios.

---

## 🧪 Requisitos

- Java 17+
- Maven 3.9+
- Docker y Docker Compose
- Keycloak externo (se puede añadir a `docker-compose` si se desea)
- Puertos disponibles: 8761 (Eureka), 8888 (Config), 8080 (QA), 80 (PROD)

---

## 🚀 Cómo levantar el entorno con Docker

```bash
docker-compose up --build

📥 Variables necesarias

    application.yml por entorno (qa/prod) en Config Server

    Variables de entorno definidas en docker-compose.yml o .env

    JWT token headers configurados en gateway-service y auth-service

🔧 Servicios Clave
Servicio	    Puerto	    Descripción
Eureka Server	8761	Registro de servicios
Config Server	8888	Configuración centralizada
API Gateway	    8080	Entrada para QA
Web MVC	        8080	Frontend MVC
Auth Service	8090	Emisión de JWT y autenticación
Keycloak (externo)	8081	Administración de usuarios

🧠 Feign Client + Eureka

Todos los servicios se comunican entre sí utilizando @FeignClient y resolviendo los nombres desde Eureka. Las configuraciones están centralizadas vía Config Server.

🛡️ Seguridad MVC

    Página de login personalizada en web-mvc

    Token JWT emitido por auth-service después de federación con Keycloak

    Token validado y propagado por Gateway a los microservicios backend

📂 Configuración de carpetas

    Archivos de configuración (*.yml) por entorno se encuentran en el repositorio de configuración y son cargados por el Config Server.

    Puedes tener archivos como cliente-service-qa.yml, cliente-service-prod.yml, etc.



---

## 🐳 `docker-compose.yml`

```yaml
version: '3.9'

services:

  eureka-server:
    image: openjdk:17
    container_name: eureka-server
    build:
      context: ./infra/eureka-server
    ports:
      - "8761:8761"
    environment:
      - SPRING_PROFILES_ACTIVE=default
    depends_on:
      - config-server

  config-server:
    image: openjdk:17
    container_name: config-server
    build:
      context: ./infra/config-server
    ports:
      - "8888:8888"
    environment:
      - SPRING_PROFILES_ACTIVE=default

  gateway-service:
    image: openjdk:17
    container_name: gateway-service
    build:
      context: ./infra/gateway-service
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=qa
    depends_on:
      - eureka-server
      - config-server

  auth-service:
    image: openjdk:17
    container_name: auth-service
    build:
      context: ./seguridad/auth-service
    ports:
      - "8090:8090"
    environment:
      - SPRING_PROFILES_ACTIVE=default
    depends_on:
      - eureka-server
      - config-server

  web-mvc:
    image: openjdk:17
    container_name: web-mvc
    build:
      context: ./mvc/web-mvc
    ports:
      - "8081:8081"
    environment:
      - SPRING_PROFILES_ACTIVE=qa
    depends_on:
      - gateway-service
      - auth-service

  cliente-service:
    image: openjdk:17
    container_name: cliente-service
    build:
      context: ./dominio/cliente-service
    environment:
      - SPRING_PROFILES_ACTIVE=qa
    depends_on:
      - eureka-server
      - config-server

  producto-service:
    image: openjdk:17
    container_name: producto-service
    build:
      context: ./dominio/producto-service
    environment:
      - SPRING_PROFILES_ACTIVE=qa
    depends_on:
      - eureka-server
      - config-server

  stock-service:
    image: openjdk:17
    container_name: stock-service
    build:
      context: ./dominio/stock-service
    environment:
      - SPRING_PROFILES_ACTIVE=qa
    depends_on:
      - eureka-server
      - config-server

  venta-service:
    image: openjdk:17
    container_name: venta-service
    build:
      context: ./dominio/venta-service
    environment:
      - SPRING_PROFILES_ACTIVE=qa
    depends_on:
      - eureka-server
      - config-server

  # Puedes incluir Keycloak aquí si no está en otro entorno
  # keycloak:
  #   image: quay.io/keycloak/keycloak:22.0.1
  #   container_name: keycloak
  #   command: start-dev
  #   ports:
  #     - "8082:8080"
  #   environment:
  #     - KEYCLOAK_ADMIN=admin
  #     - KEYCLOAK_ADMIN_PASSWORD=admin

