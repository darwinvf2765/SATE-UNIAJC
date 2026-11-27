package co.uniajc.sate.persistence;

import co.uniajc.sate.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
}