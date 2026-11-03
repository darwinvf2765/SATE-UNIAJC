
package co.uniajc.sate.service.notificacion;

import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.service.evaluacion.EvaluadorAlerta;

public interface NotificacionService {

    void notificar(
            Estudiante estudiante,
            EvaluadorAlerta.ResultadoEvaluacion resultado
    );
}
