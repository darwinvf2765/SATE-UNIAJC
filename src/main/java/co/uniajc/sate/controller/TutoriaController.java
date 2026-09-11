package co.uniajc.sate.controller;

import co.uniajc.sate.model.Tutoria;
import co.uniajc.sate.repository.EstudianteRepository;
import co.uniajc.sate.repository.TutoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutorias")
@CrossOrigin
public class TutoriaController {
    private final TutoriaRepository repository;
    private final EstudianteRepository estudianteRepository;

    public TutoriaController(TutoriaRepository repository, EstudianteRepository estudianteRepository) {
        this.repository = repository;
        this.estudianteRepository = estudianteRepository;
    }

    @GetMapping
    public List<Tutoria> listar() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tutoria crear(@RequestBody Tutoria tutoria) {
        if (tutoria.getEstudianteId() != null) {
            estudianteRepository.findById(tutoria.getEstudianteId()).ifPresent(e ->
                    tutoria.setEstudianteNombre(e.getNombre()));
        }
        if (tutoria.getEstado() == null || tutoria.getEstado().isBlank()) {
            tutoria.setEstado("AGENDADA");
        }
        return repository.save(tutoria);
    }

    @PutMapping("/{id}/estado")
    public Tutoria cambiarEstado(@PathVariable Long id, @RequestBody EstadoRequest request) {
        Tutoria t = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoría no encontrada"));
        t.setEstado(request.estado());
        return repository.save(t);
    }

    public record EstadoRequest(String estado) {}
}
