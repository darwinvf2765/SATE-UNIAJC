
package co.uniajc.sate.service.evaluacion;

import co.uniajc.sate.model.Estudiante;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EvaluadorInasistencia implements EvaluadorAlerta {

    private static final double LIMITE_INASISTENCIA = 15.0;

    @Override
    public Optional<ResultadoEvaluacion> evaluar(Estudiante estudiante) {

        if (estudiante == null) {
            throw new IllegalArgumentException(
                    "El estudiante no puede ser nulo"
            );
        }

        double porcentaje = estudiante.getPorcentajeInasistencia();

        if (!Double.isFinite(porcentaje) || porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException(
                    "El porcentaje de inasistencia debe estar entre 0 y 100"
            );
        }

        if (porcentaje > LIMITE_INASISTENCIA) {
            return Optional.of(new ResultadoEvaluacion(
                    "ROJA",
                    "Inasistencia superior al 15%"
            ));
        }

        return Optional.empty();
    }
}
