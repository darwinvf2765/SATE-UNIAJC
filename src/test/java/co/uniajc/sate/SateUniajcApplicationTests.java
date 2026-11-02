
package co.uniajc.sate;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Asistencia;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.model.Materia;
import co.uniajc.sate.repository.AlertaRepository;
import co.uniajc.sate.repository.AsistenciaRepository;
import co.uniajc.sate.repository.EstudianteRepository;
import co.uniajc.sate.service.AlertaService;
import co.uniajc.sate.service.AsistenciaService;
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
import static org.mockito.ArgumentMatchers.any;
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

    @InjectMocks
    private AsistenciaService asistenciaService;

    @Test
    void debeCalcularPorcentajeDeInasistencia() {
        Estudiante estudiante = crearEstudiante(0.0, 4.2);
        Materia materia = new Materia("Ingenieria de Software");

        List<Asistencia> asistencias = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            asistencias.add(new Asistencia(
                    estudiante, materia,
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
    void debeRegistrarAsistenciaYActualizarPorcentaje() {
        Estudiante estudiante = crearEstudiante(0.0, 4.2);
        Materia materia = new Materia("Ingenieria de Software");

        Asistencia asistencia = new Asistencia(
                estudiante, materia, LocalDate.of(2026, 3, 20), false
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
    void debeGenerarAlertaRojaCuandoInasistenciaSupera15Porciento() {
        Estudiante estudiante = crearEstudiante(16.0, 4.2);

        String resultado = crearAlertaService().evaluarEstudiante(estudiante);

        assertEquals("ROJA", resultado);
        assertEquals("ROJA", estudiante.getEstadoAlerta());

        verify(estudianteRepository).save(estudiante);
        verify(alertaRepository).save(any(Alerta.class));
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

    @Test
    void elQuincePorCientoExactoNoDebeGenerarAlertaRoja() {
        Estudiante estudiante = crearEstudiante(15.0, 4.2);

        String resultado = crearAlertaService().evaluarEstudiante(estudiante);

        assertEquals("VERDE", resultado);
        verify(alertaRepository, org.mockito.Mockito.never())
                .save(any(Alerta.class));
    }

    @Test
    void promedioExactamenteTresNoDebeGenerarAlertaAmarilla() {
        Estudiante estudiante = crearEstudiante(0.0, 3.0);

        String resultado = crearAlertaService().evaluarEstudiante(estudiante);

        assertEquals("VERDE", resultado);
        verify(alertaRepository, org.mockito.Mockito.never())
                .save(any(Alerta.class));
    }

    @Test
    void promedioInferiorATresDebeGenerarAlertaAmarilla() {
        Estudiante estudiante = crearEstudiante(0.0, 2.9);

        String resultado = crearAlertaService().evaluarEstudiante(estudiante);

        assertEquals("AMARILLA", resultado);
        assertEquals("AMARILLA", estudiante.getEstadoAlerta());
        verify(alertaRepository).save(any(Alerta.class));
    }

    private Estudiante crearEstudiante(double inasistencia, double promedio) {
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

    private AlertaService crearAlertaService() {
        return new AlertaService(estudianteRepository, alertaRepository);
    }
}
