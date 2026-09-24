package co.uniajc.sate.repository;

import co.uniajc.sate.model.Asistencia;
import co.uniajc.sate.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    List<Asistencia> findByEstudiante(Estudiante estudiante);
}