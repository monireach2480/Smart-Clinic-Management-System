// adminDashboard.js
import { getDoctors, saveDoctor, deleteDoctor } from "./services/doctorServices.js";
import { createDoctorCard } from "./components/doctorCard.js";
import { openModal } from "./components/modals.js";

window.onload = async () => {
    const token = localStorage.getItem("adminToken");
    const role = localStorage.getItem("userRole");
    if (!token || role !== "admin") {
        alert("Unauthorized access");
        window.location.href = "../index.html";
        return;
    }

    const doctorContainer = document.getElementById("doctor-container");
    const searchInput = document.getElementById("search-input");
    const addDoctorBtn = document.getElementById("add-doctor-btn");
    const addDoctorForm = document.getElementById("add-doctor-form");

    const renderDoctors = async () => {
        doctorContainer.innerHTML = "";
        const doctors = await getDoctors(token);
        doctors.forEach(doc => {
            const card = createDoctorCard(doc, token, deleteDoctor, renderDoctors);
            doctorContainer.appendChild(card);
        });
    };

    await renderDoctors();

    searchInput.addEventListener("input", async () => {
        const query = searchInput.value.trim();
        const doctors = await getDoctors(token);
        const filtered = doctors.filter(doc =>
            doc.name.toLowerCase().includes(query.toLowerCase())
        );
        doctorContainer.innerHTML = "";
        filtered.forEach(doc => {
            const card = createDoctorCard(doc, token, deleteDoctor, renderDoctors);
            doctorContainer.appendChild(card);
        });
    });

    addDoctorBtn.addEventListener("click", () => {
        openModal("add-doctor-modal");
    });

    addDoctorForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const formData = new FormData(addDoctorForm);
        const doctor = {
            name: formData.get("name"),
            specialty: formData.get("specialty"),
            contact: formData.get("contact")
        };
        await saveDoctor(doctor, token);
        await renderDoctors();
        document.getElementById("add-doctor-modal").style.display = "none";
    });
};
