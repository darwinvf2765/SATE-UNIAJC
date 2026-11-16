
# Revisión de principios YAGNI, KISS y DRY — SATE-UNIAJC

## Sesión 12 — Ingeniería de Software I

**Proyecto:** SATE-UNIAJC

**Componente revisado:** `AlertaService.java`

**Historias relacionadas:** HU-06 y HU-07.

## 1. Objetivo

Revisar la aplicación de los principios de diseño ágil YAGNI, KISS y DRY en el motor de alertas académicas.

## 2. Código analizado

Archivo:

`src/main/java/co/uniajc/sate/service/AlertaService.java`

Método principal:

`evaluarEstudiante(Estudiante estudiante)`

El método coordina los evaluadores, solicita las notificaciones y actualiza el estado general del estudiante.

## 3. Principio YAGNI

**YAGNI — You Aren't Gonna Need It**

Consiste en evitar implementar funcionalidades que no se necesitan para los requerimientos actuales.

En `AlertaService`, la lógica revisada se concentra en evaluar las condiciones de riesgo académico existentes.

No se observan algoritmos predictivos adicionales ni funcionalidades ajenas a las historias analizadas.

**Resultado:** no se identifica una necesidad de eliminar funcionalidades en el método revisado.

## 4. Principio KISS

**KISS — Keep It Simple, Stupid**

Propone mantener el código sencillo y fácil de comprender.

El método utiliza:

- Un ciclo para ejecutar los evaluadores.
- Un ciclo para gestionar los resultados.
- Condiciones para seleccionar el estado general del estudiante.

La prioridad de los estados se expresa directamente:

1. ROJA, si existe una alerta roja.
2. AMARILLA, si existen resultados sin alerta roja.
3. VERDE, si no se detectan condiciones de alerta.

**Resultado:** la estructura revisada es clara y no requiere introducir un mecanismo más complejo.

## 5. Principio DRY

**DRY — Don't Repeat Yourself**

Busca evitar la repetición innecesaria de reglas y procedimientos.

El servicio utiliza la interfaz `EvaluadorAlerta` para ejecutar distintos evaluadores mediante un mecanismo común.

También utiliza `NotificacionService` para gestionar los resultados sin repetir la lógica de notificación para cada tipo de alerta.

**Resultado:** se identifica reutilización de componentes y separación de responsabilidades.

## 6. Relación con las historias de usuario

| Historia | Regla de negocio |
|---|---|
| HU-06 | Inasistencia superior al 15 %: alerta ROJA |
| HU-07 | Promedio inferior a 3.0: alerta AMARILLA |

Las reglas específicas se encuentran delegadas a los evaluadores correspondientes.

## 7. Conclusiones

La revisión de `AlertaService.java` muestra una implementación compatible con los principios YAGNI, KISS y DRY.

No se identificaron cambios obligatorios en el método analizado.

Esta conclusión se limita al componente revisado y no representa una auditoría completa de todo el código fuente.

## 8. Evidencia

Código fuente:

https://github.com/darwinvf2765/SATE-UNIAJC/blob/master/src/main/java/co/uniajc/sate/service/AlertaService.java

Pruebas automatizadas:

18 pruebas ejecutadas exitosamente con Maven.

Integración continua y entrega continua:

https://github.com/darwinvf2765/SATE-UNIAJC/actions

## 9. Mejoras futuras

- Revisar otros servicios para detectar lógica duplicada.
- Ampliar las pruebas de integración.
- Mantener los criterios de aceptación actualizados en GitHub Issues.
- Evitar incorporar funcionalidades fuera del alcance acordado.
