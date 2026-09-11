const api = '/api';

document.querySelectorAll('.tab').forEach(btn => {
    btn.addEventListener('click', () => {
        document.querySelectorAll('.tab').forEach(b => b.classList.remove('active'));
        document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
        btn.classList.add('active');
        document.getElementById(btn.dataset.section).classList.add('active');
    });
});

async function getJson(url, options) {
    const response = await fetch(url, options);
    if (!response.ok) throw new Error(await response.text());
    return response.json();
}

async function cargarTodo() {
    try {
        const [dashboard, estudiantes, alertas, tutorias] = await Promise.all([
            getJson(api + '/dashboard'),
            getJson(api + '/estudiantes'),
            getJson(api + '/alertas'),
            getJson(api + '/tutorias')
        ]);
        pintarDashboard(dashboard);
        pintarEstudiantes(estudiantes);
        pintarAlertas(alertas);
        pintarTutorias(tutorias);
        llenarSelects(estudiantes);
    } catch (e) {
        alert('No se pudo cargar la información. Verifique que Spring Boot esté ejecutándose.');
        console.error(e);
    }
}

function pintarDashboard(d) {
    totalEstudiantes.textContent = d.totalEstudiantes;
    alertasRojas.textContent = d.alertasRojas;
    alertasAmarillas.textContent = d.alertasAmarillas;
    proyeccion.textContent = d.proyeccionDesercion + '%';
    totalTutorias.textContent = d.totalTutorias;
    efectividad.textContent = d.efectividadTutorias + '%';

    cohortes.innerHTML = Object.entries(d.estudiantesPorCohorte).map(([c, n]) =>
        `<div><b>${c}</b><span style="float:right">${n}</span>
         <div class="bar"><i style="width:${Math.min(n*25,100)}%"></i></div></div><br>`
    ).join('');
}

function llenarSelects(estudiantes) {
    const html = estudiantes.map(e => `<option value="${e.id}">${e.nombre} — ${e.cohorte}</option>`).join('');
    estudianteId.innerHTML = html;
    tutoriaEstudiante.innerHTML = html;
}

function pintarEstudiantes(lista) {
    tablaEstudiantes.innerHTML = lista.map(e => `
        <tr>
            <td>${e.nombre}</td><td>${e.cohorte}</td>
            <td>${e.porcentajeInasistencia}%</td><td>${e.promedio.toFixed(1)}</td>
            <td class="status ${e.estadoAlerta}">${e.estadoAlerta}</td>
        </tr>`).join('');
}

function pintarAlertas(lista) {
    tablaAlertas.innerHTML = lista.sort((a,b)=>b.id-a.id).map(a => `
        <tr>
            <td>${a.estudianteNombre}</td>
            <td class="status ${a.tipo}">${a.tipo}</td>
            <td>${a.motivo}</td>
            <td>${formatearFecha(a.fecha)}</td>
            <td>${a.atendida ? '<span>Atendida</span>' : `<button class="action" onclick="atenderAlerta(${a.id})">Marcar atendida</button>`}</td>
        </tr>`).join('');
}

function pintarTutorias(lista) {
    tablaTutorias.innerHTML = lista.map(t => `
        <tr>
            <td>${t.estudianteNombre || '—'}</td><td>${t.docente}</td><td>${t.tipo}</td>
            <td>${formatearFecha(t.fecha)}</td>
            <td>${t.estado}</td>
            <td>
                ${t.estado !== 'REALIZADA' ? `<button class="action" onclick="cambiarEstado(${t.id},'REALIZADA')">Realizada</button>` : '✓'}
            </td>
        </tr>`).join('');
}

function formatearFecha(f) {
    if (!f) return '—';
    return new Date(f).toLocaleString('es-CO', {dateStyle:'short', timeStyle:'short'});
}

registroForm.addEventListener('submit', async e => {
    e.preventDefault();
    try {
        await getJson(`${api}/estudiantes/${estudianteId.value}/registro`, {
            method:'PUT',
            headers:{'Content-Type':'application/json'},
            body:JSON.stringify({
                porcentajeInasistencia:Number(inasistencia.value),
                promedio:Number(promedio.value)
            })
        });
        alert('Registro guardado y reglas evaluadas correctamente.');
        await cargarTodo();
    } catch(err) { alert('Error al guardar el registro.'); console.error(err); }
});

tutoriaForm.addEventListener('submit', async e => {
    e.preventDefault();
    try {
        await getJson(api + '/tutorias', {
            method:'POST',
            headers:{'Content-Type':'application/json'},
            body:JSON.stringify({
                estudianteId:Number(tutoriaEstudiante.value),
                docente:docente.value,
                tipo:tipo.value,
                fecha:fecha.value,
                estado:'AGENDADA'
            })
        });
        alert('Tutoría agendada correctamente.');
        tutoriaForm.reset();
        await cargarTodo();
    } catch(err) { alert('Error al agendar la tutoría.'); console.error(err); }
});

async function atenderAlerta(id) {
    await getJson(`${api}/alertas/${id}/atendida`, {method:'PUT'});
    await cargarTodo();
}

async function evaluarAlertas() {
    await getJson(api + '/alertas/evaluar', {method:'POST'});
    alert('Motor de reglas ejecutado.');
    await cargarTodo();
}

async function cambiarEstado(id, estado) {
    await getJson(`${api}/tutorias/${id}/estado`, {
        method:'PUT',
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify({estado})
    });
    await cargarTodo();
}

cargarTodo();
