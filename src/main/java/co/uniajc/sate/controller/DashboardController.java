
package co.uniajc.sate.controller;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.model.Tutoria;
import co.uniajc.sate.repository.AlertaRepository;
import co.uniajc.sate.repository.EstudianteRepository;
import co.uniajc.sate.repository.TutoriaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    private final EstudianteRepository estudianteRepository;
    private final TutoriaRepository tutoriaRepository;
    private final AlertaRepository alertaRepository;

    public DashboardController(
            EstudianteRepository estudianteRepository,
            TutoriaRepository tutoriaRepository,
            AlertaRepository alertaRepository
    ) {
        this.estudianteRepository = estudianteRepository;
        this.tutoriaRepository = tutoriaRepository;
        this.alertaRepository = alertaRepository;
    }

    @GetMapping
    public Map<String, Object> resumen() {

        List<Estudiante> estudiantes = estudianteRepository.findAll();
        List<Tutoria> tutorias = tutoriaRepository.findAll();

        // Obtener alertas pendientes.
        List<Alerta> alertasPendientes =
                alertaRepository.findByAtendidaFalse();

        // Contar alertas por tipo.
        long rojas = alertasPendientes.stream()
                .filter(a -> "ROJA".equalsIgnoreCase(a.getTipo()))
                .count();

        long amarillas = alertasPendientes.stream()
                .filter(a -> "AMARILLA".equalsIgnoreCase(a.getTipo()))
                .count();

        // Contar estudiantes únicos con alertas pendientes.
        Set<Long> estudiantesConAlerta = new HashSet<>();

        for (Alerta alerta : alertasPendientes) {
            estudiantesConAlerta.add(alerta.getEstudianteId());
        }

        long verdes = estudiantes.stream()
                .filter(e -> "VERDE".equals(e.getEstadoAlerta()))
                .count();

        // Calcular proyección sin duplicar estudiantes con dos alertas.
        double proyeccion = estudiantes.isEmpty() ? 0 :
                ((estudiantesConAlerta.size() * 0.80)
                        + (verdes * 0.10))
                        / estudiantes.size() * 100;

        long realizadas = tutorias.stream()
                .filter(t -> "REALIZADA".equalsIgnoreCase(t.getEstado()))
                .count();

        double efectividad = tutorias.isEmpty() ? 0 :
                ((double) realizadas / tutorias.size()) * 100;

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("totalEstudiantes", estudiantes.size());
        result.put("alertasRojas", rojas);
        result.put("alertasAmarillas", amarillas);
        result.put("estudiantesVerdes", verdes);
        result.put("proyeccionDesercion",
                Math.round(proyeccion * 10.0) / 10.0);
        result.put("totalTutorias", tutorias.size());
        result.put("tutoriasRealizadas", realizadas);
        result.put("efectividadTutorias",
                Math.round(efectividad * 10.0) / 10.0);

        Map<String, Long> porCohorte = new TreeMap<>();

        for (Estudiante e : estudiantes) {
            porCohorte.merge(e.getCohorte(), 1L, Long::sum);
        }

        result.put("estudiantesPorCohorte", porCohorte);

        return result;
    }
}
