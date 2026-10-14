# Product Backlog — SATE-UNIAJC

**Producto:** Sistema de Alertas Tempranas Estudiantiles (SATE-UNIAJC)  
**Repositorio:** https://github.com/darwinvf2765/SATE-UNIAJC  
**Estado:** propuesta inicial para validar con el Product Owner y el equipo.

## Backlog priorizado propuesto

| ID | Historia de usuario | Criterios de aceptación resumidos | Prioridad propuesta | Estado sugerido |
|---|---|---|---|---|
| PB-01 | Como usuario autorizado, quiero iniciar sesión para acceder según mi rol. | El endpoint acepta credenciales válidas y responde con autenticación; credenciales inválidas producen un error controlado. | Alta | Implementado en código; validar con pruebas de integración |
| PB-02 | Como usuario, quiero recuperar mi contraseña para volver a acceder a mi cuenta. | Existen endpoints de solicitud y restablecimiento; validar el flujo completo y la seguridad del mecanismo. | Alta | Implementación presente; validar |
| PB-03 | Como director, quiero consultar el dashboard para revisar indicadores del sistema. | `/api/dashboard` responde con indicadores y la interfaz los muestra sin errores. | Alta | Implementado; validar por HTTP |
| PB-04 | Como usuario autorizado, quiero consultar estudiantes para revisar su información. | Se puede listar estudiantes y consultar un estudiante por identificador. | Alta | Implementado; validar permisos y respuestas |
| PB-05 | Como usuario autorizado, quiero registrar estudiantes para mantener actualizada la información. | Se puede crear un estudiante y actualizar su registro mediante las rutas disponibles. | Alta | Implementado; validar datos y duplicados |
| PB-06 | Como docente, quiero registrar asistencias para mantener seguimiento por estudiante. | Se registra asistencia y se consultan asistencias por estudiante; los datos se guardan correctamente. | Alta | Implementado; hay pruebas unitarias relacionadas |
| PB-07 | Como director, quiero consultar alertas para identificar estudiantes que requieren seguimiento. | Se consultan alertas generales y pendientes con datos suficientes para la interfaz. | Alta | Implementado; validar reglas y permisos |
| PB-08 | Como director, quiero evaluar alertas para detectar riesgos por inasistencia. | El nivel asignado cumple las reglas de negocio documentadas y verificadas con pruebas. | Alta | Implementado; una prueba cubre alerta roja por encima del 15 % |
| PB-09 | Como director, quiero marcar alertas como atendidas para registrar el seguimiento. | Se actualiza el estado y la interfaz refleja el cambio. | Media | Implementado; validar persistencia |
| PB-10 | Como usuario autorizado, quiero crear tutorías y actualizar su estado para coordinar acompañamiento. | Se pueden consultar, crear y cambiar el estado de tutorías. | Media | Implementado; validar flujo |
| PB-11 | Como equipo, queremos ejecutar pruebas automatizadas para detectar regresiones. | `mvn test` finaliza correctamente y los casos relevantes tienen aserciones claras. | Alta | 3 pruebas reportadas exitosas en la ejecución disponible |
| PB-12 | Como equipo, queremos documentar instalación, configuración y pruebas. | La documentación describe los requisitos, ejecución y alcance real de las pruebas. | Media | En revisión |

## Definition of Done propuesta

- Código integrado en la rama acordada mediante revisión del equipo.
- Compilación y pruebas automatizadas aplicables exitosas.
- Criterios de aceptación verificados.
- Documentación afectada actualizada.
- Defectos críticos conocidos registrados.

## Nota de trazabilidad

Las historias se redactaron a partir de los controladores, servicios, interfaz y pruebas identificados en el repositorio. Las prioridades y estados son propuestas para revisar, no una aprobación formal ni evidencia de que cada endpoint haya sido probado de extremo a extremo.
