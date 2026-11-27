package co.uniajc.sate.service.factory;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.service.evaluacion.EvaluadorAlerta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlertaFactoryTest {

    private final AlertaFactory alertaFactory =
            new AlertaFactory();

    @Test
    void debeCrearAlertaRoja() {

        Estudiante estudiante = mock(Estudiante.class);

        when(estudiante.getId()).thenReturn(1L);
        when(estudiante.getNombre()).thenReturn("Estudiante Prueba");

        EvaluadorAlerta.ResultadoEvaluacion resultado =
                new EvaluadorAlerta.ResultadoEvaluacion(
                        "ROJA",
                        "Inasistencia superior al 15%"
                );

        Alerta alerta =
                alertaFactory.crearAlerta(estudiante, resultado);

        assertNotNull(alerta);
        assertEquals(1L, alerta.getEstudianteId());
        assertEquals("Estudiante Prueba", alerta.getEstudianteNombre());
        assertEquals("ROJA", alerta.getTipo());
        assertEquals(
                "Inasistencia superior al 15%",
                alerta.getMotivo()
        );
        assertNotNull(alerta.getFecha());
        assertFalse(alerta.isAtendida());
    }

    @Test
    void debeCrearAlertaAmarilla() {

        Estudiante estudiante = mock(Estudiante.class);

        when(estudiante.getId()).thenReturn(2L);
        when(estudiante.getNombre()).thenReturn("Estudiante Prueba 2");

        EvaluadorAlerta.ResultadoEvaluacion resultado =
                new EvaluadorAlerta.ResultadoEvaluacion(
                        "AMARILLA",
                        "Promedio inferior a 3.0"
                );

        Alerta alerta =
                alertaFactory.crearAlerta(estudiante, resultado);

        assertNotNull(alerta);
        assertEquals(2L, alerta.getEstudianteId());
        assertEquals("Estudiante Prueba 2", alerta.getEstudianteNombre());
        assertEquals("AMARILLA", alerta.getTipo());
        assertEquals(
                "Promedio inferior a 3.0",
                alerta.getMotivo()
        );
        assertNotNull(alerta.getFecha());
        assertFalse(alerta.isAtendida());
    }
}