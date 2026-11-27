package co.uniajc.sate.controller;

import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public List<Estudiante> listar() {
        return estudianteService.listar();
    }

    @GetMapping("/{id}")
    public Estudiante buscar(@PathVariable Long id) {
        return estudianteService.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estudiante crear(@Valid @RequestBody Estudiante estudiante) {
        return estudianteService.crear(estudiante);
    }

    @PutMapping("/{id}/registro")
    public Estudiante actualizarRegistro(
            @PathVariable Long id,
            @Valid @RequestBody RegistroRequest request) {

        return estudianteService.actualizarRegistro(
                id,
                request.porcentajeInasistencia(),
                request.promedio());
    }

    public record RegistroRequest(
            @jakarta.validation.constraints.DecimalMin("0.0")
            @jakarta.validation.constraints.DecimalMax("100.0")
            double porcentajeInasistencia,

            @jakarta.validation.constraints.DecimalMin("0.0")
            @jakarta.validation.constraints.DecimalMax("5.0")
            double promedio
    ) {}
}
