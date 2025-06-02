export function createFooter() {
    const footer = document.createElement('footer');
    footer.classList.add('site-footer');
    footer.innerHTML = `
    <p>&copy; 2025 Clinic Management System. All rights reserved.</p>
  `;
    document.body.appendChild(footer);
}