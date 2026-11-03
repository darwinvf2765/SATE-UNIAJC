
package co.uniajc.sate.service.notificacion;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.repository.AlertaRepository;
import co.uniajc.sate.service.evaluacion.EvaluadorAlerta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionSistemaServiceTest {

    @Mock
    private AlertaRepository alertaRepository;

    @Test
    void debeGuardarNotificacionEnElSistema() {

        Estudiante estudiante = mock(Estudiante.class);

        when(estudiante.getId()).thenReturn(1L);
        when(estudiante.getNombre()).thenReturn("Juan Prueba");

        EvaluadorAlerta.ResultadoEvaluacion resultado =
                new EvaluadorAlerta.ResultadoEvaluacion(
                        "ROJA",
                        "Inasistencia superior al 15%"
                );

        when(alertaRepository.existsByEstudianteIdAndTipoAndAtendidaFalse(
                1L, "ROJA"
        )).thenReturn(false);

        NotificacionSistemaService servicio =
                new NotificacionSistemaService(alertaRepository);

        servicio.notificar(estudiante, resultado);

        verify(alertaRepository).save(any(Alerta.class));
    }

    @Test
    void noDebeGuardarUnaNotificacionDuplicada() {

        Estudiante estudiante = mock(Estudiante.class);

        when(estudiante.getId()).thenReturn(1L);

        EvaluadorAlerta.ResultadoEvaluacion resultado =
                new EvaluadorAlerta.ResultadoEvaluacion(
                        "ROJA",
                        "Inasistencia superior al 15%"
                );

        when(alertaRepository.existsByEstudianteIdAndTipoAndAtendidaFalse(
                1L, "ROJA"
        )).thenReturn(true);

        NotificacionSistemaService servicio =
                new NotificacionSistemaService(alertaRepository);

        servicio.notificar(estudiante, resultado);

        verify(alertaRepository, never()).save(any(Alerta.class));
    }
}
