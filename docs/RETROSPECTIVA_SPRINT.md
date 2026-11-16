# 

# \# Retrospectiva de Sprint — SATE-UNIAJC

# 

# \## 1. Información general

# 

# \*\*Proyecto:\*\* SATE-UNIAJC — Sistema de Alertas Tempranas y Acompañamiento Estudiantil

# 

# \*\*Institución:\*\* Institución Universitaria Antonio José Camacho

# 

# \*\*Asignatura:\*\* Ingeniería de Software I

# 

# \*\*Sprint:\*\* Sprint 1 (revisión del trabajo e integración posterior)

# 

# \*\*Fecha de retrospectiva:\*\* Pendiente de confirmar con el equipo.

# 

# \*\*Participantes:\*\*

# \- Darwin Alexander Valencia Flor — Product Owner.

# \- Juan David Rosero Guerrero — Scrum Master.

# \- Jeremy Andrey Agreda Rosero — Developer.

# 

# \*\*Facilitador propuesto:\*\* Juan David Rosero Guerrero, Scrum Master.

# 

# \*\*Nota:\*\* Este documento recoge hechos técnicos verificables y propuestas de mejora. Los acuerdos definitivos deben validarse en una reunión del equipo.

# 

# \---

# 

# \## 2. Objetivo de la retrospectiva

# 

# Identificar los aspectos positivos, las dificultades y las oportunidades de mejora durante el desarrollo e integración de SATE-UNIAJC.

# 

# La retrospectiva busca fortalecer la colaboración del equipo, la calidad del código, la documentación y el seguimiento de las historias de usuario mediante GitHub Projects.

# 

# \---

# 

# \## 3. Técnica Sailboat

# 

# \### Viento — ¿Qué nos ayudó a avanzar?

# 

# \- El uso de Git y GitHub permitió compartir e integrar el código.

# \- La separación del backend en controladores, servicios, modelos y repositorios facilitó la organización.

# \- El trabajo distribuido permitió desarrollar el registro académico y el motor de alertas.

# \- Se incorporaron pruebas automatizadas con JUnit y Mockito.

# \- Se ejecutaron 18 pruebas automatizadas con 0 fallos y 0 errores.

# \- Se configuró GitHub Actions para ejecutar las pruebas automáticamente.

# \- Se recuperó y vinculó el tablero Kanban del equipo.

# 

# \### Anclas — ¿Qué dificultó el trabajo?

# 

# \- Se presentaron conflictos al integrar cambios de diferentes integrantes.

# \- La configuración inicial de Java y Maven produjo errores de compilación.

# \- Algunos documentos conservaron información de versiones anteriores.

# \- Las historias HU-04 a HU-07 no estaban registradas inicialmente en el tablero Kanban.

# \- Faltan evidencias funcionales de algunos criterios de aceptación.

# 

# \### Rocas — ¿Qué riesgos debemos prevenir?

# 

# \- Que la documentación técnica no corresponda al código implementado.

# \- Que las historias se marquen como terminadas sin validar todos sus criterios.

# \- Que se acumulen cambios sin ejecutar las pruebas.

# \- Que existan diferencias entre el repositorio local y la rama principal.

# \- Que algunos endpoints o permisos no cuenten con pruebas de integración.

# \- Que se utilicen configuraciones inseguras de base de datos en ambientes distintos al desarrollo.

# 

# \### Rumbo — ¿Qué debemos mejorar?

# 

# \- Mantener las historias y tareas actualizadas en GitHub Projects.

# \- Ejecutar las pruebas antes de integrar cambios.

# \- Utilizar Pull Requests para revisar nuevas funcionalidades.

# \- Documentar las evidencias de pruebas de aceptación.

# \- Mantener actualizados los documentos técnicos.

# \- Revisar los impedimentos y el avance de las tareas durante el Sprint.

# 

# \---

# 

# \## 4. Resultados técnicos comprobados

# 

# | Actividad | Resultado |

# |---|---|

# | Recuperación del repositorio | Completada |

# | Configuración de Java 25 | Completada |

# | Compilación con Maven | Exitosa |

# | Pruebas automatizadas | 18 exitosas |

