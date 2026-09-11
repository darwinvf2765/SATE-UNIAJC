CREATE TABLE IF NOT EXISTS estudiantes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    documento VARCHAR(30) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    programa VARCHAR(150) NOT NULL,
    cohorte VARCHAR(30) NOT NULL,
    porcentaje_inasistencia DOUBLE NOT NULL,
    promedio DOUBLE NOT NULL,
    estado_alerta VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS tutorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT,
    estudiante_nombre VARCHAR(150),
    docente VARCHAR(150) NOT NULL,
    tipo VARCHAR(80),
    fecha TIMESTAMP,
    estado VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS alertas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT,
    estudiante_nombre VARCHAR(150),
    tipo VARCHAR(20),
    motivo VARCHAR(255),
    fecha TIMESTAMP,
    atendida BOOLEAN
);
