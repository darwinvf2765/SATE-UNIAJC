
# Documentación del proyecto SATE-UNIAJC

## 1. Información general

**Proyecto:** SATE-UNIAJC — Sistema de Alertas Tempranas y Acompañamiento Estudiantil

**Institución:** Institución Universitaria Antonio José Camacho

**Asignatura:** Ingeniería de Software I

**Metodología:** Scrum con tablero Kanban en GitHub Projects.

## 2. Equipo Scrum

| Integrante | Rol Scrum |
|---|---|
| Darwin Alexander Valencia Flor | Product Owner |
| Juan David Rosero Guerrero | Scrum Master |
| Jeremy Andrey Agreda Rosero | Developer |

## 3. Gestión ágil del proyecto

El Product Backlog, las historias de usuario, los criterios de aceptación y las tareas se gestionan directamente mediante GitHub Issues y GitHub Projects.

Tablero oficial:

https://github.com/users/darwinvf2765/projects/1

Repositorio:

https://github.com/darwinvf2765/SATE-UNIAJC

Historias de usuario registradas:

- HU-01: Inicio de sesión.
- HU-02: Recuperación de contraseña.
- HU-03: Gestión de roles y permisos.
- HU-04: Registro de asistencia.
- HU-05: Registro de calificaciones.
- HU-06: Alerta roja por inasistencia superior al 15 %.
- HU-07: Alerta amarilla por promedio inferior a 3.0.

Los estados, responsables y criterios de aceptación vigentes deben consultarse en el tablero oficial y las Issues correspondientes.

## 4. Documentos del repositorio

- `acta_constitucion.md`: conformación del equipo Scrum y responsabilidades.
- `DOCUMENTACION_TECNICA.md`: arquitectura, componentes, servicios y endpoints.
- `PLAN_PRUEBAS.md`: planificación y evidencias de pruebas.
- `RETROSPECTIVA_SPRINT.md`: retrospectiva y oportunidades de mejora.
- `logo-sate.png`: identidad visual del proyecto.

Los archivos `PRODUCT_BACKLOG.md` y `KANBAN.md` se consideran documentos históricos o de referencia, no la fuente oficial de seguimiento.

## 5. Arquitectura del sistema

SATE-UNIAJC está desarrollado con:

- Java 25.
- Spring Boot 3.4.5.
- Maven.
- Spring Security y JWT.
- Spring Data JPA.
- H2 para ejecución local y pruebas.
- HTML, CSS y JavaScript.

El backend se organiza en controladores, servicios, modelos y repositorios.

## 6. Motor de alertas

El sistema contempla dos reglas principales:

**HU-06 — Alerta roja:** se genera cuando el porcentaje de inasistencia del estudiante supera el 15 %.

**HU-07 — Alerta amarilla:** se genera cuando el promedio académico es inferior a 3.0.

Las dos alertas pueden existir de manera independiente para un mismo estudiante.

## 7. Pruebas automatizadas

La ejecución local de:

    mvn clean test

reportó los siguientes resultados:

- Pruebas ejecutadas: 18.
- Fallos: 0.
- Errores: 0.
- Resultado: BUILD SUCCESS.

Esta ejecución comprueba las pruebas automatizadas existentes, pero no reemplaza la validación funcional de todos los criterios de aceptación.

## 8. Integración continua

El repositorio cuenta con GitHub Actions.

Archivo:

    .github/workflows/ci.yml

El workflow ejecuta las pruebas Maven utilizando Java 25 al realizar cambios en la rama `master` o abrir Pull Requests hacia ella.

GitHub Actions:

https://github.com/darwinvf2765/SATE-UNIAJC/actions

La primera ejecución verificada finalizó correctamente.

## 9. Documentación pendiente de revisión

- Actualizar la documentación técnica según el código actual.
- Revisar el plan de pruebas y sus evidencias.
- Completar los diagramas UML necesarios.
- Revisar la retrospectiva y los resultados del Sprint.
- Verificar la correspondencia entre Issues, código implementado y criterios de aceptación.

## 10. Control de documentación

Los documentos técnicos deben mantenerse actualizados mediante Git.

GitHub Projects es la fuente oficial para los estados del trabajo y las prioridades del equipo.
