
# Plan de pruebas — SATE-UNIAJC

## 1. Objetivo

Establecer los casos, procedimientos y evidencias para verificar el funcionamiento de SATE-UNIAJC.

El plan contempla autenticación, control de acceso, registro académico, asistencia, generación de alertas, tutorías y dashboard.

Los criterios de aceptación oficiales se gestionan mediante las Issues del repositorio y el tablero GitHub Projects.

## 2. Entorno de pruebas

| Componente | Configuración |
|---|---|
| Lenguaje | Java 25 |
| Framework | Spring Boot 3.4.5 |
| Compilación | Maven |
| Pruebas | JUnit 5, Mockito y Spring Boot Test |
| Base de datos local | H2 en memoria |
| Base de datos alternativa | MySQL |
| Sistema operativo local | Windows 11 |
| Integración continua | GitHub Actions |
| Rama de referencia | master |

Comando principal:

    mvn clean test

## 3. Evidencia de ejecución automatizada

Fecha de ejecución local: 10 de noviembre de 2026.

Comando ejecutado:

    mvn clean test

Resultados obtenidos:

| Indicador | Resultado |
|---|---|
| Pruebas ejecutadas | 18 |
| Pruebas exitosas | 18 |
| Fallos | 0 |
| Errores | 0 |
| Omitidas | 0 |
| Resultado general | BUILD SUCCESS |

Distribución:

- SateUniajcApplicationTests: 16 pruebas exitosas.
- NotificacionSistemaServiceTest: 2 pruebas exitosas.

Estos resultados demuestran que la suite automatizada existente pasó, pero no certifican por sí solos todos los flujos funcionales.

## 4. Matriz de pruebas por historia de usuario

La siguiente matriz contiene escenarios de aceptación y verificación. El estado "Pendiente" significa que no se ha registrado evidencia suficiente de una prueba específica, aunque existan pruebas unitarias relacionadas.

| ID | HU | Escenario | Resultado esperado | Evidencia |
|---|---|---|---|---|
| CP-01 | HU-01 | Inicio de sesión con credenciales válidas | Acceso autorizado y entrega de JWT | Pendiente de evidencia HTTP |
| CP-02 | HU-01 | Inicio de sesión con credenciales incorrectas | Acceso rechazado | Pendiente de evidencia HTTP |
| CP-03 | HU-02 | Recuperación con token válido | Permite restablecer contraseña | Pendiente |
| CP-04 | HU-02 | Token vencido o inválido | Rechaza recuperación | Pendiente |
| CP-05 | HU-03 | Acceso con rol autorizado | Permite la operación | Pendiente de evidencia HTTP |
| CP-06 | HU-03 | Acceso con rol no autorizado | Deniega la operación | Pendiente de evidencia HTTP |
| CP-07 | HU-04 | Registrar asistencia | Guarda registro correctamente | Suite Maven exitosa; verificar caso individual |
| CP-08 | HU-04 | Actualizar porcentaje de inasistencia | Calcula el porcentaje esperado | Suite Maven exitosa; verificar caso individual |
| CP-09 | HU-05 | Actualizar información académica | Guarda valores válidos | Pendiente de evidencia funcional |
| CP-10 | HU-05 | Ingresar calificación fuera de rango | Rechaza valores inválidos | Pendiente de evidencia funcional |
| CP-11 | HU-06 | Inasistencia de 16 % | Genera alerta ROJA | Suite Maven exitosa; verificar caso individual |
| CP-12 | HU-06 | Inasistencia de 15 % | No genera alerta roja | Suite Maven exitosa; verificar caso individual |
| CP-13 | HU-06 | Inasistencia de 10 % | No genera alerta roja | Suite Maven exitosa; verificar caso individual |
| CP-14 | HU-07 | Promedio de 2.8 | Genera alerta AMARILLA | Suite Maven exitosa; verificar caso individual |
| CP-15 | HU-07 | Promedio de 3.0 | No genera alerta amarilla | Suite Maven exitosa; verificar caso individual |
| CP-16 | HU-07 | Promedio de 3.5 | No genera alerta amarilla | Suite Maven exitosa; verificar caso individual |
| CP-17 | HU-06/07 | Inasistencia 20 % y promedio 2.8 | Genera dos alertas independientes | Suite Maven exitosa; verificar caso individual |
| CP-18 | HU-06/07 | Reevaluación con alerta pendiente | No duplica alerta pendiente del mismo tipo | Suite Maven exitosa; verificar caso individual |
| CP-19 | HU-06/07 | Consultar alertas como director | Muestra tipo, estudiante y motivo | Pendiente de evidencia HTTP |
| CP-20 | HU-06/07 | Marcar alerta como atendida | Actualiza el estado de la alerta | Pendiente de evidencia funcional |

## 5. Casos adicionales de integración

