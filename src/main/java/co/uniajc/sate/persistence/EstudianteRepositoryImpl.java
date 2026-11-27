package co.uniajc.sate.persistence;

import co.uniajc.sate.model.Estudiante;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EstudianteRepositoryImpl
        implements EstudianteRepository {

    private final EstudianteJpaRepository jpaRepository;

    public EstudianteRepositoryImpl(
            EstudianteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Estudiante> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Estudiante> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Estudiante save(Estudiante estudiante) {
        return jpaRepository.save(estudiante);
    }

    @Override
    public List<Estudiante> findByCohorte(String cohorte) {
        return jpaRepository.findByCohorte(cohorte);
    }
}
