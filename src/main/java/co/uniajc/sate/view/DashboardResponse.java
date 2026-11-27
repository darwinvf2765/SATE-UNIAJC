package co.uniajc.sate.view;

import java.util.Map;

public record DashboardResponse(
        int totalEstudiantes,
        long alertasRojas,
        long alertasAmarillas,
        long estudiantesVerdes,
        double proyeccionDesercion,
        int totalTutorias,
        long tutoriasRealizadas,
        double efectividadTutorias,
        Map<String, Long> estudiantesPorCohorte
) {}
