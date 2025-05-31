# Schema Design for Smart Clinic

## MySQL Database Design

### Table: patients
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- full_name: VARCHAR(100), NOT NULL
- date_of_birth: DATE
- gender: ENUM('Male', 'Female', 'Other')
- email: VARCHAR(100), UNIQUE, NOT NULL
- phone: VARCHAR(20), NOT NULL
- address: TEXT

> Notes: Patient contact details should be unique for identification. Appointments related to a deleted patient should be preserved or marked as inactive (soft delete).

---

### Table: doctors
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- full_name: VARCHAR(100), NOT NULL
- specialization: VARCHAR(100), NOT NULL
- email: VARCHAR(100), UNIQUE, NOT NULL
- phone: VARCHAR(20), NOT NULL
- availability_start: TIME
- availability_end: TIME

> Notes: Each doctor can have set working hours. Overlapping appointments should be prevented via scheduling logic.

---

### Table: appointments
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- patient_id: INT, FOREIGN KEY → patients(id), NOT NULL
- doctor_id: INT, FOREIGN KEY → doctors(id), NOT NULL
- appointment_time: DATETIME, NOT NULL
- status: ENUM('Scheduled', 'Completed', 'Cancelled'), DEFAULT 'Scheduled'
- created_at: TIMESTAMP DEFAULT CURRENT_TIMESTAMP

> Notes: Consider using triggers or app logic to prevent overlapping appointments per doctor. A patient can view past and upcoming appointments.

---

### Table: admin
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- username: VARCHAR(50), UNIQUE, NOT NULL
- password_hash: VARCHAR(255), NOT NULL
- email: VARCHAR(100), UNIQUE, NOT NULL
- role: ENUM('Admin', 'Manager'), DEFAULT 'Admin'

> Notes: Admins have system-wide privileges. Store password securely (hashed).

---

### Table: payments (optional)
- id: INT, PRIMARY KEY, AUTO_INCREMENT
- appointment_id: INT, FOREIGN KEY → appointments(id), NOT NULL
- patient_id: INT, FOREIGN KEY → patients(id), NOT NULL
- amount: DECIMAL(10, 2), NOT NULL
- payment_method: ENUM('Cash', 'Card', 'Insurance')
- payment_date: DATETIME DEFAULT CURRENT_TIMESTAMP

> Notes: Track financials of appointments and generate reports for clinic usage.

---

## MongoDB Collection Design

### Collection: prescriptions
```json
{
  "_id": "ObjectId('665df123abc')",
  "appointmentId": 101,
  "patientId": 25,
  "doctorId": 8,
  "medications": [
    {
      "name": "Paracetamol",
      "dosage": "500mg",
      "instructions": "Take one tablet every 6 hours after meals"
    },
    {
      "name": "Vitamin C",
      "dosage": "1000mg",
      "instructions": "Take once daily"
    }
  ],
  "doctorNotes": "Patient reports mild headache and fatigue.",
  "refillCount": 2,
  "createdAt": "2025-05-31T10:30:00Z",
  "pharmacy": {
    "name": "City Meds Pharmacy",
    "location": "123 Riverside Drive"
  }
}
