# Documentación técnica — SATE-UNIAJC

## 1. Descripción

SATE-UNIAJC es una aplicación web orientada al seguimiento de estudiantes, registro de asistencias, identificación de alertas tempranas y coordinación de tutorías. El repositorio contiene un backend Java/Spring Boot, persistencia mediante JPA, configuración H2/MySQL y una interfaz web estática.

## 2. Tecnologías identificadas

- Java y Maven.
- Spring Boot 3.4.5.
- Spring Web, Spring Data JPA, Spring Validation y Spring Security.
- JWT mediante JJWT 0.12.6.
- H2 para la configuración de desarrollo por defecto.
- Driver MySQL para configuración alternativa.
- HTML, CSS y JavaScript para la interfaz.
- Spring Boot Test para pruebas automatizadas.

El `pom.xml` declara Java 25. Verificar que el JDK y Maven instalados sean compatibles.

## 3. Estructura principal

```text
SATE-UNIAJC/
├── .github/
├── docs/
│   ├── acta_constitucion.md
│   └── logo-sate.png
├── src/
│   ├── main/
│   │   ├── java/co/uniajc/sate/
│   │   │   ├── config/
│   │   │   ├── controllers/
│   │   │   ├── models/
│   │   │   ├── repositories/
│   │   │   └── services/
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

## 4. Componentes del backend

- **Controllers:** exponen operaciones de autenticación, estudiantes, asistencias, alertas, tutorías y dashboard.
- **Services:** contienen lógica de autenticación/JWT, asistencia y alertas.
- **Repositories:** interfaces de acceso a datos.
- **Models:** representan usuario, rol, estudiante, materia, asistencia, alerta y tutoría.
- **Config:** inicialización de datos, configuración de contraseñas, seguridad y filtro JWT.
- **Static:** página HTML, estilos CSS y JavaScript de la interfaz.

## 5. Endpoints identificados

Las rutas se identificaron en los controladores. Esta lista no implica que cada endpoint haya sido probado individualmente mediante HTTP.

| Método | Ruta | Propósito |
|---|---|---|
| POST | `/api/auth/login` | Iniciar sesión |
| POST | `/api/auth/forgot-password` | Solicitar recuperación |
| POST | `/api/auth/reset-password` | Restablecer contraseña |
| GET | `/api/estudiantes` | Listar estudiantes |
| GET | `/api/estudiantes/{id}` | Consultar estudiante |
| POST | `/api/estudiantes` | Crear estudiante |
| PUT | `/api/estudiantes/{id}/registro` | Actualizar registro de estudiante |
| POST | `/api/asistencias` | Registrar asistencia |
| GET | `/api/asistencias/estudiante/{id}` | Consultar asistencias por estudiante |
| GET | `/api/alertas` | Consultar alertas |
| GET | `/api/alertas/pendientes` | Consultar alertas pendientes |
| POST | `/api/alertas/evaluar` | Evaluar alertas |
| PUT | `/api/alertas/{id}/atendida` | Marcar alerta como atendida |
| GET | `/api/tutorias` | Listar tutorías |
| POST | `/api/tutorias` | Crear tutoría |
| PUT | `/api/tutorias/{id}/estado` | Actualizar estado de tutoría |
| GET | `/api/dashboard` | Consultar indicadores |

## 6. Seguridad observada en configuración

La configuración establece sesiones sin estado, desactiva CSRF y define reglas de acceso por ruta y rol. Permite `/api/auth/**` sin autenticación, limita `/api/asistencias/**` al rol `DOCENTE` y `/api/alertas/**` y `/api/dashboard/**` al rol `DIRECTOR`; las demás rutas requieren autenticación.

El filtro JWT procesa tokens enviados como `Bearer` y construye la autoridad a partir del rol. Son observaciones estáticas; se deben complementar con pruebas HTTP de accesos permitidos y denegados.

## 7. Configuración de base de datos

### Por defecto

`application.properties` configura H2 en memoria con URL `jdbc:h2:mem:satedb`, usuario `sa`, contraseña vacía, creación del esquema al iniciar y consola H2 habilitada. Está orientada al desarrollo y no conserva datos después de detener la aplicación.

### MySQL alternativa

Existe `application-mysql.properties`, con conexión a `localhost:3306/sate_uniajc`, usuario `root` y contraseña vacía. Antes de usarla, configurar credenciales seguras y confirmar cómo se activa el perfil MySQL. No publicar contraseñas ni datos de conexión sensibles.

## 8. Ejecución local

Requisitos:
- JDK compatible con la versión declarada en `pom.xml`.
- Maven instalado.
- Acceso al repositorio.

Desde la raíz del proyecto:

```bash
mvn test
mvn spring-boot:run
```

La configuración por defecto apunta a H2. Para usar MySQL, configurar la base de datos y las credenciales, confirmar el mecanismo de activación del perfil y comprobar la conexión antes de iniciar.

## 9. Pruebas

En una ejecución reportada se ejecutaron 3 pruebas, sin fallos ni errores y con `BUILD SUCCESS`. Cubren el porcentaje de inasistencia, el registro de asistencia con actualización del porcentaje y una alerta roja cuando la inasistencia supera el 15 %. Se recomienda ampliar las pruebas de integración de endpoints, seguridad, validación y persistencia MySQL.

## 10. Pendientes de verificación

- Probar por HTTP el comportamiento de cada endpoint.
- Generar un reporte de cobertura.
- Verificar el flujo completo de recuperación de contraseña.
- Confirmar si la interfaz adjunta el JWT en todas las solicitudes protegidas.
- Validar la configuración MySQL en un ambiente con MySQL disponible.
- Alinear README, versiones requeridas y comandos con el entorno real.
