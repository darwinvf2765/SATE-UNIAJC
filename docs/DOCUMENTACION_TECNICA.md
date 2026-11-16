
# Documentación técnica — SATE-UNIAJC

## 1. Descripción general

SATE-UNIAJC (Sistema de Alertas Tempranas y Acompañamiento Estudiantil) es una aplicación web desarrollada para apoyar el seguimiento académico de los estudiantes de la Institución Universitaria Antonio José Camacho.

El sistema permite gestionar información estudiantil, registrar asistencias, actualizar información académica, identificar condiciones de riesgo y administrar alertas y tutorías.

El código fuente se encuentra en:
https://github.com/darwinvf2765/SATE-UNIAJC

## 2. Tecnologías utilizadas

| Tecnología | Función |
|---|---|
| Java 25 | Lenguaje del backend |
| Spring Boot 3.4.5 | Framework principal |
| Spring Web | Desarrollo de API REST |
| Spring Data JPA | Persistencia |
| Spring Security | Control de acceso |
| JWT (JJWT 0.12.6) | Autenticación mediante tokens |
| Maven | Gestión de dependencias y compilación |
| H2 | Base de datos local en memoria |
| MySQL | Alternativa de base de datos |
| HTML, CSS y JavaScript | Interfaz web |
| JUnit y Mockito | Pruebas automatizadas |
| GitHub Actions | Integración continua |

## 3. Arquitectura del sistema

La aplicación utiliza una arquitectura por capas con separación de responsabilidades, compatible con los principios MVC.

### Modelo (Model)

Representa los datos del sistema mediante entidades como:

- Estudiante
- Usuario
- Rol
- Materia
- Asistencia
- Alerta
- Tutoria

Los repositorios gestionan la persistencia mediante Spring Data JPA.

### Vista (View)

Está desarrollada con HTML, CSS y JavaScript.

Archivos principales:

- `src/main/resources/static/index.html`
- `src/main/resources/static/styles.css`
- `src/main/resources/static/app.js`

Permite iniciar sesión, visualizar información académica, consultar alertas e interactuar con las funcionalidades disponibles según el rol.

### Controlador (Controller)

Recibe las peticiones HTTP, invoca los servicios correspondientes y devuelve respuestas al cliente.

Controladores principales:

- `AuthController`
- `EstudianteController`
- `AsistenciaController`
- `AlertaController`
- `TutoriaController`
- `DashboardController`

### Servicios (Service)

Contienen y coordinan la lógica de negocio.

Entre sus responsabilidades están autenticar usuarios, registrar asistencias, calcular porcentajes de inasistencia, evaluar condiciones académicas y generar alertas.

### Repositorios (Repository)

Gestionan las operaciones de acceso a datos mediante interfaces Spring Data JPA.

## 4. Estructura principal del proyecto

```text
SATE-UNIAJC/
├── .github/
│   └── workflows/
│       └── ci.yml
├── docs/
│   ├── acta_constitucion.md
│   ├── DOCUMENTACION_TECNICA.md
│   ├── PLAN_PRUEBAS.md
│   ├── RETROSPECTIVA_SPRINT.md
│   └── README_DOCUMENTOS.md
├── src/
│   ├── main/
│   │   ├── java/co/uniajc/sate/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-mysql.properties
│   │       ├── data.sql
│   │       ├── schema.sql
│   │       └── static/
│   └── test/
├── pom.xml
└── README.md
```

## 5. Funcionalidades desarrolladas

### HU-01 — Inicio de sesión

Permite autenticar usuarios utilizando credenciales y generar un token JWT para acceder a los recursos protegidos.

### HU-04 — Registro de asistencia

Permite registrar las asistencias de los estudiantes y actualizar su porcentaje de inasistencia.

Componentes principales:

- `AsistenciaController`
- `AsistenciaService`
- `AsistenciaRepository`

### HU-05 — Registro académico

Permite actualizar información académica relacionada con el rendimiento de los estudiantes.

La implementación y el alcance exacto del registro de calificaciones deben contrastarse con los controladores y servicios actuales.

### HU-06 — Alerta roja por inasistencia

**Regla de negocio:** cuando el porcentaje de inasistencia es superior al 15 %, el sistema genera una alerta ROJA.

Ejemplos:

- 16 %: genera alerta roja.
- 15 %: no genera alerta roja.
- 10 %: no genera alerta roja.

La evaluación se realiza mediante `EvaluadorInasistencia`, que implementa la interfaz `EvaluadorAlerta`.

### HU-07 — Alerta amarilla por bajo promedio

**Regla de negocio:** cuando el promedio académico es inferior a 3.0, el sistema genera una alerta AMARILLA.

Ejemplos:

- 2.8: genera alerta amarilla.
- 3.0: no genera alerta amarilla.
- 3.5: no genera alerta amarilla.

La evaluación se realiza mediante `EvaluadorPromedio`, que implementa la interfaz `EvaluadorAlerta`.

### Alertas independientes

Si un estudiante tiene simultáneamente una inasistencia superior al 15 % y un promedio inferior a 3.0, el sistema puede generar dos alertas independientes.

Cuando ambas condiciones se cumplen, el estado general del estudiante prioriza la alerta ROJA.

## 6. Diseño del motor de alertas

El motor utiliza una interfaz común:

`EvaluadorAlerta`

Sus implementaciones permiten evaluar condiciones diferentes:

- `EvaluadorInasistencia`: comprueba el porcentaje de inasistencia.
- `EvaluadorPromedio`: comprueba el promedio académico.

`AlertaService` coordina las evaluaciones y actualiza el estado de alerta del estudiante.

