// doctorCard.js
export function createDoctorCard(doctor, role) {
    const card = document.createElement('div');
    card.classList.add('doctor-card');

    card.innerHTML = `
    <h3>${doctor.name}</h3>
    <p>Specialty: ${doctor.specialty}</p>
    <p>Availability: ${doctor.availability}</p>
    <p>Email: ${doctor.email}</p>
  `;

    if (role === 'admin') {
        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = 'Delete';
        deleteBtn.addEventListener('click', () => {
            // Call deleteDoctor API (not implemented here)
            alert(`Doctor ${doctor.name} will be deleted.`);
        });
        card.appendChild(deleteBtn);
    }

    if (role === 'patient') {
        const bookBtn = document.createElement('button');
        bookBtn.textContent = 'Book Appointment';
        bookBtn.addEventListener('click', () => {
            // Open booking modal (not implemented here)
            alert(`Booking appointment with Dr. ${doctor.name}`);
        });
        card.appendChild(bookBtn);
    }

    return card;
}