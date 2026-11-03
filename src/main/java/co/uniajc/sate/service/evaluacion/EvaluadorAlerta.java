
package co.uniajc.sate.service.evaluacion;

import co.uniajc.sate.model.Estudiante;
import java.util.Optional;

public interface EvaluadorAlerta {

    Optional<ResultadoEvaluacion> evaluar(Estudiante estudiante);

    record ResultadoEvaluacion(
            String tipo,
            String motivo
    ) {}
}
