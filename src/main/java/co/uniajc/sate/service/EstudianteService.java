package co.uniajc.sate.service;

import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.persistence.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository repository;
    private final AlertaService alertaService;

    public EstudianteService(
            EstudianteRepository repository,
            AlertaService alertaService) {
        this.repository = repository;
        this.alertaService = alertaService;
    }

    public List<Estudiante> listar() {
        return repository.findAll();
    }

    public Estudiante buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Estudiante no encontrado"));
    }

    public Estudiante crear(Estudiante estudiante) {
        Estudiante guardado = repository.save(estudiante);
        alertaService.evaluarEstudiante(guardado);
        return guardado;
    }

    public Estudiante actualizarRegistro(
            Long id,
            double porcentajeInasistencia,
            double promedio) {

        Estudiante estudiante = buscar(id);

        estudiante.setPorcentajeInasistencia(porcentajeInasistencia);
        estudiante.setPromedio(promedio);

        Estudiante guardado = repository.save(estudiante);

        alertaService.evaluarEstudiante(guardado);

        return repository.save(guardado);
    }
}
