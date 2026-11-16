
# SATE-UNIAJC

**Sistema de Alertas Tempranas y Acompañamiento Estudiantil**

**Institución:** Institución Universitaria Antonio José Camacho  
**Asignatura:** Ingeniería de Software I  
**Período académico:** 2026-2  
**Metodología:** Scrumban — Scrum y Kanban

---

## Equipo Scrum

| Integrante | Usuario GitHub | Rol |
|---|---|---|
| Darwin Alexander Valencia Flor | @darwinvf2765 | Product Owner (PO) |
| Juan David Rosero Guerrero | @JuanRGuerrero | Scrum Master (SM) |
| Jeremy Andrey Agreda Rosero | @jere1927 | Developer |

## Logo del proyecto

![Logo SATE-UNIAJC](./docs/logo-sate.png)

---

## Descripción del proyecto

**SATE-UNIAJC (Sistema de Alertas Tempranas y Acompañamiento Estudiantil)** es una aplicación web orientada al seguimiento y acompañamiento de los estudiantes de la Institución Universitaria Antonio José Camacho.

El sistema permite gestionar información académica, registrar asistencias e identificar condiciones de riesgo mediante alertas tempranas.

Su finalidad es facilitar la identificación de situaciones que puedan afectar el desempeño y la permanencia estudiantil, contribuyendo a brindar un acompañamiento oportuno.

## Visión del producto

Proporcionar una herramienta tecnológica que permita identificar oportunamente factores de riesgo académico y facilitar el acompañamiento de los estudiantes.

El producto busca centralizar información que apoye la toma de decisiones y fortalezca las estrategias de permanencia estudiantil.

## Objetivo general

Desarrollar un sistema web que permita gestionar información estudiantil, detectar posibles situaciones de riesgo académico y apoyar el seguimiento y acompañamiento de los estudiantes.

---

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 25 | Lenguaje del backend |
| Spring Boot 3.4.5 | Framework principal |
| Spring Web | API REST |
| Spring Security | Seguridad y control de acceso |
| JWT | Autenticación mediante tokens |
| Spring Data JPA | Persistencia |
| H2 | Base de datos local en memoria |
| MySQL | Base de datos alternativa |
| Maven | Compilación y dependencias |
| JUnit y Mockito | Pruebas automatizadas |
| HTML, CSS y JavaScript | Interfaz web |
| Git y GitHub | Control de versiones |
| GitHub Projects | Gestión ágil del proyecto |
| GitHub Actions | Integración y entrega continua |
| Mermaid | Documentación de diagramas UML |

---

## Arquitectura del sistema

SATE-UNIAJC utiliza una arquitectura por capas, con separación de responsabilidades entre:

- **Modelo:** representa las entidades y los datos.
- **Vista:** presenta la interfaz web e interactúa con el usuario.
- **Controlador:** recibe las solicitudes HTTP.
- **Servicio:** coordina las reglas del negocio.
- **Repositorio:** permite consultar y guardar información mediante JPA.

La arquitectura contempla las entidades Estudiante, Usuario, Rol, Materia, Asistencia, Alerta y Tutoria.

[Consultar documentación técnica](./docs/DOCUMENTACION_TECNICA.md)

[Consultar diagrama de arquitectura MVC](./docs/diagramas/ARQUITECTURA_MVC.md)

---

## Historias de usuario

El seguimiento oficial de las historias de usuario se realiza mediante GitHub Issues y GitHub Projects.

| Historia | Funcionalidad |
|---|---|
| HU-01 | Inicio de sesión |
| HU-02 | Recuperación de contraseña |
| HU-03 | Gestión de roles y permisos |
| HU-04 | Registro de asistencia |
| HU-05 | Registro académico y actualización de promedio |
| HU-06 | Alerta roja por inasistencia |
| HU-07 | Alerta amarilla por promedio bajo |

La existencia de una historia en el tablero no implica por sí sola que todos sus criterios de aceptación estén validados.

### Tablero Kanban

