// Header.js
export function createHeader() {
    const header = document.createElement('header');
    header.classList.add('site-header');
    const userRole = localStorage.getItem('userRole');
    const token = localStorage.getItem(`${userRole}Token`);

    let navContent = `
    <div class="logo">Clinic System</div>
    <nav class="nav-links">
  `;

    if (token) {
        if (userRole === 'admin') {
            navContent += `
        <a href="/admin/adminDashboard.html">Dashboard</a>
        <button id="logout">Logout</button>
      `;
        } else if (userRole === 'doctor') {
            navContent += `
        <a href="/doctor/doctorDashboard.html">Dashboard</a>
        <button id="logout">Logout</button>
      `;
        } else if (userRole === 'patient') {
            navContent += `
        <a href="/pages/patientDashboard.html">Dashboard</a>
        <button id="logout">Logout</button>
      `;
        }
    } else {
        navContent += `<a href="/index.html">Home</a>`;
    }

    navContent += `</nav>`;
    header.innerHTML = navContent;

    // Logout button handler
    header.querySelector('#logout')?.addEventListener('click', () => {
        localStorage.clear();
        window.location.href = '/index.html';
    });

    document.body.prepend(header);
}
