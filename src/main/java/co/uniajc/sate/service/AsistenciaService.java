package co.uniajc.sate.service;

import co.uniajc.sate.model.Asistencia;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.repository.AsistenciaRepository;
import co.uniajc.sate.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final EstudianteRepository estudianteRepository;
    private final AlertaService alertaService;

    public AsistenciaService(
            AsistenciaRepository asistenciaRepository,
            EstudianteRepository estudianteRepository,
            AlertaService alertaService) {

        this.asistenciaRepository = asistenciaRepository;
        this.estudianteRepository = estudianteRepository;
        this.alertaService = alertaService;
    }

    /**
     * Registra una asistencia y actualiza el porcentaje
     * de inasistencia del estudiante.
     */
    public Asistencia registrar(Asistencia asistencia) {

        if (asistencia.getEstudiante() == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }

        if (asistencia.getFecha() == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        Asistencia guardada = asistenciaRepository.save(asistencia);

        actualizarPorcentajeInasistencia(asistencia.getEstudiante());

        return guardada;
    }

    /**
     * Calcula el porcentaje de inasistencia de un estudiante.
     */
    public double calcularPorcentajeInasistencia(Estudiante estudiante) {

        List<Asistencia> asistencias =
                asistenciaRepository.findByEstudiante(estudiante);

        if (asistencias.isEmpty()) {
            return 0.0;
        }

        long ausencias = asistencias.stream()
                .filter(asistencia -> !asistencia.isPresente())
                .count();

        return (ausencias * 100.0) / asistencias.size();
    }

    /**
     * Obtiene todas las asistencias de un estudiante.
     */
    public List<Asistencia> getAsistenciasPorEstudiante(Estudiante estudiante) {
        return asistenciaRepository.findByEstudiante(estudiante);
    }

    /**
     * Actualiza el porcentaje de inasistencia y
     * evalúa nuevamente las condiciones de alerta.
     */
    private void actualizarPorcentajeInasistencia(Estudiante estudiante) {

        double porcentaje = calcularPorcentajeInasistencia(estudiante);

        estudiante.setPorcentajeInasistencia(porcentaje);

        estudianteRepository.save(estudiante);

        alertaService.evaluarEstudiante(estudiante);
    }
}