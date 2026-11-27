package co.uniajc.sate.service;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.model.Tutoria;
import co.uniajc.sate.persistence.AlertaRepository;
import co.uniajc.sate.persistence.EstudianteRepository;
import co.uniajc.sate.persistence.TutoriaRepository;
import co.uniajc.sate.view.DashboardResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private TutoriaRepository tutoriaRepository;

    @Mock
    private AlertaRepository alertaRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void debeRetornarResumenVacio() {

        when(estudianteRepository.findAll())
                .thenReturn(List.of());

        when(tutoriaRepository.findAll())
                .thenReturn(List.of());

        when(alertaRepository.findByAtendidaFalse())
                .thenReturn(List.of());

        DashboardResponse resultado = dashboardService.resumen();

        assertEquals(0, resultado.totalEstudiantes());
        assertEquals(0L, resultado.alertasRojas());
        assertEquals(0L, resultado.alertasAmarillas());
        assertEquals(0L, resultado.estudiantesVerdes());
        assertEquals(0.0, resultado.proyeccionDesercion());
        assertEquals(0, resultado.totalTutorias());
        assertEquals(0L, resultado.tutoriasRealizadas());
        assertEquals(0.0, resultado.efectividadTutorias());
        assertTrue(resultado.estudiantesPorCohorte().isEmpty());
    }

    @Test
    void debeContarEstudiantesCorrectamente() {

        Estudiante primero = crearEstudiante("2026-1", "VERDE");
        Estudiante segundo = crearEstudiante("2026-1", "ROJA");
        Estudiante tercero = crearEstudiante("2026-2", "VERDE");

        when(estudianteRepository.findAll())
                .thenReturn(List.of(primero, segundo, tercero));

        DashboardResponse resultado = dashboardService.resumen();

        assertEquals(3, resultado.totalEstudiantes());
        assertEquals(2L, resultado.estudiantesVerdes());
    }

    @Test
    void debeContarAlertasPorTipo() {

        Alerta roja1 = mock(Alerta.class);
        Alerta roja2 = mock(Alerta.class);
        Alerta amarilla = mock(Alerta.class);

        when(roja1.getTipo()).thenReturn("ROJA");
        when(roja2.getTipo()).thenReturn("ROJA");
        when(amarilla.getTipo()).thenReturn("AMARILLA");

        when(alertaRepository.findByAtendidaFalse())
                .thenReturn(List.of(roja1, roja2, amarilla));

        DashboardResponse resultado = dashboardService.resumen();

        assertEquals(2L, resultado.alertasRojas());
        assertEquals(1L, resultado.alertasAmarillas());
    }

    @Test
    void debeCalcularProyeccionSinDuplicarEstudiantes() {

        Estudiante primero = crearEstudiante("2026-1", "ROJA");
        Estudiante segundo = crearEstudiante("2026-1", "VERDE");

        Alerta roja = mock(Alerta.class);
        Alerta amarilla = mock(Alerta.class);

        when(roja.getTipo()).thenReturn("ROJA");
        when(amarilla.getTipo()).thenReturn("AMARILLA");

        when(roja.getEstudianteId()).thenReturn(1L);
        when(amarilla.getEstudianteId()).thenReturn(1L);

        when(estudianteRepository.findAll())
                .thenReturn(List.of(primero, segundo));

        when(alertaRepository.findByAtendidaFalse())
                .thenReturn(List.of(roja, amarilla));

        DashboardResponse resultado = dashboardService.resumen();

        assertEquals(45.0, resultado.proyeccionDesercion(), 0.01);
    }

    @Test
    void debeCalcularEfectividadDeTutorias() {

        Tutoria primera = mock(Tutoria.class);
        Tutoria segunda = mock(Tutoria.class);
        Tutoria tercera = mock(Tutoria.class);
        Tutoria cuarta = mock(Tutoria.class);

        when(primera.getEstado()).thenReturn("REALIZADA");
        when(segunda.getEstado()).thenReturn("REALIZADA");
        when(tercera.getEstado()).thenReturn("AGENDADA");
        when(cuarta.getEstado()).thenReturn("AGENDADA");

        when(tutoriaRepository.findAll())
                .thenReturn(List.of(
                        primera, segunda, tercera, cuarta));

        DashboardResponse resultado = dashboardService.resumen();

        assertEquals(4, resultado.totalTutorias());
        assertEquals(2L, resultado.tutoriasRealizadas());
        assertEquals(50.0, resultado.efectividadTutorias(), 0.01);
    }

    @Test
    void debeAgruparEstudiantesPorCohorte() {

        Estudiante primero = crearEstudiante("2026-1", "VERDE");
        Estudiante segundo = crearEstudiante("2026-1", "ROJA");
        Estudiante tercero = crearEstudiante("2026-2", "AMARILLA");

        when(estudianteRepository.findAll())
                .thenReturn(List.of(primero, segundo, tercero));

        DashboardResponse resultado = dashboardService.resumen();

        assertEquals(
                Map.of("2026-1", 2L, "2026-2", 1L),
                resultado.estudiantesPorCohorte()
        );
    }

    private Estudiante crearEstudiante(
            String cohorte, String estado) {

        Estudiante estudiante = new Estudiante();

        estudiante.setCohorte(cohorte);
        estudiante.setEstadoAlerta(estado);

        return estudiante;
    }
}
