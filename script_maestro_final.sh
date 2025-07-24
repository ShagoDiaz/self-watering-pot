#!/bin/bash

# ===========================
# VARIABLES GLOBALES
# ===========================
PROJECT_NAME="profe-perez"
BASE_PACKAGE="cl.profeperez"
BASE_DIR="$PROJECT_NAME"
#GIT_REPO="https://github.com/empresa/$PROJECT_NAME"
JAVA_VERSION="21"
SPRING_BOOT_VERSION="3.1.0"

# ===========================
# FUNCIONES GENERALES
# ===========================
crear_directorio() {
  mkdir -p "$1"
}

crear_archivo() {
  local ruta="$1"
  local contenido="$2"
  echo -e "$contenido" > "$ruta"
}

crear_estructura_basica() {
  local nombre_servicio="$1"
  local grupo="$2"
  local package="$BASE_PACKAGE.$grupo.$nombre_servicio"
  local path_servicio="$BASE_DIR/$grupo/$nombre_servicio"

  crear_directorio "$path_servicio/src/main/java/$(echo $package | tr '.' '/')"
  crear_directorio "$path_servicio/src/main/resources"
  crear_directorio "$path_servicio/src/test/java/$(echo $package | tr '.' '/')"

  # Clase principal
  crear_archivo "$path_servicio/src/main/java/$(echo $package | tr '.' '/')/Application.java" \
  "package $package;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}"

  # application.yml
  crear_archivo "$path_servicio/src/main/resources/application.yml" "spring:\n  application:\n    name: $nombre_servicio\n  datasource:\n    url: jdbc:h2:mem:${nombre_servicio//-/_}_db\n    driver-class-name: org.h2.Driver\n    username: sa\n    password: \n  h2:\n    console:\n      enabled: true\n  jpa:\n    hibernate:\n      ddl-auto: update\n    show-sql: true\n    properties:\n      hibernate.format_sql: true\nserver:\n  port: 0"

  # pom.xml
  crear_archivo "$path_servicio/pom.xml" "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"
         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"
         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>$PROJECT_NAME</groupId>
    <artifactId>$PROJECT_NAME-$grupo</artifactId>
    <version>1.0.0</version>
  </parent>
  <artifactId>$nombre_servicio</artifactId>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.1.0</version>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>
  </dependencies>
</project>"
}

crear_pom_grupo() {
  local grupo="$1"
  crear_directorio "$BASE_DIR/$grupo"
  crear_archivo "$BASE_DIR/$grupo/pom.xml" "<project xmlns=\"http://maven.apache.org/POM/4.0.0\">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>$PROJECT_NAME</groupId>
    <artifactId>$PROJECT_NAME</artifactId>
    <version>1.0.0</version>
  </parent>
  <modules>
  </modules>
  <artifactId>$PROJECT_NAME-$grupo</artifactId>
</project>"
}

crear_pom_raiz() {
  crear_archivo "$BASE_DIR/pom.xml" "<project xmlns=\"http://maven.apache.org/POM/4.0.0\">
  <modelVersion>4.0.0</modelVersion>
  <groupId>$PROJECT_NAME</groupId>
  <artifactId>$PROJECT_NAME</artifactId>
  <version>1.0.0</version>
  <packaging>pom</packaging>
  <modules>
    <module>arquitectura</module>
    <module>entidades</module>
    <module>servicios</module>
    <module>dominio</module>
    <module>seguridad</module>
  </modules>
</project>"
}

# ===========================
# EJECUCIÓN DEL SCRIPT
# ===========================
crear_directorio "$BASE_DIR"
crear_pom_raiz
crear_pom_grupo "arquitectura"
crear_pom_grupo "entidades"
crear_pom_grupo "servicios"
crear_pom_grupo "dominio"
crear_pom_grupo "seguridad"

crear_estructura_basica "config-service" "arquitectura"
crear_estructura_basica "eureka-service" "arquitectura"
crear_estructura_basica "gateway-service" "arquitectura"
crear_estructura_basica "producto-service" "entidades"
crear_estructura_basica "cliente-service" "entidades"
crear_estructura_basica "venta-service" "entidades"
crear_estructura_basica "stock-service" "entidades"
crear_estructura_basica "gestion-stock-service" "servicios"
crear_estructura_basica "libro-contable-service" "servicios"
crear_estructura_basica "frontend-mvc" "dominio"
crear_estructura_basica "auth-service" "seguridad"

echo "Estructura del proyecto $PROJECT_NAME creada correctamente en $BASE_DIR."
