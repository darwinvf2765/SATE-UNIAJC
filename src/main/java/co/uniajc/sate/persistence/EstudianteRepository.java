package co.uniajc.sate.persistence;

import co.uniajc.sate.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    List<Estudiante> findByCohorte(String cohorte);
}
