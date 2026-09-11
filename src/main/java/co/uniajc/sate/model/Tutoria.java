package co.uniajc.sate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tutorias")
public class Tutoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long estudianteId;
    private String estudianteNombre;

    @NotBlank
    private String docente;

    private String tipo;
    private LocalDateTime fecha;
    private String estado;

    public Tutoria() {}

    public Tutoria(Long estudianteId, String estudianteNombre, String docente,
                    String tipo, LocalDateTime fecha, String estado) {
        this.estudianteId = estudianteId;
        this.estudianteNombre = estudianteNombre;
        this.docente = docente;
        this.tipo = tipo;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    public String getEstudianteNombre() { return estudianteNombre; }
    public void setEstudianteNombre(String estudianteNombre) { this.estudianteNombre = estudianteNombre; }
    public String getDocente() { return docente; }
    public void setDocente(String docente) { this.docente = docente; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
