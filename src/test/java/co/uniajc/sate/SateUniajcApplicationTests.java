package co.uniajc.sate;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.repository.AlertaRepository;
import co.uniajc.sate.repository.EstudianteRepository;
import co.uniajc.sate.service.AlertaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SateUniajcApplicationTests {

    @Mock
    private EstudianteRepository estudianteRepository;

    @Mock
    private AlertaRepository alertaRepository;

    @InjectMocks
    private AlertaService alertaService;

    @Test
    void debeGenerarAlertaRojaCuandoInasistenciaSupera15Porciento() {

        Estudiante estudiante = new Estudiante(
                "123456789",
                "Juan Prueba",
                "Ingeniería de Software",
                "2026-1",
                16.0,
                4.2,
                null
        );

        String resultado = alertaService.evaluarEstudiante(estudiante);

        assertEquals("ROJA", resultado);
        assertEquals("ROJA", estudiante.getEstadoAlerta());

        verify(estudianteRepository).save(estudiante);

        ArgumentCaptor<Alerta> captor =
                ArgumentCaptor.forClass(Alerta.class);

        verify(alertaRepository).save(captor.capture());

        Alerta alertaGenerada = captor.getValue();

        assertNotNull(alertaGenerada);
        assertEquals("ROJA", alertaGenerada.getTipo());
        assertEquals("Inasistencia superior al 15%",
                alertaGenerada.getMotivo());
        assertEquals("Juan Prueba",
                alertaGenerada.getEstudianteNombre());
        assertEquals(false, alertaGenerada.isAtendida());
    }
}