`NotificacionService` define la operación de notificación.

`NotificacionSistemaService` implementa el almacenamiento de las alertas y evita crear otra alerta pendiente del mismo tipo para el mismo estudiante.

### Principios de diseño

**SRP:** cada evaluador tiene una responsabilidad específica.

**OCP:** pueden agregarse nuevos evaluadores mediante la interfaz común, sin modificar las reglas existentes.

**DIP:** el servicio coordinador depende de abstracciones como `EvaluadorAlerta` y `NotificacionService`.

## 7. Endpoints de la API

Los siguientes endpoints se encuentran identificados en la documentación técnica previa. Su disponibilidad y comportamiento exactos deben contrastarse con el código actual.

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auth/login` | Iniciar sesión |
| POST | `/api/auth/forgot-password` | Solicitar recuperación de contraseña |
| POST | `/api/auth/reset-password` | Restablecer contraseña |
| GET | `/api/estudiantes` | Listar estudiantes |
| GET | `/api/estudiantes/{id}` | Consultar estudiante |
| POST | `/api/estudiantes` | Crear estudiante |
| PUT | `/api/estudiantes/{id}/registro` | Actualizar registro académico |
| POST | `/api/asistencias` | Registrar asistencia |
| GET | `/api/asistencias/estudiante/{id}` | Consultar asistencias |
| GET | `/api/alertas` | Consultar alertas |
| GET | `/api/alertas/pendientes` | Consultar alertas pendientes |
| POST | `/api/alertas/evaluar` | Ejecutar el motor de alertas |
| PUT | `/api/alertas/{id}/atendida` | Marcar alerta atendida |
| GET | `/api/tutorias` | Consultar tutorías |
| POST | `/api/tutorias` | Registrar tutoría |
| PUT | `/api/tutorias/{id}/estado` | Cambiar estado de tutoría |
| GET | `/api/dashboard` | Consultar indicadores |

La presencia de una ruta en esta tabla no garantiza por sí sola que su funcionalidad esté terminada o probada.

## 8. Seguridad

El sistema emplea Spring Security con autenticación JWT.

La configuración conocida establece:

- `/api/auth/**`: acceso público para autenticación.
- `/api/asistencias/**`: acceso restringido al rol DOCENTE.
- `/api/alertas/**`: acceso restringido al rol DIRECTOR.
- `/api/dashboard/**`: acceso restringido al rol DIRECTOR.
- Otras rutas: requieren autenticación, salvo los recursos públicos expresamente autorizados.

El filtro JWT procesa el encabezado `Authorization: Bearer <token>`.

Se recomienda validar por HTTP los accesos autorizados, denegados y los tokens expirados.

## 9. Persistencia de datos

### H2

La configuración de desarrollo utiliza una base de datos H2 en memoria.

Esto permite ejecutar el proyecto localmente sin instalar un servidor MySQL, pero los datos de la base en memoria no persisten después de detener la aplicación.

### MySQL

El repositorio incluye una configuración alternativa para MySQL.

Antes de utilizarla deben configurarse credenciales seguras, comprobar la conexión y verificar el perfil correspondiente.

No deben publicarse contraseñas reales en el repositorio.

## 10. Ejecución local

### Requisitos

- JDK 25.
- Maven.
- Git, si se desea trabajar con el repositorio remoto.

### Ejecutar pruebas

```bash
mvn clean test
```

### Iniciar la aplicación

```bash
mvn spring-boot:run
```

### Abrir la interfaz

```text
http://localhost:8080
```

## 11. Pruebas automatizadas

El 10 de noviembre de 2026 se ejecutó localmente:

```bash
mvn clean test
```

Resultados:

| Indicador | Resultado |
|---|---|
| Pruebas ejecutadas | 18 |
| Fallos | 0 |
| Errores | 0 |
| Pruebas omitidas | 0 |
| Estado | BUILD SUCCESS |

Las pruebas se distribuyeron en:

- `SateUniajcApplicationTests`: 16 pruebas.
- `NotificacionSistemaServiceTest`: 2 pruebas.

Esta evidencia demuestra que las pruebas automatizadas existentes finalizaron correctamente. No equivale a certificar todos los flujos funcionales y de seguridad del sistema.

## 12. Integración continua — GitHub Actions

El repositorio incorpora:

`.github/workflows/ci.yml`

El workflow utiliza Java 25 y ejecuta:

```bash
mvn --batch-mode clean test
```

Se activa mediante:

- Push a la rama `master`.
- Pull Request dirigido a `master`.
- Ejecución manual desde GitHub Actions.

La ejecución inicial del workflow fue revisada y finalizó correctamente.

Enlace:

https://github.com/darwinvf2765/SATE-UNIAJC/actions

## 13. Gestión ágil

Las historias de usuario y sus criterios de aceptación se administran mediante GitHub Issues.

El seguimiento se realiza en GitHub Projects:

https://github.com/users/darwinvf2765/projects/1

GitHub Projects es la fuente oficial para consultar el estado y las prioridades de las historias de usuario. Esta documentación técnica no sustituye al tablero Kanban.

## 14. Pendientes de verificación

- Verificar por HTTP todos los endpoints documentados.
- Validar los criterios de aceptación de HU-01 a HU-07.
- Comprobar el flujo de recuperación de contraseña.
- Revisar el alcance real del registro de calificaciones de HU-05.
- Verificar los permisos de acceso de cada rol.
- Generar un reporte de cobertura.
- Validar MySQL en un entorno de pruebas.
- Incorporar diagramas de arquitectura, clases y secuencia.
