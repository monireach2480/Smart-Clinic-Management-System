import { getPatientAppointments } from "./services/patientServices.js";
import { createPatientRow } from "./components/patientRows.js";

window.onload = async () => {
    const token = localStorage.getItem("doctorToken");
    const role = localStorage.getItem("userRole");
    if (!token || role !== "doctor") {
        alert("Unauthorized access");
        window.location.href = "../index.html";
        return;
    }

    const patientTable = document.getElementById("patient-table-body");
    const searchInput = document.getElementById("search-patient");
    const dateFilter = document.getElementById("date-filter");

    const loadPatients = async (date) => {
        patientTable.innerHTML = "";
        const appointments = await getPatientAppointments(date, token);
        if (appointments.length === 0) {
            const row = document.createElement("tr");
            row.innerHTML = `<td colspan="4">No appointments found.</td>`;
            patientTable.appendChild(row);
        } else {
            appointments.forEach(appt => {
                const row = createPatientRow(appt);
                patientTable.appendChild(row);
            });
        }
    };

    const today = new Date().toISOString().split("T")[0];
    dateFilter.value = today;
    await loadPatients(today);

    dateFilter.addEventListener("change", async () => {
        const selectedDate = dateFilter.value;
        await loadPatients(selectedDate);
    });

    searchInput.addEventListener("input", () => {
        const term = searchInput.value.toLowerCase();
        const rows = patientTable.getElementsByTagName("tr");
        Array.from(rows).forEach(row => {
            const text = row.innerText.toLowerCase();
            row.style.display = text.includes(term) ? "" : "none";
        });
    });
};
