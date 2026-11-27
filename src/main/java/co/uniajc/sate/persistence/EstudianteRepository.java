package co.uniajc.sate.persistence;

import co.uniajc.sate.model.Estudiante;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepository {

    List<Estudiante> findAll();

    Optional<Estudiante> findById(Long id);

    Estudiante save(Estudiante estudiante);

    List<Estudiante> findByCohorte(String cohorte);
}
