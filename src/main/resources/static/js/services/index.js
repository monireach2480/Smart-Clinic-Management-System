import { openModal } from "./components/modals.js";
import { API_BASE_URL } from "./config/config.js";
const adminEndpoint = `${API_BASE_URL}/admin/login`;
const doctorEndpoint = `${API_BASE_URL}/doctor/login`;

window.onload = () => {
    document.getElementById("admin-login-btn").addEventListener("click", () => {
        openModal("admin-login-modal");
    });

    document.getElementById("doctor-login-btn").addEventListener("click", () => {
        openModal("doctor-login-modal");
    });

    document.querySelectorAll(".close").forEach(btn => {
        btn.onclick = () => {
            btn.parentElement.parentElement.classList.add("hidden");
        };
    });
};

window.adminLoginHandler = async function () {
    const username = document.getElementById("admin-username").value;
    const password = document.getElementById("admin-password").value;

    const admin = { username, password };

    try {
        const response = await fetch(adminEndpoint, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(admin)
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("role", "admin");
            localStorage.setItem("token", data.token);
            window.location.href = "templates/admin/adminDashboard.html";
        } else {
            alert("Invalid credentials!");
        }
    } catch (error) {
        alert("Error logging in: " + error);
    }
};

window.doctorLoginHandler = async function () {
    const email = document.getElementById("doctor-email").value;
    const password = document.getElementById("doctor-password").value;

    const doctor = { email, password };

    try {
        const response = await fetch(doctorEndpoint, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(doctor)
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("role", "doctor");
            localStorage.setItem("token", data.token);
            window.location.href = "templates/doctor/doctorDashboard.html";
        } else {
            alert("Invalid credentials!");
        }
    } catch (error) {
        alert("Error logging in: " + error);
    }
};