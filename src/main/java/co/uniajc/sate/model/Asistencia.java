package co.uniajc.sate.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asistencia_estudiante")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private boolean presente;

    public Asistencia() {
    }

    public Asistencia(
            Estudiante estudiante,
            Materia materia,
            LocalDate fecha,
            boolean presente) {

        this.estudiante = estudiante;
        this.materia = materia;
        this.fecha = fecha;
        this.presente = presente;
    }

    public Long getId() {
        return id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
}