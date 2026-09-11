package co.uniajc.sate.controller;

import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.repository.EstudianteRepository;
import co.uniajc.sate.service.AlertaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin
public class EstudianteController {
    private final EstudianteRepository repository;
    private final AlertaService alertaService;

    public EstudianteController(EstudianteRepository repository, AlertaService alertaService) {
        this.repository = repository;
        this.alertaService = alertaService;
    }

    @GetMapping
    public List<Estudiante> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Estudiante buscar(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estudiante crear(@Valid @RequestBody Estudiante estudiante) {
        Estudiante guardado = repository.save(estudiante);
        alertaService.evaluarEstudiante(guardado);
        return guardado;
    }

    @PutMapping("/{id}/registro")
    public Estudiante actualizarRegistro(@PathVariable Long id, @RequestBody RegistroRequest request) {
        Estudiante e = buscar(id);
        e.setPorcentajeInasistencia(request.porcentajeInasistencia());
        e.setPromedio(request.promedio());
        Estudiante guardado = repository.save(e);
        alertaService.evaluarEstudiante(guardado);
        return repository.save(guardado);
    }

    public record RegistroRequest(double porcentajeInasistencia, double promedio) {}
}
