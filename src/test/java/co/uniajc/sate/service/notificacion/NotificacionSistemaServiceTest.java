package co.uniajc.sate.service.notificacion;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.persistence.AlertaRepository;
import co.uniajc.sate.service.evaluacion.EvaluadorAlerta;
import co.uniajc.sate.service.factory.AlertaFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionSistemaServiceTest {

    @Mock
    private AlertaRepository alertaRepository;

    @Mock
    private AlertaFactory alertaFactory;

    @InjectMocks
    private NotificacionSistemaService servicio;

    @Test
    void debeGuardarNotificacionEnElSistema() {

        Estudiante estudiante = mock(Estudiante.class);

        when(estudiante.getId()).thenReturn(1L);

        EvaluadorAlerta.ResultadoEvaluacion resultado =
                new EvaluadorAlerta.ResultadoEvaluacion(
                        "ROJA",
                        "Inasistencia superior al 15%"
                );

        Alerta alerta = mock(Alerta.class);

        when(alertaRepository
                .existsByEstudianteIdAndTipoAndAtendidaFalse(
                        1L, "ROJA"
                )).thenReturn(false);

        when(alertaFactory.crearAlerta(estudiante, resultado))
                .thenReturn(alerta);

        servicio.notificar(estudiante, resultado);

        verify(alertaFactory).crearAlerta(estudiante, resultado);

        verify(alertaRepository).save(alerta);
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

        when(alertaRepository
                .existsByEstudianteIdAndTipoAndAtendidaFalse(
                        1L, "ROJA"
                )).thenReturn(true);

        servicio.notificar(estudiante, resultado);

        verifyNoInteractions(alertaFactory);

        verify(alertaRepository, never())
                .save(any(Alerta.class));
    }
}