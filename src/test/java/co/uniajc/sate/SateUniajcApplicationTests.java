
package co.uniajc.sate;

import co.uniajc.sate.model.Asistencia;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.model.Materia;
import co.uniajc.sate.repository.AlertaRepository;
import co.uniajc.sate.repository.AsistenciaRepository;
import co.uniajc.sate.repository.EstudianteRepository;
import co.uniajc.sate.service.AlertaService;
import co.uniajc.sate.service.AsistenciaService;
import co.uniajc.sate.service.evaluacion.EvaluadorAlerta;
import co.uniajc.sate.service.evaluacion.EvaluadorInasistencia;
import co.uniajc.sate.service.evaluacion.EvaluadorPromedio;
import co.uniajc.sate.service.notificacion.NotificacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SateUniajcApplicationTests {

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private AlertaRepository alertaRepository;

    @Mock
    private AsistenciaRepository asistenciaRepository;

    @Mock
    private AlertaService alertaService;

    @Mock
    private NotificacionService notificacionService;

    @InjectMocks
    private AsistenciaService asistenciaService;

    // ============================
    // PRUEBAS HU-04 Y HU-05
    // ============================

    @Test
    void debeCalcularPorcentajeDeInasistencia() {

        Estudiante estudiante = crearEstudiante(0.0, 4.2);
        Materia materia = new Materia("Ingenieria de Software");

        List<Asistencia> asistencias = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            asistencias.add(new Asistencia(
                    estudiante,
                    materia,
                    LocalDate.of(2026, 1, 1).plusDays(i),
                    i != 3 && i != 9
            ));
        }

        when(asistenciaRepository.findByEstudiante(estudiante))
                .thenReturn(asistencias);

        double porcentaje =
                asistenciaService.calcularPorcentajeInasistencia(estudiante);

        assertEquals(20.0, porcentaje);
        verify(asistenciaRepository).findByEstudiante(estudiante);
    }

    @Test
    void debeRetornarCeroCuandoNoHayAsistencias() {

        Estudiante estudiante = crearEstudiante(0.0, 4.2);

        when(asistenciaRepository.findByEstudiante(estudiante))
                .thenReturn(List.of());

        double porcentaje =
                asistenciaService.calcularPorcentajeInasistencia(estudiante);

        assertEquals(0.0, porcentaje);
        verify(asistenciaRepository).findByEstudiante(estudiante);
    }

    @Test
    void debeRegistrarAsistenciaYActualizarPorcentaje() {

        Estudiante estudiante = crearEstudiante(0.0, 4.2);
        Materia materia = new Materia("Ingenieria de Software");

        Asistencia asistencia = new Asistencia(
                estudiante,
                materia,
                LocalDate.of(2026, 3, 20),
                false
        );

        when(asistenciaRepository.save(asistencia)).thenReturn(asistencia);
        when(asistenciaRepository.findByEstudiante(estudiante))
                .thenReturn(List.of(asistencia));

        asistenciaService.registrar(asistencia);

        assertEquals(100.0, estudiante.getPorcentajeInasistencia());
        verify(asistenciaRepository).save(asistencia);
        verify(estudianteRepository).save(estudiante);
        verify(alertaService).evaluarEstudiante(estudiante);
    }

    @Test
    void debeCalcularInasistenciaPorMateria() {

        Estudiante estudiante = crearEstudiante(0.0, 4.2);
        Materia materia = new Materia("Matematicas");

        List<Asistencia> asistencias = List.of(
                new Asistencia(estudiante, materia, LocalDate.of(2026, 1, 10), true),
                new Asistencia(estudiante, materia, LocalDate.of(2026, 1, 17), false),
                new Asistencia(estudiante, materia, LocalDate.of(2026, 1, 24), true),
                new Asistencia(estudiante, materia, LocalDate.of(2026, 1, 31), true)
        );

        when(asistenciaRepository.findByEstudianteAndMateria(estudiante, materia))
                .thenReturn(asistencias);

        double porcentaje = asistenciaService.calcularPorcentajeInasistencia(
                estudiante, materia
        );

        assertEquals(25.0, porcentaje);
    }

    @Test
    void debeRetornarCeroCuandoNoHayAsistenciasPorMateria() {

        Estudiante estudiante = crearEstudiante(0.0, 4.2);
        Materia materia = new Materia("Matematicas");

        when(asistenciaRepository.findByEstudianteAndMateria(estudiante, materia))
                .thenReturn(List.of());

        double porcentaje = asistenciaService.calcularPorcentajeInasistencia(
                estudiante, materia
        );

        assertEquals(0.0, porcentaje);
    }

    @Test
    void debeCalcularCorrectamenteElQuincePorCiento() {

        Estudiante estudiante = crearEstudiante(0.0, 4.2);
        Materia materia = new Materia("Matematicas");

        List<Asistencia> asistencias = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            asistencias.add(new Asistencia(
                    estudiante,
                    materia,
                    LocalDate.of(2026, 1, 1).plusDays(i),
                    i >= 3
            ));
        }

        when(asistenciaRepository.findByEstudianteAndMateria(estudiante, materia))
                .thenReturn(asistencias);

        double porcentaje = asistenciaService.calcularPorcentajeInasistencia(
                estudiante, materia
        );

        assertEquals(15.0, porcentaje);
    }

    @Test
    void debeRechazarEstudianteNuloAlCalcularPorMateria() {

        Materia materia = new Materia("Matematicas");

        assertThrows(
                IllegalArgumentException.class,
                () -> asistenciaService.calcularPorcentajeInasistencia(null, materia)
        );
    }

    // ============================
    // PRUEBAS HU-06: ALERTA ROJA
    // ============================

    @Test
    void debeGenerarAlertaRojaCuandoInasistenciaSupera15Porciento() {

        Estudiante estudiante = crearEstudiante(16.0, 4.2);

        AlertaService servicio = crearAlertaService(
                new EvaluadorInasistencia()
        );

        String resultado = servicio.evaluarEstudiante(estudiante);

        assertEquals("ROJA", resultado);
        assertEquals("ROJA", estudiante.getEstadoAlerta());

        verify(notificacionService).notificar(
                any(Estudiante.class),
                any(EvaluadorAlerta.ResultadoEvaluacion.class)
        );

        verify(estudianteRepository).save(estudiante);
    }

    @Test
    void noDebeGenerarAlertaRojaConInasistenciaExactamente15() {

        Estudiante estudiante = crearEstudiante(15.0, 4.2);

        AlertaService servicio = crearAlertaService(
                new EvaluadorInasistencia()
        );

        String resultado = servicio.evaluarEstudiante(estudiante);

        assertEquals("VERDE", resultado);

        verify(notificacionService, never()).notificar(
                any(Estudiante.class),
                any(EvaluadorAlerta.ResultadoEvaluacion.class)
        );
    }

    @Test
    void debeRechazarInasistenciaNegativa() {

        Estudiante estudiante = crearEstudiante(-1.0, 4.0);

        EvaluadorInasistencia evaluador = new EvaluadorInasistencia();

        IllegalArgumentException excepcion = assertThrows(
                IllegalArgumentException.class,
                () -> evaluador.evaluar(estudiante)
        );

        assertTrue(excepcion.getMessage().contains("entre 0 y 100"));
    }

    @Test
    void debeRechazarInasistenciaSuperiorA100() {

        Estudiante estudiante = crearEstudiante(101.0, 4.0);

        EvaluadorInasistencia evaluador = new EvaluadorInasistencia();

        assertThrows(
                IllegalArgumentException.class,
                () -> evaluador.evaluar(estudiante)
        );
    }

    // ============================
    // PRUEBAS HU-07: ALERTA AMARILLA
    // ============================

    @Test
    void debeGenerarAlertaAmarillaCuandoPromedioEsInferiorA3() {

        Estudiante estudiante = crearEstudiante(10.0, 2.8);

        AlertaService servicio = crearAlertaService(
                new EvaluadorPromedio()
        );

        String resultado = servicio.evaluarEstudiante(estudiante);

        assertEquals("AMARILLA", resultado);
        assertEquals("AMARILLA", estudiante.getEstadoAlerta());

        verify(notificacionService).notificar(
                any(Estudiante.class),
                any(EvaluadorAlerta.ResultadoEvaluacion.class)
        );
    }

    @Test
    void noDebeGenerarAlertaAmarillaConPromedioExactamente3() {

        Estudiante estudiante = crearEstudiante(10.0, 3.0);

        AlertaService servicio = crearAlertaService(
                new EvaluadorPromedio()
        );

        String resultado = servicio.evaluarEstudiante(estudiante);

        assertEquals("VERDE", resultado);

        verify(notificacionService, never()).notificar(
                any(Estudiante.class),
                any(EvaluadorAlerta.ResultadoEvaluacion.class)
        );
    }

    @Test
    void debeRechazarPromedioNegativo() {

        Estudiante estudiante = crearEstudiante(10.0, -1.0);

        EvaluadorPromedio evaluador = new EvaluadorPromedio();

        assertThrows(
                IllegalArgumentException.class,
                () -> evaluador.evaluar(estudiante)
        );
    }

    @Test
    void debeRechazarPromedioSuperiorA5() {

        Estudiante estudiante = crearEstudiante(10.0, 5.1);

        EvaluadorPromedio evaluador = new EvaluadorPromedio();

        assertThrows(
                IllegalArgumentException.class,
                () -> evaluador.evaluar(estudiante)
        );
    }

    // ============================
    // PRUEBA DE AMBAS ALERTAS
    // ============================

    @Test
    void debeGenerarDosAlertasCuandoCumpleAmbasCondiciones() {

        Estudiante estudiante = crearEstudiante(20.0, 2.5);

        AlertaService servicio = crearAlertaService(
                new EvaluadorInasistencia(),
                new EvaluadorPromedio()
        );

        String resultado = servicio.evaluarEstudiante(estudiante);

        assertEquals("ROJA", resultado);
        assertEquals("ROJA", estudiante.getEstadoAlerta());

        verify(notificacionService, times(2)).notificar(
                any(Estudiante.class),
                any(EvaluadorAlerta.ResultadoEvaluacion.class)
        );
    }

    // ============================
    // MÉTODOS AUXILIARES
    // ============================

    private Estudiante crearEstudiante(
            double inasistencia,
            double promedio
    ) {
        return new Estudiante(
                "123456789",
                "Juan Prueba",
                "Ingenieria de Software",
                "2026-1",
                inasistencia,
                promedio,
                null
        );
    }

    private AlertaService crearAlertaService(
            EvaluadorAlerta... evaluadores
    ) {
        return new AlertaService(
                estudianteRepository,
                alertaRepository,
                List.of(evaluadores),
                notificacionService
        );
    }
}
