// defineRole.js

import { openModal } from './components/modals.js';
import { API_BASE_URL } from './config/config.js';

window.onload = () => {
    const adminBtn = document.getElementById('adminBtn');
    const doctorBtn = document.getElementById('doctorBtn');

    adminBtn?.addEventListener('click', () => openModal('admin'));
    doctorBtn?.addEventListener('click', () => openModal('doctor'));
};

window.adminLoginHandler = async () => {
    const username = document.getElementById('adminUsername')?.value;
    const password = document.getElementById('adminPassword')?.value;

    const admin = { username, password };
    try {
        const res = await fetch(`${API_BASE_URL}/admin`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(admin),
        });

        if (res.ok) {
            const data = await res.json();
            localStorage.setItem('token', data.token);
            localStorage.setItem('userRole', 'admin');
            window.location.href = './pages/adminDashboard.html';
        } else {
            alert('Invalid credentials!');
        }
    } catch (err) {
        alert('Error: ' + err.message);
    }
};

window.doctorLoginHandler = async () => {
    const email = document.getElementById('doctorEmail')?.value;
    const password = document.getElementById('doctorPassword')?.value;

    const doctor = { email, password };
    try {
        const res = await fetch(`${API_BASE_URL}/doctor/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(doctor),
        });

        if (res.ok) {
            const data = await res.json();
            localStorage.setItem('token', data.token);
            localStorage.setItem('userRole', 'doctor');
            window.location.href = './pages/doctorDashboard.html';
        } else {
            alert('Invalid credentials!');
        }
    } catch (err) {
        alert('Error: ' + err.message);
    }
};