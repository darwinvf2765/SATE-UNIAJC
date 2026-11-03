

package co.uniajc.sate.service.notificacion;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.repository.AlertaRepository;
import co.uniajc.sate.service.evaluacion.EvaluadorAlerta;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificacionSistemaService implements NotificacionService {

    private final AlertaRepository alertaRepository;

    public NotificacionSistemaService(AlertaRepository alertaRepository) {
        this.alertaRepository = alertaRepository;
    }

    @Override
    public void notificar(
            Estudiante estudiante,
            EvaluadorAlerta.ResultadoEvaluacion resultado
    ) {

        boolean alertaExistente =
                alertaRepository.existsByEstudianteIdAndTipoAndAtendidaFalse(
                        estudiante.getId(),
                        resultado.tipo()
                );

        if (alertaExistente) {
            return;
        }

        Alerta alerta = new Alerta(
                estudiante.getId(),
                estudiante.getNombre(),
                resultado.tipo(),
                resultado.motivo(),
                LocalDateTime.now(),
                false
        );

        alertaRepository.save(alerta);
    }
}

