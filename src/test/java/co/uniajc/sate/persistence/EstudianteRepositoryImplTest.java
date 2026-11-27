package co.uniajc.sate.persistence;

import co.uniajc.sate.model.Estudiante;
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
class EstudianteRepositoryImplTest {

    @Mock
    private EstudianteJpaRepository jpaRepository;

    @InjectMocks
    private EstudianteRepositoryImpl repository;

    @Test
    void debeListarTodosLosEstudiantes() {
        Estudiante estudiante = crearEstudiante();

        when(jpaRepository.findAll())
                .thenReturn(List.of(estudiante));

        List<Estudiante> resultado = repository.findAll();

        assertEquals(1, resultado.size());
        assertEquals(estudiante, resultado.get(0));

        verify(jpaRepository).findAll();
    }

    @Test
    void debeBuscarEstudiantePorId() {
        Estudiante estudiante = crearEstudiante();

        when(jpaRepository.findById(1L))
                .thenReturn(Optional.of(estudiante));

        Optional<Estudiante> resultado = repository.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(estudiante, resultado.get());

        verify(jpaRepository).findById(1L);
    }

    @Test
    void debeRetornarVacioCuandoEstudianteNoExiste() {
        when(jpaRepository.findById(99L))
                .thenReturn(Optional.empty());

        Optional<Estudiante> resultado = repository.findById(99L);

        assertTrue(resultado.isEmpty());

        verify(jpaRepository).findById(99L);
    }

    @Test
    void debeGuardarEstudiante() {
        Estudiante estudiante = crearEstudiante();

        when(jpaRepository.save(estudiante))
                .thenReturn(estudiante);

        Estudiante resultado = repository.save(estudiante);

        assertEquals(estudiante, resultado);

        verify(jpaRepository).save(estudiante);
    }

    @Test
    void debeBuscarEstudiantesPorCohorte() {
        Estudiante estudiante = crearEstudiante();

        when(jpaRepository.findByCohorte("2026-1"))
                .thenReturn(List.of(estudiante));

        List<Estudiante> resultado =
                repository.findByCohorte("2026-1");

        assertEquals(1, resultado.size());
        assertEquals(estudiante, resultado.get(0));

        verify(jpaRepository).findByCohorte("2026-1");
    }

    private Estudiante crearEstudiante() {
        return new Estudiante(
                "123456789",
                "Estudiante Prueba",
                "Ingenieria de Software",
                "2026-1",
                10.0,
                4.2,
                null
        );
    }
}
