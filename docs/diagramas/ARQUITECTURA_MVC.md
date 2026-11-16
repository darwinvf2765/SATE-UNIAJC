

\# Diagrama de arquitectura MVC — SATE-UNIAJC



\## 1. Objetivo



Representar la arquitectura general de SATE-UNIAJC y las responsabilidades de sus componentes.



La aplicación utiliza una arquitectura por capas basada en Spring Boot, con separación entre presentación, controladores, servicios, modelos y persistencia.



\## 2. Diagrama de arquitectura



```mermaid

flowchart TD

&#x20;   U\["Usuario: Director, Docente o Estudiante"]



&#x20;   subgraph V\["Vista - Frontend"]

&#x20;       HTML\["index.html"]

&#x20;       JS\["app.js"]

&#x20;       CSS\["styles.css"]

&#x20;   end



&#x20;   subgraph C\["Controladores REST"]

&#x20;       AUTH\["AuthController"]

&#x20;       EST\["EstudianteController"]

&#x20;       ASIS\["AsistenciaController"]

&#x20;       ALER\["AlertaController"]

&#x20;       TUT\["TutoriaController"]

&#x20;       DASH\["DashboardController"]

&#x20;   end



&#x20;   subgraph S\["Servicios - Logica de negocio"]

&#x20;       AUTHS\["AuthService y JwtService"]

&#x20;       ASISS\["AsistenciaService"]

&#x20;       ALERTS\["AlertaService"]

&#x20;       EVAL\["EvaluadorInasistencia y EvaluadorPromedio"]

&#x20;       NOTIF\["NotificacionSistemaService"]

&#x20;   end



&#x20;   subgraph P\["Persistencia y modelo"]

&#x20;       REPO\["Repositorios JPA"]

&#x20;       MODEL\["Entidades del dominio"]

&#x20;   end



&#x20;   DB\[("Base de datos H2 / MySQL")]



&#x20;   U --> HTML

&#x20;   HTML --> JS

&#x20;   CSS -.-> HTML



&#x20;   JS -->|"HTTP / JSON / JWT"| C



&#x20;   AUTH --> AUTHS

&#x20;   ASIS --> ASISS

&#x20;   ALER --> ALERTS

&#x20;   EST --> REPO

&#x20;   TUT --> REPO

&#x20;   DASH --> REPO



&#x20;   ASISS --> ALERTS

&#x20;   ALERTS --> EVAL

&#x20;   ALERTS --> NOTIF



&#x20;   AUTHS --> REPO

&#x20;   ASISS --> REPO

&#x20;   NOTIF --> REPO



&#x20;   REPO --> MODEL

&#x20;   REPO --> DB

```



\## 3. Responsabilidades



\### Vista



Presenta los formularios, tablas, alertas y resultados al usuario.



\### Controladores



Reciben solicitudes HTTP y coordinan las operaciones invocando los componentes correspondientes.



\### Servicios



Aplican y coordinan las reglas de negocio, como el cálculo de inasistencia y la generación de alertas.



\### Modelos



Representan las entidades del sistema, como Estudiante, Asistencia, Alerta y Usuario.



\### Repositorios



Gestionan la persistencia de información mediante Spring Data JPA.



\### Base de datos



Almacena los registros académicos y las alertas del sistema.



\## 4. Seguridad



Spring Security protege los endpoints de la aplicación.



JWT permite identificar al usuario autenticado y aplicar las restricciones correspondientes a su rol.



\## 5. Relación con las historias de usuario



\- HU-01: autenticación mediante AuthController y AuthService.

\- HU-04: registro y cálculo de asistencia.

\- HU-05: actualización de información académica.

\- HU-06: evaluación de inasistencia superior al 15 %.

\- HU-07: evaluación de promedio académico inferior a 3.0.



\## 6. Consideraciones



El diagrama es una representación conceptual. Algunas operaciones utilizan directamente repositorios desde los controladores y otras pasan por servicios.



El detalle exacto de las dependencias puede ampliarse a partir del código fuente.



