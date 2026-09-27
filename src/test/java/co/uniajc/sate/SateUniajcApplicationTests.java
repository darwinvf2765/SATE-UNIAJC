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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        Estudiante estudiante = new Estudiante(
                "123456789",
                "Juan Prueba",
                "Ingenieria de Software",
                "2026-1",
                0.0,
                4.2,
                null
        );

        Materia materia = new Materia("Ingenieria de Software");

        List<Asistencia> asistencias = List.of(
                new Asistencia(
                        estudiante,
                        materia,
                        LocalDate.of(2026, 1, 10),
                        true
                ),
                new Asistencia(
                        estudiante,
                        materia,
                        LocalDate.of(2026, 1, 17),
                        true
                ),
                new Asistencia(
                        estudiante,
                        materia,
                        LocalDate.of(2026, 1, 24),
                        true
                ),
                new Asistencia(
                        estudiante,
                        materia,
                        LocalDate.of(2026, 1, 31),
                        false
                ),
                new Asistencia(
                        estudiante,
                        materia,
                        LocalDate.of(2026, 2, 7),
                        true
                ),
                new Asistencia(
                        estudiante,
                        materia,
                        LocalDate.of(2026, 2, 14),
                        true
                ),
                new Asistencia(
                        estudiante,
                        materia,
                        LocalDate.of(2026, 2, 21),
                        true
                ),
                new Asistencia(
                        estudiante,
                        materia,
                        LocalDate.of(2026, 2, 28),
                        true
                ),
                new Asistencia(
                        estudiante,
                        materia,
                        LocalDate.of(2026, 3, 7),
                        true
                ),
                new Asistencia(
                        estudiante,
                        materia,
                        LocalDate.of(2026, 3, 14),
                        false
                )
        );

        when(asistenciaRepository.findByEstudiante(estudiante))
                .thenReturn(asistencias);

        double porcentaje =
                asistenciaService.calcularPorcentajeInasistencia(estudiante);

        assertEquals(20.0, porcentaje);

        verify(asistenciaRepository).findByEstudiante(estudiante);
    }

    @Test
    void debeRegistrarAsistenciaYActualizarPorcentaje() {

        Estudiante estudiante = new Estudiante(
                "987654321",
                "Estudiante Prueba",
                "Ingenieria de Software",
                "2026-1",
                0.0,
                4.2,
                null
        );

        Materia materia = new Materia("Ingenieria de Software");

        Asistencia asistencia = new Asistencia(
                estudiante,
                materia,
                LocalDate.of(2026, 3, 20),
                false
        );

        List<Asistencia> asistencias = List.of(asistencia);

        when(asistenciaRepository.save(asistencia))
                .thenReturn(asistencia);

        when(asistenciaRepository.findByEstudiante(estudiante))
                .thenReturn(asistencias);

        asistenciaService.registrar(asistencia);

        assertEquals(100.0, estudiante.getPorcentajeInasistencia());

        verify(asistenciaRepository).save(asistencia);
        verify(estudianteRepository).save(estudiante);
        verify(alertaService).evaluarEstudiante(estudiante);
    }

    @Test
    void debeGenerarAlertaRojaCuandoInasistenciaSupera15Porciento() {

        Estudiante estudiante = new Estudiante(
                "123456789",
                "Juan Prueba",
                "Ingenieria de Software",
                "2026-1",
                16.0,
                4.2,
                null
        );

        String resultado = new AlertaService(
                estudianteRepository,
                alertaRepository
        ).evaluarEstudiante(estudiante);

        assertEquals("ROJA", resultado);
        assertEquals("ROJA", estudiante.getEstadoAlerta());

        verify(estudianteRepository).save(estudiante);
        verify(alertaRepository).save(
                org.mockito.ArgumentMatchers.any(Alerta.class)
        );
    }
}