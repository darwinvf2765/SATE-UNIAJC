# Tablero Kanban — SATE-UNIAJC

**Objetivo:** visualizar el trabajo pendiente, en curso, en revisión/pruebas y terminado. Este tablero es inicial; el equipo debe confirmar y actualizar el estado real de cada tarjeta.

## Tablero inicial

| Por hacer | En curso | En revisión / pruebas | Hecho |
|---|---|---|---|
| Añadir pruebas de integración HTTP para controladores | — | Verificar permisos por rol en endpoints | Aplicación Spring Boot configurada |
| Documentar variables de configuración y perfil MySQL | — | Validar flujo completo de recuperación de contraseña | Autenticación JWT implementada en código |
| Ampliar pruebas de reglas de alerta y casos límite | — | Confirmar que la interfaz envía el token JWT cuando corresponde | Endpoints de estudiantes, asistencias, alertas, tutorías y dashboard presentes |
| Acordar y registrar Definition of Done | — | Revisar documentación de instalación y ejecución | 3 pruebas automatizadas reportadas exitosas |

La columna **En curso** se deja vacía porque no hay evidencia suficiente para afirmar qué tarea está actualmente en desarrollo.

## Tarjetas sugeridas

### K-01 — Verificación de autorización
- **Descripción:** comprobar que cada rol acceda solo a los recursos permitidos.
- **Criterio de salida:** pruebas HTTP de accesos permitidos y denegados con resultados documentados.
- **Estado sugerido:** En revisión / pruebas.

### K-02 — Pruebas de integración de API
- **Descripción:** probar endpoints principales con una base de datos de prueba.
- **Criterio de salida:** registrar solicitud, respuesta esperada, respuesta obtenida y resultado.
- **Estado sugerido:** Por hacer.

### K-03 — Recuperación de contraseña
- **Descripción:** verificar solicitud, validez del mecanismo y restablecimiento.
- **Criterio de salida:** documentar casos exitosos y de error sin exponer credenciales ni tokens.
- **Estado sugerido:** En revisión / pruebas.

### K-04 — Ampliación de pruebas de asistencia y alertas
- **Descripción:** cubrir límites porcentuales, ausencia de registros, datos inválidos y errores de persistencia.
- **Criterio de salida:** pruebas automatizadas para los escenarios acordados.
- **Estado sugerido:** Por hacer.

### K-05 — Actualización de documentación
- **Descripción:** contrastar README, configuración, comandos y estructura real del repositorio.
- **Criterio de salida:** instrucciones ejecutadas en un entorno limpio y actualizadas según lo observado.
- **Estado sugerido:** En revisión / pruebas.

## Política de trabajo propuesta

- Limitar el trabajo simultáneo para terminar tareas antes de abrir nuevas.
- Cada tarjeta debe tener descripción, responsable, criterio de salida y evidencia.
- Mover a “Hecho” solo al cumplir los criterios de salida.
- Revisar el tablero en las reuniones y registrar bloqueos.