[**Abrir tablero SATE-UNIAJC en GitHub Projects**](https://github.com/users/darwinvf2765/projects/1)

[Consultar Issues del repositorio](https://github.com/darwinvf2765/SATE-UNIAJC/issues)

Los estados, responsables, tareas y criterios de aceptación deben consultarse directamente en las Issues y en el tablero.

---

## Motor de alertas académicas

El sistema incorpora un motor de evaluación para detectar condiciones de riesgo.

### HU-06 — Alerta roja

Se genera una alerta **ROJA** cuando la inasistencia supera el 15 %.

Ejemplos:

- 16 %: genera alerta roja.
- 15 %: no genera alerta roja.
- 10 %: no genera alerta roja.

### HU-07 — Alerta amarilla

Se genera una alerta **AMARILLA** cuando el promedio académico es inferior a 3.0.

Ejemplos:

- 2.8: genera alerta amarilla.
- 3.0: no genera alerta amarilla.
- 3.5: no genera alerta amarilla.

Si un estudiante cumple las dos condiciones, el motor puede registrar una alerta roja y una amarilla independientes.

El servicio de notificaciones evita generar otra alerta pendiente del mismo tipo para el mismo estudiante.

---

## Documentación del proyecto

La documentación técnica, Scrum y de calidad se encuentra en la carpeta [`docs`](./docs/).

### Documentos generales

| Documento | Descripción |
|---|---|
| [Acta de constitución](./docs/acta_constitucion.md) | Integrantes y roles Scrum |
| [Índice de documentos](./docs/README_DOCUMENTOS.md) | Organización documental |
| [Documentación técnica](./docs/DOCUMENTACION_TECNICA.md) | Arquitectura y funcionamiento |
| [Plan de pruebas](./docs/PLAN_PRUEBAS.md) | Casos y evidencias de pruebas |
| [Retrospectiva](./docs/RETROSPECTIVA_SPRINT.md) | Resultados y propuestas de mejora |
| [Revisión YAGNI, KISS y DRY](./docs/REVISION_YAGNI_KISS_DRY.md) | Análisis de principios de diseño ágil |

### Diagramas UML

| Diagrama | Enlace |
|---|---|
| Arquitectura MVC | [Visualizar](./docs/diagramas/ARQUITECTURA_MVC.md) |
| Clases HU-04 — Asistencia | [Visualizar](./docs/diagramas/CLASES_HU04.md) |
| Clases HU-05 — Registro académico | [Visualizar](./docs/diagramas/CLASES_HU05.md) |
| Secuencia del motor de alertas | [Visualizar](./docs/secuencia.md) |
| Despliegue del sistema | [Visualizar](./docs/despliegue.md) |

Los diagramas utilizan Mermaid y pueden visualizarse directamente desde GitHub.

---

## Entregables de la Sesión 12

La Sesión 12 de Ingeniería de Software I contempla UML dinámico, entrega continua y principios de diseño ágil.

| Entregable | Archivo |
|---|---|
| Diagrama de secuencia | [`docs/secuencia.md`](./docs/secuencia.md) |
| Diagrama de despliegue | [`docs/despliegue.md`](./docs/despliegue.md) |
| Integración continua | [`.github/workflows/ci.yml`](./.github/workflows/ci.yml) |
| Entrega continua | [`.github/workflows/cd.yml`](./.github/workflows/cd.yml) |
| Revisión YAGNI, KISS y DRY | [`docs/REVISION_YAGNI_KISS_DRY.md`](./docs/REVISION_YAGNI_KISS_DRY.md) |

El diagrama de despliegue diferencia el entorno local implementado de la infraestructura de staging propuesta.

El workflow CD empaqueta la aplicación y publica el archivo JAR como artefacto. El despliegue en un servidor externo permanece pendiente de configuración.

---

## Integración continua y entrega continua

El repositorio utiliza GitHub Actions para automatizar tareas de verificación y empaquetado.

### CI — Integración continua

Archivo: `.github/workflows/ci.yml`

- Configura Java 25.
- Ejecuta Maven.
- Compila el proyecto.
- Ejecuta las pruebas automatizadas.

### CD — Entrega continua

Archivo: `.github/workflows/cd.yml`

- Ejecuta las pruebas automatizadas.
- Empaqueta la aplicación Spring Boot.
- Verifica la generación del archivo JAR.
- Publica el artefacto en GitHub Actions.

[**Consultar las ejecuciones de GitHub Actions**](https://github.com/darwinvf2765/SATE-UNIAJC/actions)

---

## Ejecución local

### Requisitos

- JDK 25.
- Maven 3.9 o superior.
- Git para clonar el repositorio.

La configuración predeterminada utiliza H2 en memoria, por lo que no es necesario instalar MySQL para la ejecución local básica.

### 1. Clonar el repositorio

```bash
git clone https://github.com/darwinvf2765/SATE-UNIAJC.git
```

### 2. Entrar al proyecto

```bash
cd SATE-UNIAJC
```

### 3. Comprobar Java y Maven

```bash
java -version
mvn -version
```

Ambos deben utilizar un entorno compatible con Java 25.

### 4. Ejecutar las pruebas

```bash
mvn clean test
```

### 5. Iniciar la aplicación

```bash
mvn spring-boot:run
```

### 6. Abrir la interfaz

Desde el navegador:

http://localhost:8080

---

## Resultados de pruebas

En la verificación local del 16 de noviembre de 2026 se obtuvo:

| Indicador | Resultado |
|---|---|
| Total de pruebas | 18 |
| Fallos | 0 |
| Errores | 0 |
| Omitidas | 0 |
| Resultado | BUILD SUCCESS |

Las 18 pruebas se distribuyen en:

- `SateUniajcApplicationTests`: 16.
- `NotificacionSistemaServiceTest`: 2.

El resultado confirma que la suite existente pasa correctamente, pero no sustituye las pruebas funcionales ni la validación completa de los criterios de aceptación.

[Consultar el plan de pruebas](./docs/PLAN_PRUEBAS.md)

---

## Estado del proyecto

El repositorio incluye funcionalidades de autenticación, registro académico, asistencia, alertas, tutorías y dashboard.

Se encuentran configuradas las automatizaciones CI y CD y están publicados los diagramas solicitados en la Sesión 12.

El proyecto continúa sujeto a la validación de criterios de aceptación, revisión de seguridad, pruebas de integración y configuración futura de un entorno de staging real.

---

## Control de versiones

El equipo utiliza Git y GitHub para mantener el código fuente y la documentación.

La rama principal del repositorio se llama `master`.

Se recomienda ejecutar las pruebas automatizadas y revisar los resultados de GitHub Actions antes de integrar nuevas modificaciones.

**Repositorio oficial:**

https://github.com/darwinvf2765/SATE-UNIAJC
