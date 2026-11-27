package co.uniajc.sate.service;

import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.persistence.EstudianteRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository repository;

    @Mock
    private AlertaService alertaService;

    @InjectMocks
    private EstudianteService estudianteService;

    @Test
    void debeListarTodosLosEstudiantes() {
        Estudiante estudiante = new Estudiante();

        when(repository.findAll())
                .thenReturn(List.of(estudiante));

        List<Estudiante> resultado = estudianteService.listar();

        assertEquals(1, resultado.size());
        assertSame(estudiante, resultado.get(0));

        verify(repository).findAll();
    }

    @Test
    void debeBuscarEstudianteExistente() {
        Estudiante estudiante = new Estudiante();

        when(repository.findById(1L))
                .thenReturn(Optional.of(estudiante));

        Estudiante resultado = estudianteService.buscar(1L);

        assertSame(estudiante, resultado);

        verify(repository).findById(1L);
    }

    @Test
    void debeLanzarExcepcionSiEstudianteNoExiste() {
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(
                RuntimeException.class,
                () -> estudianteService.buscar(99L)
        );

        assertEquals(
                "Estudiante no encontrado",
                excepcion.getMessage()
        );

        verify(repository).findById(99L);
    }

    @Test
    void debeCrearEstudiante() {
        Estudiante estudiante = new Estudiante();

        when(repository.save(estudiante))
                .thenReturn(estudiante);

        Estudiante resultado = estudianteService.crear(estudiante);

        assertSame(estudiante, resultado);

        verify(repository).save(estudiante);
    }

    @Test
    void debeActualizarRegistroAcademico() {
        Estudiante estudiante = new Estudiante();

        when(repository.findById(1L))
                .thenReturn(Optional.of(estudiante));

        when(repository.save(estudiante))
                .thenReturn(estudiante);

        Estudiante resultado = estudianteService.actualizarRegistro(
                1L,
                20.0,
                2.8
        );

        assertSame(estudiante, resultado);

        assertEquals(
                20.0,
                resultado.getPorcentajeInasistencia(),
                0.001
        );

        assertEquals(
                2.8,
                resultado.getPromedio(),
                0.001
        );

        verify(repository).findById(1L);
        verify(repository, times(2)).save(estudiante);
        verify(alertaService).evaluarEstudiante(estudiante);
    }

    @Test
    void debeEvaluarAlertasAlCrearEstudiante() {
        Estudiante estudiante = new Estudiante();

        estudiante.setPorcentajeInasistencia(20.0);
        estudiante.setPromedio(2.8);

        when(repository.save(estudiante))
                .thenReturn(estudiante);

        estudianteService.crear(estudiante);

        verify(alertaService, times(1))
                .evaluarEstudiante(estudiante);
    }
}
