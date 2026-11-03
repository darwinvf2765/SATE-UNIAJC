
package co.uniajc.sate.service;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.repository.AlertaRepository;
import co.uniajc.sate.repository.EstudianteRepository;
import co.uniajc.sate.service.evaluacion.EvaluadorAlerta;
import co.uniajc.sate.service.notificacion.NotificacionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertaService {

    private final EstudianteRepository estudianteRepository;
    private final AlertaRepository alertaRepository;
    private final List<EvaluadorAlerta> evaluadores;
    private final NotificacionService notificacionService;

    public AlertaService(
            EstudianteRepository estudianteRepository,
            AlertaRepository alertaRepository,
            List<EvaluadorAlerta> evaluadores,
            NotificacionService notificacionService
    ) {
        this.estudianteRepository = estudianteRepository;
        this.alertaRepository = alertaRepository;
        this.evaluadores = evaluadores;
        this.notificacionService = notificacionService;
    }

    public String evaluarEstudiante(Estudiante estudiante) {

        List<EvaluadorAlerta.ResultadoEvaluacion> resultados =
                new ArrayList<>();

        for (EvaluadorAlerta evaluador : evaluadores) {
            evaluador.evaluar(estudiante).ifPresent(resultados::add);
        }

        for (EvaluadorAlerta.ResultadoEvaluacion resultado : resultados) {
            notificacionService.notificar(estudiante, resultado);
        }

        String estado;

        if (resultados.stream().anyMatch(r -> "ROJA".equals(r.tipo()))) {
            estado = "ROJA";
        } else if (!resultados.isEmpty()) {
            estado = "AMARILLA";
        } else {
            estado = "VERDE";
        }

        estudiante.setEstadoAlerta(estado);
        estudianteRepository.save(estudiante);

        return estado;
    }

    public List<Alerta> evaluarTodos() {
        for (Estudiante estudiante : estudianteRepository.findAll()) {
            evaluarEstudiante(estudiante);
        }

        return alertaRepository.findAll();
    }
}
