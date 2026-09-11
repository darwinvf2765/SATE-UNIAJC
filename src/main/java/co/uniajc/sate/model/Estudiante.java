package co.uniajc.sate.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String documento;

    @NotBlank
    private String nombre;

    @NotBlank
    private String programa;

    @NotBlank
    private String cohorte;

    @Min(0)
    @Max(100)
    private double porcentajeInasistencia;

    @Min(0)
    @Max(5)
    private double promedio;

    private String estadoAlerta;

    public Estudiante() {}

    public Estudiante(String documento, String nombre, String programa, String cohorte,
                       double porcentajeInasistencia, double promedio, String estadoAlerta) {
        this.documento = documento;
        this.nombre = nombre;
        this.programa = programa;
        this.cohorte = cohorte;
        this.porcentajeInasistencia = porcentajeInasistencia;
        this.promedio = promedio;
        this.estadoAlerta = estadoAlerta;
    }

    public Long getId() { return id; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }
    public String getCohorte() { return cohorte; }
    public void setCohorte(String cohorte) { this.cohorte = cohorte; }
    public double getPorcentajeInasistencia() { return porcentajeInasistencia; }
    public void setPorcentajeInasistencia(double porcentajeInasistencia) { this.porcentajeInasistencia = porcentajeInasistencia; }
    public double getPromedio() { return promedio; }
    public void setPromedio(double promedio) { this.promedio = promedio; }
    public String getEstadoAlerta() { return estadoAlerta; }
    public void setEstadoAlerta(String estadoAlerta) { this.estadoAlerta = estadoAlerta; }
}