# | Fallos en pruebas | 0 |

# | GitHub Actions | Configurado y verificado |

# | Registro de HU-04 a HU-07 en Issues | Completado |

# | Vinculación del tablero Kanban | Completada |

# | Revisión de documentación técnica | En proceso |

# 

# Estos resultados corresponden a actividades técnicas verificadas. No implican que todos los criterios de aceptación del producto estén aprobados.

# 

# \---

# 

# \## 5. Acciones de mejora propuestas

# 

# | ID | Acción | Responsable propuesto | Estado |

# |---|---|---|---|

# | AM-01 | Revisar y actualizar las historias en GitHub Projects | Darwin — Product Owner | Realizado para HU-04 a HU-07 |

# | AM-02 | Mantener el workflow de pruebas Maven | Equipo de desarrollo | Configurado |

# | AM-03 | Actualizar documentación técnica y plan de pruebas | Equipo Scrum | En proceso |

# | AM-04 | Agregar pruebas HTTP de autenticación y autorización | Por asignar | Pendiente |

# | AM-05 | Verificar el flujo de recuperación de contraseña | Por asignar | Pendiente |

# | AM-06 | Validar funcionalmente los criterios de HU-04 a HU-07 | Product Owner y equipo | Pendiente de evidencia completa |

# | AM-07 | Elaborar diagramas UML | Por asignar | Pendiente |

# | AM-08 | Verificar la persistencia mediante MySQL | Por asignar | Pendiente |

# 

# Las asignaciones y fechas definitivas deberán acordarse entre los tres integrantes.

# 

# \---

# 

# \## 6. Recomendaciones para próximos Sprints

# 

# 1\. Definir un Sprint Goal claro antes de comenzar el desarrollo.

# 2\. Registrar cada historia de usuario y sus criterios BDD en GitHub Issues.

# 3\. Dividir las historias en tareas verificables.

# 4\. Asignar responsables y actualizar el estado de las tareas.

# 5\. Mantener el trabajo en progreso dentro de los límites acordados.

# 6\. Ejecutar las pruebas automatizadas antes de integrar cambios.

# 7\. Revisar el resultado del workflow de GitHub Actions.

# 8\. Validar los criterios de aceptación con el Product Owner.

# 9\. Actualizar la documentación como parte de la Definition of Done.

# 10\. Realizar una retrospectiva al finalizar cada Sprint.

# 

# \---

# 

# \## 7. Acuerdos pendientes de validación

# 

# Esta sección debe completarse durante una retrospectiva real del equipo.

# 

# \*\*Fecha de reunión:\*\* Pendiente.

# 

# \*\*Acuerdos aprobados:\*\*

# \- Pendiente de registrar.

# 

# \*\*Responsables:\*\*

# \- Pendiente de confirmar.

# 

# \*\*Fechas de seguimiento:\*\*

# \- Pendiente de definir.

# 

# \*\*Impedimentos activos:\*\*

# \- Pendiente de confirmar.

# 

# \---

# 

# \## 8. Evidencias

# 

# \*\*Repositorio:\*\*

# https://github.com/darwinvf2765/SATE-UNIAJC

# 

# \*\*Tablero Kanban:\*\*

# https://github.com/users/darwinvf2765/projects/1

# 

# \*\*GitHub Actions:\*\*

# https://github.com/darwinvf2765/SATE-UNIAJC/actions

# 

# \*\*Comando de pruebas:\*\*

# 

# &#x20;   mvn clean test

# 

# \*\*Resultado local verificado:\*\*

# 18 pruebas exitosas, 0 fallos, 0 errores y BUILD SUCCESS.

# 

# \---

# 

# \## 9. Conclusión

# 

# El equipo SATE-UNIAJC ha avanzado en la integración del código, la automatización de pruebas y la organización del seguimiento mediante GitHub Projects.

# 

# Se identifican oportunidades para fortalecer la validación funcional, mantener la documentación sincronizada y mejorar la trazabilidad de los criterios de aceptación.

# 

# Los compromisos definitivos deberán revisarse y aprobarse entre los integrantes del equipo Scrum.