| ID | Módulo | Escenario | Resultado esperado | Estado |
|---|---|---|---|---|
| TI-01 | Seguridad | Solicitud protegida sin JWT | Acceso denegado | Pendiente |
| TI-02 | Seguridad | JWT inválido o expirado | Acceso denegado | Pendiente |
| TI-03 | Seguridad | Docente intenta consultar dashboard de director | Acceso denegado | Pendiente |
| TI-04 | Estudiantes | Crear estudiante sin datos obligatorios | Validación controlada | Pendiente |
| TI-05 | Tutorías | Registrar tutoría válida | Registro persistido | Pendiente |
| TI-06 | Tutorías | Actualizar estado de tutoría | Cambio persistido | Pendiente |
| TI-07 | Dashboard | Consultar indicadores como director | Respuesta válida | Pendiente |
| TI-08 | Persistencia | Reiniciar aplicación con MySQL | Los datos persisten | Pendiente |
| TI-09 | Interfaz | Login y navegación por rol | Interfaz muestra opciones autorizadas | Pendiente |
| TI-10 | Interfaz | Ejecutar evaluación desde la pantalla | Alertas actualizadas | Pendiente |

## 6. Pruebas BDD para HU-06

### Escenario: Alerta roja por inasistencia

**Given:** Dado que un estudiante presenta una inasistencia del 16 %.

**When:** Cuando el sistema ejecuta el motor de alertas.

**Then:** Entonces se genera una alerta ROJA indicando que supera el 15 % de inasistencias.

### Escenario: Límite exacto

**Given:** Dado que el estudiante presenta exactamente un 15 % de inasistencias.

**When:** Cuando se evalúa su información académica.

**Then:** Entonces no se genera una alerta roja por esta regla.

## 7. Pruebas BDD para HU-07

### Escenario: Alerta amarilla por promedio bajo

**Given:** Dado que un estudiante tiene un promedio de 2.8.

**When:** Cuando el sistema ejecuta el motor de alertas.

**Then:** Entonces se genera una alerta AMARILLA con el motivo "Promedio inferior a 3.0".

### Escenario: Promedio igual al límite

**Given:** Dado que el estudiante tiene un promedio de 3.0.

**When:** Cuando el sistema evalúa su promedio.

**Then:** Entonces no se genera una alerta amarilla por esta regla.

### Escenario: Dos condiciones de riesgo

**Given:** Dado que un estudiante tiene 20 % de inasistencia y un promedio de 2.8.

**When:** Cuando se ejecutan las reglas académicas.

**Then:** Entonces se generan dos alertas independientes, ROJA y AMARILLA.

## 8. Procedimiento de ejecución

1. Abrir una terminal en la raíz del repositorio.
2. Comprobar la versión de Java con `java -version`.
3. Comprobar la versión utilizada por Maven con `mvn -version`.
4. Ejecutar `mvn clean test`.
5. Confirmar que aparezca `BUILD SUCCESS`.
6. Consultar los resultados en `target/surefire-reports/`.
7. Ejecutar las pruebas HTTP y funcionales correspondientes.
8. Registrar evidencias de cada caso.
9. Documentar defectos como GitHub Issues.

## 9. Integración continua

Archivo del workflow:

    .github/workflows/ci.yml

GitHub Actions ejecuta Maven con Java 25 para los cambios enviados a `master` y los Pull Requests dirigidos a esa rama.

Workflow:

https://github.com/darwinvf2765/SATE-UNIAJC/actions

La ejecución inicial revisada finalizó correctamente.

## 10. Registro de evidencias

Para cada prueba funcional se recomienda registrar:

| Campo | Información |
|---|---|
| ID del caso | |
| Historia de usuario | |
| Responsable | |
| Fecha | |
| Commit evaluado | |
| Datos de entrada | |
| Resultado esperado | |
| Resultado obtenido | |
| Estado | Aprobado / Fallido / Bloqueado |
| Evidencia | Captura, salida de prueba o enlace |
| Incidencia relacionada | Issue de GitHub, si corresponde |

## 11. Criterios de salida

Para considerar una funcionalidad terminada deben verificarse, según corresponda:

- Código integrado en el repositorio.
- Compilación exitosa.
- Pruebas automatizadas superadas.
- Criterios de aceptación BDD validados.
- Pruebas funcionales críticas ejecutadas.
- Ausencia de defectos críticos abiertos.
- Evidencia asociada a la historia de usuario.
- Validación del Product Owner.

## 12. Limitaciones y trabajo pendiente

- La ejecución de las 18 pruebas no demuestra cobertura total.
- Falta obtener una medición formal de cobertura.
- Deben verificarse los permisos por rol mediante peticiones HTTP.
- Debe comprobarse el alcance funcional de HU-05.
- Debe verificarse el proceso completo de recuperación de contraseña HU-02.
- Falta validar la persistencia con MySQL.
- Los resultados funcionales deben asociarse a sus Issues correspondientes.

## 13. Gestión del seguimiento

Las historias, tareas y criterios de aceptación se administran en:

https://github.com/users/darwinvf2765/projects/1

Este documento registra la estrategia y las evidencias de pruebas. No reemplaza el tablero Kanban.
