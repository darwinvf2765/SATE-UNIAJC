package co.uniajc.sate.service.factory;

import co.uniajc.sate.model.Alerta;
import co.uniajc.sate.model.Estudiante;
import co.uniajc.sate.service.evaluacion.EvaluadorAlerta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AlertaFactory {

    public Alerta crearAlerta(
            Estudiante estudiante,
            EvaluadorAlerta.ResultadoEvaluacion resultado
    ) {

        return new Alerta(
                estudiante.getId(),
                estudiante.getNombre(),
                resultado.tipo(),
                resultado.motivo(),
                LocalDateTime.now(),
                false
        );
    }
}