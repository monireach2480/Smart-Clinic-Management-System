import { API_BASE_URL } from '../config/config.js';

export async function getDoctors() {
    try {
        const res = await fetch(`${API_BASE_URL}/doctor`);
        if (!res.ok) throw new Error('Failed to fetch doctors');
        return await res.json();
    } catch (err) {
        console.error(err);
        return [];
    }
}

export async function saveDoctor(doctor, token) {
    try {
        const res = await fetch(`${API_BASE_URL}/doctor`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(doctor),
        });
        return res.ok;
    } catch (err) {
        console.error(err);
        return false;
    }
}

export async function filterDoctors(query) {
    try {
        const res = await fetch(`${API_BASE_URL}/doctor/search?q=${encodeURIComponent(query)}`);
        if (!res.ok) throw new Error('Filter failed');
        return await res.json();
    } catch (err) {
        console.error(err);
        return [];
    }
}

export async function deleteDoctor(id, token) {
    try {
        const res = await fetch(`${API_BASE_URL}/doctor/${id}`, {
            method: 'DELETE',
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return res.ok;
    } catch (err) {
        console.error(err);
        return false;
    }
}
