
const api = '/api';

const loginForm = document.getElementById('loginForm');
const loginScreen = document.getElementById('loginScreen');

function mostrarAplicacion(visible) {
    document.body.classList.toggle('authenticated', visible);

    Array.from(document.body.children).forEach(elemento => {
        if (elemento.id !== 'loginScreen' && elemento.tagName !== 'SCRIPT') {
            elemento.hidden = !visible;
        }
    });

    loginScreen.style.display = visible ? 'none' : 'flex';
}

function cerrarSesion() {
    localStorage.removeItem('sateToken');
    localStorage.removeItem('sateEmail');
    localStorage.removeItem('sateRol');

    mostrarAplicacion(false);
}

loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('loginEmail').value.trim();
    const password = document.getElementById('loginPassword').value;
    const error = document.getElementById('loginError');

    error.textContent = '';

    try {
        const response = await fetch(api + '/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            throw new Error('Correo o contraseña incorrectos.');
        }

        const data = await response.json();

        if (!data.token) {
            throw new Error('El servidor no entregó un token de autenticación.');
        }

        localStorage.setItem('sateToken', data.token);
        localStorage.setItem('sateEmail', data.email);
        localStorage.setItem('sateRol', data.rol);

        mostrarAplicacion(true);
        configurarPermisos();

        await cargarTodo();

    } catch (err) {
        error.textContent = err.message;
    }
});

function obtenerRol() {
    return (localStorage.getItem('sateRol') || '')
        .toUpperCase()
        .replace(/^ROLE_/, '');
}

function configurarPermisos() {
    const esDirector = obtenerRol() === 'DIRECTOR';

    document.querySelectorAll('.tab').forEach(tab => {
        const seccion = (tab.dataset.section || '').toLowerCase();

        if (seccion.includes('dashboard') || seccion.includes('alerta')) {
            tab.hidden = !esDirector;
        } else {
            tab.hidden = false;
        }
    });

    document.querySelectorAll('.section').forEach(seccion => {
        const id = seccion.id.toLowerCase();

        if (id.includes('dashboard') || id.includes('alerta')) {
            seccion.hidden = !esDirector;
        } else {
            seccion.hidden = false;
        }
    });

    const primeraTab = Array.from(document.querySelectorAll('.tab'))
        .find(tab => !tab.hidden);

    if (primeraTab) {
        document.querySelectorAll('.tab').forEach(tab => {
            tab.classList.remove('active');
        });

        document.querySelectorAll('.section').forEach(seccion => {
            seccion.classList.remove('active');
        });

        primeraTab.classList.add('active');

        const seccionInicial = document.getElementById(
            primeraTab.dataset.section
        );

        if (seccionInicial) {
            seccionInicial.classList.add('active');
        }
    }
}

document.querySelectorAll('.tab').forEach(btn => {
    btn.addEventListener('click', () => {
        if (btn.hidden) return;

        document.querySelectorAll('.tab').forEach(b => {
            b.classList.remove('active');
        });

        document.querySelectorAll('.section').forEach(s => {
            s.classList.remove('active');
        });

        btn.classList.add('active');

        const seccion = document.getElementById(btn.dataset.section);

        if (seccion) {
            seccion.classList.add('active');
        }
    });
});

