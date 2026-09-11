package co.uniajc.sate.controller;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.repository.AlertaRepository;
import co.uniajc.sate.service.AlertaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@CrossOrigin
public class AlertaController {
    private final AlertaRepository repository;
    private final AlertaService service;

    public AlertaController(AlertaRepository repository, AlertaService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public List<Alerta> listar() {
        return repository.findAll();
    }

    @GetMapping("/pendientes")
    public List<Alerta> pendientes() {
        return repository.findByAtendidaFalse();
    }

    @PostMapping("/evaluar")
    public List<Alerta> evaluar() {
        return service.evaluarTodos();
    }

    @PutMapping("/{id}/atendida")
    public Alerta marcarAtendida(@PathVariable Long id) {
        Alerta a = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        a.setAtendida(true);
        return repository.save(a);
    }
}
