package co.uniajc.sate.service.notificacion;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.persistence.AlertaRepository;
import co.uniajc.sate.service.evaluacion.EvaluadorAlerta;
import co.uniajc.sate.service.factory.AlertaFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificacionSistemaService implements NotificacionService {

    private final AlertaRepository alertaRepository;
    private final AlertaFactory alertaFactory;

    public NotificacionSistemaService(
            AlertaRepository alertaRepository,
            AlertaFactory alertaFactory
    ) {
        this.alertaRepository = alertaRepository;
        this.alertaFactory = alertaFactory;
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

        Alerta alerta = alertaFactory.crearAlerta(
                estudiante,
                resultado
        );

        alertaRepository.save(alerta);
    }
}