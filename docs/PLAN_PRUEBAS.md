# Plan de pruebas — SATE-UNIAJC

## 1. Objetivo

Definir una base de verificación para el cálculo de inasistencia, el registro de asistencias, las alertas y los endpoints. Se diferencia entre las pruebas que aparecen en la ejecución reportada y las pruebas propuestas.

## 2. Entorno identificado

- Aplicación Java con Spring Boot y Maven.
- Base de datos por defecto: H2 en memoria (`jdbc:h2:mem:satedb`).
- Configuración alternativa: `application-mysql.properties`.
- Comando de pruebas: `mvn test`.

La base H2 en memoria no conserva datos al detener el proceso. La configuración MySQL requiere una instancia correctamente configurada.

## 3. Resultado conocido

En la ejecución compartida se reportó:

- Tests run: 3
- Failures: 0
- Errors: 0
- Skipped: 0
- Resultado: `BUILD SUCCESS`

Los tres casos son pruebas unitarias relacionadas con el porcentaje de inasistencia, el registro de asistencia y la generación de alerta roja cuando la inasistencia supera el 15 %. Esto no demuestra que todos los controladores, permisos o flujos completos estén probados.

## 4. Casos automatizados identificados

| ID | Caso | Resultado esperado | Estado conocido |
|---|---|---|---|
| TU-01 | Calcular porcentaje con 2 inasistencias de 10 registros | 20 % | Reportado exitoso |
| TU-02 | Registrar asistencia y actualizar porcentaje del estudiante | Se registra la asistencia, se actualiza el porcentaje y se invoca el servicio de alertas | Reportado exitoso |
| TU-03 | Generar alerta roja con inasistencia superior al 15 % | Para el caso de 16 %, el nivel es `ROJA` | Reportado exitoso |

## 5. Casos recomendados pendientes de ejecutar

| ID | Área | Escenario | Resultado esperado |
|---|---|---|---|
| TI-01 | Autenticación | Login con credenciales válidas | Respuesta exitosa y token con formato esperado |
| TI-02 | Autenticación | Login con credenciales inválidas | Error controlado; no se entrega token válido |
| TI-03 | Recuperación | Solicitud con correo registrado y no registrado | Respuesta segura que no revele innecesariamente si existe una cuenta |
| TI-04 | JWT | Solicitud protegida sin token | Acceso denegado |
| TI-05 | Roles | Rol no autorizado intenta registrar asistencia | Acceso denegado |
| TI-06 | Estudiantes | Crear estudiante sin datos obligatorios | Validación y respuesta de error apropiada |
| TI-07 | Asistencias | Consultar asistencias de estudiante inexistente | Respuesta definida y consistente |
| TI-08 | Alertas | Evaluar valores justo por debajo, igual y por encima del umbral | Nivel conforme a la regla documentada |
| TI-09 | Tutorías | Crear tutoría con datos válidos e inválidos | Persistencia correcta o validación controlada |
| TI-10 | Dashboard | Consultar con datos y sin datos | Respuesta válida y estructura estable |
| TI-11 | Persistencia | Reiniciar con perfil MySQL | Datos persistidos y conexión correcta |
| TI-12 | Interfaz | Ejecutar acciones principales desde el navegador | La interfaz procesa errores y actualiza información |

## 6. Procedimiento

1. Abrir una terminal en la raíz del proyecto.
2. Ejecutar `mvn test`.
3. Confirmar que la salida termine con `BUILD SUCCESS`.
4. Revisar los informes en `target/surefire-reports/`.
5. Para cada caso pendiente, registrar fecha, ambiente, datos, resultado esperado, resultado obtenido y evidencia.
6. No marcar un caso como aprobado hasta haberlo ejecutado.

## 7. Plantilla de registro

| Campo | Valor |
|---|---|
| ID del caso | |
| Fecha y responsable | |
| Versión/commit | |
| Ambiente | |
| Datos de entrada | |
| Resultado esperado | |
| Resultado obtenido | |
| Estado (Aprobado/Fallido/Bloqueado) | |
| Evidencia o incidencia | |

## 8. Criterios de salida propuestos

- Las pruebas automatizadas existentes pasan.
- Se ejecutan y documentan las pruebas críticas de autenticación, autorización, asistencia y alertas.
- Los defectos quedan registrados y priorizados.
- Los resultados corresponden a la versión exacta del código entregado.
