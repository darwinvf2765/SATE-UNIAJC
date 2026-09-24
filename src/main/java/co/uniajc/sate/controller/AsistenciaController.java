package co.uniajc.sate.controller;

import co.uniajc.sate.model.Asistencia;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.model.Materia;
import co.uniajc.sate.repository.EstudianteRepository;
import co.uniajc.sate.repository.MateriaRepository;
import co.uniajc.sate.service.AsistenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin
public class AsistenciaController {

    private final AsistenciaService asistenciaService;
    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    public AsistenciaController(
            AsistenciaService asistenciaService,
            EstudianteRepository estudianteRepository,
            MateriaRepository materiaRepository) {

        this.asistenciaService = asistenciaService;
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
    }

    @PostMapping
    public Asistencia registrar(@RequestBody AsistenciaRequest request) {

        Estudiante estudiante = estudianteRepository.findById(request.estudianteId())
                .orElseThrow(() ->
                        new RuntimeException("Estudiante no encontrado"));

        Materia materia = materiaRepository.findById(request.materiaId())
                .orElseThrow(() ->
                        new RuntimeException("Materia no encontrada"));

        Asistencia asistencia = new Asistencia(
                estudiante,
                materia,
                request.fecha(),
                request.presente()
        );

        return asistenciaService.registrar(asistencia);
    }

    @GetMapping("/estudiante/{id}")
    public List<Asistencia> listarPorEstudiante(@PathVariable Long id) {

        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Estudiante no encontrado"));

        return asistenciaService
                .getAsistenciasPorEstudiante(estudiante);
    }

    public record AsistenciaRequest(
            Long estudianteId,
            Long materiaId,
            java.time.LocalDate fecha,
            boolean presente
    ) {
    }
}