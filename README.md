# Proyecto PetClinic Integration Test

Implementación en Spring Boot 3.5.6 / Java 17 del clásico dominio PetClinic, adaptado para laboratorios de pruebas de integración. Expone endpoints REST de CRUD para las principales entidades (Pet, Owner, Vet, Specialty, Visit, PetType) y muestra cómo conectar servicios, repositorios, DTOs, mapeadores con MapStruct y scripts de base de datos, manteniendo una alta cobertura con MockMvc, Rest-Assured y JaCoCo.

---

## Contenido
1. [Arquitectura](#arquitectura)
2. [Stack Tecnológico](#stack-tecnológico)
3. [Base de Datos y Datos Semilla](#base-de-datos-y-datos-semilla)
4. [Perfiles y Configuración](#perfiles-y-configuración)
5. [Primeros Pasos](#primeros-pasos)
6. [Ejecución y Pruebas](#ejecución-y-pruebas)
7. [Endpoints REST](#endpoints-rest)
8. [Estructura del Proyecto](#estructura-del-proyecto)
9. [Notas de CI/CD](#notas-de-cicd)
10. [Solución de Problemas](#solución-de-problemas)

---

## Arquitectura

```
             +--------------------+
   REST API  |   Capa Controller  |  Pruebas de integración con MockMvc
   --------> +--------------------+
                 |        ^
                 v        |
           +--------------------+
           |    Capa Service    |  Lógica, validaciones, excepciones
           +--------------------+
                 |        ^
                 v        |
           +--------------------+
           |  Capa Repository   |  Spring Data JPA (H2 / MySQL)
           +--------------------+
                 |        ^
                 v        |
           +--------------------+
           |     Base de datos  |  schema.sql / data.sql
           +--------------------+
```

Características clave:
- DTO + mapper MapStruct por agregado (ej. `PetDTO`, `PetMapper`) para desacoplar los payloads REST de las entidades.
- Jerarquía de excepciones (`PetNotFoundException`, etc.) propagada por los servicios y traducida a códigos HTTP en los controladores.
- Pruebas divididas entre integración con MockMvc y unitarias con Mockito.
- Entidades protegidas contra problemas de serialización por lazy-loading mediante `@JsonIgnore`, `@JsonIgnoreProperties` y exclusiones de Lombok.

---

## Stack Tecnológico
| Capa | Tecnología | Notas |
|------|------------|-------|
| Lenguaje | Java 17 | Versión LTS |
| Framework | Spring Boot 3.5.6 | Web + Data JPA |
| Build | Maven Wrapper | scripts `mvnw` incluidos |
| BD (tests/dev) | H2 en memoria | provisionado vía `schema.sql` / `data.sql` |
| BD (prod) | MySQL | activar con `-Dspring.profiles.active=mysql` |
| Mapeo | MapStruct 1.5.5 | DTO ↔ entidad |
| Boilerplate | Lombok | builders/getters/setters |
| Testing | JUnit 5, Spring Boot Test, MockMvc, Rest-Assured | más Mockito |
| Cobertura | JaCoCo 0.8.13 | reporte en `prepare-package` |
| CI | Pipeline Jenkins (`Jenkinsfile`) | compile → test → package |

---

## Base de Datos y Datos Semilla

- `src/main/resources/schema.sql` crea todas las tablas (`vets`, `specialties`, `vet_specialties`, `types`, `owners`, `pets`, `visits`).
- `src/main/resources/data.sql` carga datos de ejemplo realistas (6 veterinarios, 8 tipos de mascota, 10 dueños, mascotas, visitas, etc.).
- Scripts equivalentes para MySQL se encuentran en `data/` (`schema-mysql.sql`, `data-mysql.sql`) para despliegues fuera de H2.
- Relaciones:
  - Owners 1:N Pets
  - Pets N:1 Types
  - Vets N:M Specialties (tabla intermedia `vet_specialties`)
  - Pets 1:N Visits

---

## Perfiles y Configuración

- El perfil por defecto en `application.yml` es `h2`, lo que levanta automáticamente la base de datos en memoria.
- Cambia a MySQL pasando `-Dspring.profiles.active=mysql` a Maven o al plugin de Spring Boot:
  ```bash
  mvn spring-boot:run -Dspring-boot.run.profiles=mysql
  ```
- Consola de base de datos: el perfil `h2` habilita la consola en `/h2`.
- JaCoCo está configurado vía el plugin Maven en `pom.xml`; no requiere pasos adicionales.

---

## Primeros Pasos

1. **Clonar / descargar** el repositorio y abrirlo en tu IDE favorito.
2. **Instalar requisitos**:
   - JDK 17+
   - Maven 3.9+ (o usar los scripts `mvnw`)
3. **Opcional**: configurar MySQL y actualizar `application-mysql.yml` si se planea usar una BD persistente.

---

## Ejecución y Pruebas

### Ejecutar la aplicación (perfil H2)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

### Ejecutar toda la batería de pruebas
```bash
mvn clean test -Dspring.profiles.active=h2
```

### Objetivos Maven frecuentes
| Objetivo | Comando | Propósito |
|----------|---------|-----------|
| Compile | `mvn clean compile` | compila fuentes y mappers de MapStruct |
| Unit tests | `mvn test -Dtest=*ServiceMockitoTest` | ejecuta solo suites Mockito |
| Integration tests | `mvn test -Dspring.profiles.active=h2` | escenario por defecto |
| Coverage report | `mvn clean verify` | genera reporte JaCoCo en `target/site/jacoco` |

Todas las pruebas de integración con MockMvc cargan `schema.sql` + `data.sql`, de modo que las aserciones coinciden con los datos semilla aunque otras pruebas agreguen registros (gracias a verificaciones sin dependencia de orden usando `jsonPath("$[*]...")`).

---

## Endpoints REST

| Entidad | Endpoints | Detalles |
|---------|-----------|----------|
| Pet | `GET /pets`, `GET /pets/{id}`, `POST /pets`, `PUT /pets/{id}`, `DELETE /pets/{id}` | CRUD basado en DTOs, valida owner/type |
| Owner | `/owners`, `/owners/{id}` | CRUD estándar con dirección y teléfono |
| Vet | `/vets`, `/vets/{id}` | Incluye relación con specialties |
| Specialty | `/specialties`, `/specialties/{id}` | CRUD básico, maneja asociaciones con vets |
| Visit | `/visits`, `/visits/{id}` | Requiere pet existente; campos costo, descripción, fecha |
| PetType | `/pettypes`, `/pettypes/{id}` | Datos de referencia para tipos de mascota |

> Otras entidades (Types, VetSpecialty, etc.) pueden seguir el mismo patrón. Revisa `schema.sql` para entender las relaciones antes de extender controladores/pruebas.

---

## Estructura del Proyecto
```
.
├── data/                          # Scripts de schema/data para MySQL
├── src
│   ├── main
│   │   ├── java/com/tecsup/petclinic
│   │   │   ├── dtos/               # Objetos DTO (ej. PetDTO)
│   │   │   ├── entities/           # Entidades JPA con protecciones de serialización
│   │   │   ├── exceptions/         # Excepciones personalizadas
│   │   │   ├── mapper/             # Mappers MapStruct
│   │   │   ├── repositories/       # Repositorios Spring Data JPA
│   │   │   ├── services/           # Interfaces + implementaciones
│   │   │   └── webs/               # Controladores REST
│   │   └── resources
│   │       ├── application.yml     # Configuración de perfiles (h2 por defecto)
│   │       ├── schema.sql          # Esquema H2
│   │       └── data.sql            # Datos semilla H2
│   └── test/java/com/tecsup/petclinic
│       ├── services/               # Pruebas unitarias/integración de servicios
│       └── webs/                   # Pruebas MockMvc de controladores
├── Jenkinsfile                     # Pipeline CI (compile → test → package)
└── pom.xml                         # Configuración Maven
```

---

## Notas de CI/CD

- Etapas del `Jenkinsfile`:
  1. **Checkout**
  2. **Compile** (`mvn compile`)
  3. **Test** (`mvn test`)
  4. **Package** (`mvn package -DskipTests`)
- Artefactos del pipeline: reporte JaCoCo y JAR de Spring Boot.
- Estrategia sugerida: ramas de característica por módulo (ej. `feature/vet-integration-tests`), merge vía PR tras pasar pruebas locales.

---

## Solución de Problemas

| Problema | Causa | Solución |
|----------|-------|----------|
| `No serializer found for class org.hibernate.proxy...` | Serialización de proxys lazy | Añadir `@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})` y `@JsonIgnore` en referencias inversas |
| `Expected ... but was ...` en pruebas | Cambios de orden por inserciones previas | Usar aserciones JSON sin dependencia de orden (ya aplicadas) |
| Esquema desincronizado | Migraciones no reflejadas en H2 | Actualizar `schema.sql` y `data.sql` (y versiones MySQL) |
| Error al conectar con MySQL | Perfil/configuración ausente | Ejecutar con `-Dspring.profiles.active=mysql` y verificar `application-mysql.yml` |

---

## Contribuciones

1. Crear una rama por módulo/feature.
2. Implementar controlador, servicio, repositorio, DTO, mapper y pruebas en conjunto.
3. Ejecutar `mvn clean test` (perfil H2) y asegurar estado verde.
4. Commit con mensajes claros, push y abrir PR con evidencias (logs/capturas) según lo solicitado.

¡Felices pruebas! 🐾

PRUEBA JENKINS