package co.uniajc.sate.service;

import co.uniajc.sate.model.Asistencia;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.model.Materia;
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

    public Asistencia registrar(Asistencia asistencia) {

        if (asistencia.getEstudiante() == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }

        if (asistencia.getMateria() == null) {
            throw new IllegalArgumentException("La materia es obligatoria");
        }

        if (asistencia.getFecha() == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }

        Asistencia guardada = asistenciaRepository.save(asistencia);

        actualizarPorcentajeInasistencia(asistencia.getEstudiante());

        return guardada;
    }

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
     * Calcula la inasistencia de un estudiante en una materia específica.
     */
    public double calcularPorcentajeInasistencia(
            Estudiante estudiante,
            Materia materia) {

        if (estudiante == null || materia == null) {
            throw new IllegalArgumentException(
                    "El estudiante y la materia son obligatorios");
        }

        List<Asistencia> asistencias =
                asistenciaRepository.findByEstudianteAndMateria(
                        estudiante, materia);

        if (asistencias.isEmpty()) {
            return 0.0;
        }

        long ausencias = asistencias.stream()
                .filter(asistencia -> !asistencia.isPresente())
                .count();

        return (ausencias * 100.0) / asistencias.size();
    }

    public List<Asistencia> getAsistenciasPorEstudiante(
            Estudiante estudiante) {

        return asistenciaRepository.findByEstudiante(estudiante);
    }

    private void actualizarPorcentajeInasistencia(Estudiante estudiante) {

        double porcentaje = calcularPorcentajeInasistencia(estudiante);

        estudiante.setPorcentajeInasistencia(porcentaje);

        estudianteRepository.save(estudiante);

        alertaService.evaluarEstudiante(estudiante);
    }
}