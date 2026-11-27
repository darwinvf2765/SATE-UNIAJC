package co.uniajc.sate.service;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.model.Tutoria;
import co.uniajc.sate.persistence.AlertaRepository;
import co.uniajc.sate.persistence.EstudianteRepository;
import co.uniajc.sate.persistence.TutoriaRepository;
import co.uniajc.sate.view.DashboardResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {

    private final EstudianteRepository estudianteRepository;
    private final TutoriaRepository tutoriaRepository;
    private final AlertaRepository alertaRepository;

    public DashboardService(
            EstudianteRepository estudianteRepository,
            TutoriaRepository tutoriaRepository,
            AlertaRepository alertaRepository) {

        this.estudianteRepository = estudianteRepository;
        this.tutoriaRepository = tutoriaRepository;
        this.alertaRepository = alertaRepository;
    }

    public DashboardResponse resumen() {

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

        // Contar estudiantes unicos con alertas pendientes.
        Set<Long> estudiantesConAlerta = new HashSet<>();

        for (Alerta alerta : alertasPendientes) {
            estudiantesConAlerta.add(alerta.getEstudianteId());
        }

        long verdes = estudiantes.stream()
                .filter(e -> "VERDE".equals(e.getEstadoAlerta()))
                .count();

        // Calcular proyeccion sin duplicar estudiantes.
        double proyeccion = estudiantes.isEmpty() ? 0 :
                ((estudiantesConAlerta.size() * 0.80)
                        + (verdes * 0.10))
                        / estudiantes.size() * 100;

        long realizadas = tutorias.stream()
                .filter(t -> "REALIZADA".equalsIgnoreCase(t.getEstado()))
                .count();

        double efectividad = tutorias.isEmpty() ? 0 :
                ((double) realizadas / tutorias.size()) * 100;

        Map<String, Long> porCohorte = new TreeMap<>();

        for (Estudiante e : estudiantes) {
            porCohorte.merge(e.getCohorte(), 1L, Long::sum);
        }

        return new DashboardResponse(
                estudiantes.size(),
                rojas,
                amarillas,
                verdes,
                Math.round(proyeccion * 10.0) / 10.0,
                tutorias.size(),
                realizadas,
                Math.round(efectividad * 10.0) / 10.0,
                porCohorte
        );
    }
}
