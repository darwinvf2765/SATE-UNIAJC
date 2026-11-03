
package co.uniajc.sate.service.evaluacion;

import co.uniajc.sate.model.Estudiante;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EvaluadorPromedio implements EvaluadorAlerta {

    private static final double LIMITE_PROMEDIO = 3.0;

    @Override
    public Optional<ResultadoEvaluacion> evaluar(Estudiante estudiante) {

        if (estudiante == null) {
            throw new IllegalArgumentException(
                    "El estudiante no puede ser nulo"
            );
        }

        double promedio = estudiante.getPromedio();

        if (!Double.isFinite(promedio) || promedio < 0 || promedio > 5) {
            throw new IllegalArgumentException(
                    "El promedio debe estar entre 0 y 5"
            );
        }

        if (promedio < LIMITE_PROMEDIO) {
            return Optional.of(new ResultadoEvaluacion(
                    "AMARILLA",
                    "Promedio inferior a 3.0"
            ));
        }

        return Optional.empty();
    }
}
