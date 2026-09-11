INSERT INTO estudiantes (documento, nombre, programa, cohorte, porcentaje_inasistencia, promedio, estado_alerta)
VALUES
('1001001','Ana María López','Ingeniería de Sistemas','2026-1',8,4.2,'VERDE'),
('1001002','Carlos Pérez','Ingeniería de Sistemas','2026-1',18,3.6,'ROJA'),
('1001003','Laura Gómez','Ingeniería de Sistemas','2026-1',10,3.2,'AMARILLA'),
('1001004','Miguel Torres','Ingeniería de Sistemas','2026-2',7,4.0,'VERDE'),
('1001005','Sofía Rojas','Ingeniería de Sistemas','2026-2',20,2.8,'ROJA'),
('1001006','Juan David Ruiz','Ingeniería de Sistemas','2026-2',12,3.1,'AMARILLA');

INSERT INTO tutorias (estudiante_id, estudiante_nombre, docente, tipo, fecha, estado)
VALUES
(2,'Carlos Pérez','Ing. Andrea Castro','Académica','2026-09-12 15:00:00','AGENDADA'),
(3,'Laura Gómez','Ing. Juan Pérez','Académica','2026-09-11 10:00:00','REALIZADA'),
(5,'Sofía Rojas','Bienestar Estudiantil','Psicopedagógica','2026-09-13 09:00:00','AGENDADA'),
(6,'Juan David Ruiz','Ing. Andrea Castro','Académica','2026-09-10 16:00:00','REALIZADA');

INSERT INTO alertas (estudiante_id, estudiante_nombre, tipo, motivo, fecha, atendida)
VALUES
(2,'Carlos Pérez','ROJA','Inasistencia superior al 15%','2026-09-10 08:00:00',false),
(3,'Laura Gómez','AMARILLA','Promedio entre 3.0 y 3.4','2026-09-10 08:05:00',false),
(5,'Sofía Rojas','ROJA','Inasistencia superior al 15% y promedio inferior a 3.0','2026-09-10 08:10:00',false),
(6,'Juan David Ruiz','AMARILLA','Promedio entre 3.0 y 3.4','2026-09-10 08:15:00',true);
