package co.uniajc.sate.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long estudianteId;
    private String estudianteNombre;
    private String tipo;
    private String motivo;
    private LocalDateTime fecha;
    private boolean atendida;

    public Alerta() {}

    public Alerta(Long estudianteId, String estudianteNombre, String tipo,
                   String motivo, LocalDateTime fecha, boolean atendida) {
        this.estudianteId = estudianteId;
        this.estudianteNombre = estudianteNombre;
        this.tipo = tipo;
        this.motivo = motivo;
        this.fecha = fecha;
        this.atendida = atendida;
    }

    public Long getId() { return id; }
    public Long getEstudianteId() { return estudianteId; }
    public String getEstudianteNombre() { return estudianteNombre; }
    public String getTipo() { return tipo; }
    public String getMotivo() { return motivo; }
    public LocalDateTime getFecha() { return fecha; }
    public boolean isAtendida() { return atendida; }
    public void setAtendida(boolean atendida) { this.atendida = atendida; }
}
