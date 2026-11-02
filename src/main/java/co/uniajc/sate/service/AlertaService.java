
package co.uniajc.sate.service;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.repository.AlertaRepository;
import co.uniajc.sate.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertaService {

    private final EstudianteRepository estudianteRepository;
    private final AlertaRepository alertaRepository;

    public AlertaService(
            EstudianteRepository estudianteRepository,
            AlertaRepository alertaRepository) {
        this.estudianteRepository = estudianteRepository;
        this.alertaRepository = alertaRepository;
    }

    public String evaluarEstudiante(Estudiante e) {
        String estado;
        String motivo;

        if (e.getPorcentajeInasistencia() > 15) {
            estado = "ROJA";
            motivo = "Inasistencia superior al 15%";
        } else if (e.getPromedio() < 3.0) {
            estado = "AMARILLA";
            motivo = "Promedio inferior a 3.0";
        } else {
            estado = "VERDE";
            motivo = "Sin condición de alerta";
        }

        e.setEstadoAlerta(estado);
        estudianteRepository.save(e);

        if (!"VERDE".equals(estado)) {
            List<Alerta> existentes =
                    alertaRepository.findByEstudianteId(e.getId());

            boolean yaExiste = existentes.stream()
                    .anyMatch(a -> estado.equals(a.getTipo())
                            && motivo.equals(a.getMotivo())
                            && !a.isAtendida());

            if (!yaExiste) {
                alertaRepository.save(new Alerta(
                        e.getId(),
                        e.getNombre(),
                        estado,
                        motivo,
                        LocalDateTime.now(),
                        false
                ));
            }
        }

        return estado;
    }

    public List<Alerta> evaluarTodos() {
        List<Alerta> nuevas = new ArrayList<>();

        for (Estudiante e : estudianteRepository.findAll()) {
            evaluarEstudiante(e);
        }

        nuevas.addAll(alertaRepository.findAll());
        return nuevas;
    }
}