async function getJson(url, options = {}) {
    const token = localStorage.getItem('sateToken');

    const headers = {
        ...(options.headers || {})
    };

    if (token) {
        headers.Authorization = `Bearer ${token}`;
    }

    const response = await fetch(url, {
        ...options,
        headers
    });

    if (response.status === 401) {
        cerrarSesion();
        throw new Error('Tu sesión expiró. Inicia sesión nuevamente.');
    }

    if (response.status === 403) {
        throw new Error('No tienes permisos para realizar esta acción.');
    }

    if (!response.ok) {
        throw new Error(await response.text());
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
}

async function cargarTodo() {
    try {
        const [estudiantes, tutorias] = await Promise.all([
            getJson(api + '/estudiantes'),
            getJson(api + '/tutorias')
        ]);

        pintarEstudiantes(estudiantes);
        pintarTutorias(tutorias);
        llenarSelects(estudiantes);

        if (obtenerRol() === 'DIRECTOR') {
            const [dashboard, alertas] = await Promise.all([
                getJson(api + '/dashboard'),
                getJson(api + '/alertas')
            ]);

            pintarDashboard(dashboard);
            pintarAlertas(alertas);
        }

    } catch (e) {
        console.error('Error al cargar información:', e);

        if (localStorage.getItem('sateToken')) {
            alert(e.message || 'No se pudo cargar la información.');
        }
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
         <div class="bar"><i style="width:${Math.min(n * 25, 100)}%"></i></div></div><br>`
    ).join('');
}

function llenarSelects(estudiantes) {
    const html = estudiantes.map(e =>
        `<option value="${e.id}">${e.nombre} — ${e.cohorte}</option>`
    ).join('');

    estudianteId.innerHTML = html;
    tutoriaEstudiante.innerHTML = html;
}

function pintarEstudiantes(lista) {
    tablaEstudiantes.innerHTML = lista.map(e => `
        <tr>
            <td>${e.nombre}</td>
            <td>${e.cohorte}</td>
            <td>${e.porcentajeInasistencia}%</td>
            <td>${e.promedio.toFixed(1)}</td>
            <td class="status ${e.estadoAlerta}">${e.estadoAlerta}</td>
        </tr>
    `).join('');
}

function pintarAlertas(lista) {
    tablaAlertas.innerHTML = lista.sort((a, b) => b.id - a.id).map(a => `
        <tr>
            <td>${a.estudianteNombre}</td>
            <td class="status ${a.tipo}">${a.tipo}</td>
            <td>${a.motivo}</td>
            <td>${formatearFecha(a.fecha)}</td>
            <td>
                ${a.atendida
                    ? '<span>Atendida</span>'
                    : `<button class="action" onclick="atenderAlerta(${a.id})">Marcar atendida</button>`
                }
            </td>
        </tr>
    `).join('');
}

function pintarTutorias(lista) {
    tablaTutorias.innerHTML = lista.map(t => `
        <tr>
            <td>${t.estudianteNombre || '—'}</td>
            <td>${t.docente}</td>
            <td>${t.tipo}</td>
            <td>${formatearFecha(t.fecha)}</td>
            <td>${t.estado}</td>
            <td>
                ${t.estado !== 'REALIZADA'
                    ? `<button class="action" onclick="cambiarEstado(${t.id},'REALIZADA')">Realizada</button>`
                    : '✓'
                }
            </td>
        </tr>
    `).join('');
}

function formatearFecha(f) {
    if (!f) return '—';

    return new Date(f).toLocaleString('es-CO', {
        dateStyle: 'short',
        timeStyle: 'short'
    });
}

registroForm.addEventListener('submit', async e => {
    e.preventDefault();

    try {
        await getJson(`${api}/estudiantes/${estudianteId.value}/registro`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                porcentajeInasistencia: Number(inasistencia.value),
                promedio: Number(promedio.value)
            })
        });

        alert('Registro guardado y reglas evaluadas correctamente.');
        await cargarTodo();

    } catch (err) {
        alert('Error al guardar el registro: ' + err.message);
        console.error(err);
    }
});

tutoriaForm.addEventListener('submit', async e => {
    e.preventDefault();

    try {
        await getJson(api + '/tutorias', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                estudianteId: Number(tutoriaEstudiante.value),
                docente: docente.value,
                tipo: tipo.value,
                fecha: fecha.value,
                estado: 'AGENDADA'
            })
        });

        alert('Tutoría agendada correctamente.');
        tutoriaForm.reset();

        await cargarTodo();

    } catch (err) {
        alert('Error al agendar la tutoría: ' + err.message);
        console.error(err);
    }
});

async function atenderAlerta(id) {
    try {
        await getJson(`${api}/alertas/${id}/atendida`, {
            method: 'PUT'
        });

        await cargarTodo();

    } catch (err) {
        alert(err.message);
    }
}

async function evaluarAlertas() {
    try {
        await getJson(api + '/alertas/evaluar', {
            method: 'POST'
        });

        alert('Motor de reglas ejecutado.');
        await cargarTodo();

    } catch (err) {
        alert(err.message);
    }
}

async function cambiarEstado(id, estado) {
    try {
        await getJson(`${api}/tutorias/${id}/estado`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ estado })
        });

        await cargarTodo();

    } catch (err) {
        alert(err.message);
    }
}

mostrarAplicacion(false);
