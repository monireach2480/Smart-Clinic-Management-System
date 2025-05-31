# User Story Template

---

**Title:**  
_As an admin, I want to log into the portal with my username and password, so that I can manage the platform securely._

**Acceptance Criteria:**
1. Login form accepts valid credentials
2. Incorrect credentials show an error
3. Successful login redirects to the admin dashboard

**Priority:** High  
**Story Points:** 3  
**Notes:**
- Handle login attempt limits for security

---

**Title:**  
_As an admin, I want to log out of the portal, so that I can protect system access._

**Acceptance Criteria:**
1. Logout button is accessible from all pages
2. Clicking logout ends the session
3. User is redirected to the login screen

**Priority:** Medium  
**Story Points:** 2  
**Notes:**
- Ensure session cookies are cleared

---

**Title:**  
_As an admin, I want to add doctors to the portal, so that they can be booked by patients._

**Acceptance Criteria:**
1. Form allows entry of doctor info (name, specialty, contact)
2. Valid submission creates a new doctor profile
3. Added doctor appears in the doctor list

**Priority:** High  
**Story Points:** 5  
**Notes:**
- Validate unique email for each doctor

---

**Title:**  
_As an admin, I want to delete a doctor's profile from the portal, so that outdated data is removed._

**Acceptance Criteria:**
1. Admin sees delete option for each doctor
2. Clicking delete removes doctor after confirmation
3. Doctor is no longer visible in the system

**Priority:** High  
**Story Points:** 3  
**Notes:**
- Prompt confirmation to avoid accidental deletion

---

**Title:**  
_As an admin, I want to run a stored procedure in MySQL CLI to get monthly appointment stats, so that I can track system usage._

**Acceptance Criteria:**
1. Procedure returns appointments per month
2. Output is grouped by month and year
3. Data is viewable in CLI or exported

**Priority:** Medium  
**Story Points:** 4  
**Notes:**
- Procedure should handle no-data months gracefully

---

**Title:**  
_As a patient, I want to view a list of doctors without logging in, so that I can explore options before registering._

**Acceptance Criteria:**
1. Public page displays doctor names and specialties
2. Pagination is available for large lists
3. Search by specialty works correctly

**Priority:** High  
**Story Points:** 3  
**Notes:**
- Do not show contact info before login

---

**Title:**  
_As a patient, I want to sign up using my email and password, so that I can book appointments._

**Acceptance Criteria:**
1. Registration form accepts valid email and password
2. Duplicate emails are rejected
3. Successful signup redirects to login

**Priority:** High  
**Story Points:** 3  
**Notes:**
- Include email format validation

---

**Title:**  
_As a patient, I want to log into the portal, so that I can manage my bookings._

**Acceptance Criteria:**
1. Login form authenticates users
2. Login session persists until logout
3. Invalid login shows appropriate message

**Priority:** High  
**Story Points:** 2  
**Notes:**
- Store session token securely

---

**Title:**  
_As a patient, I want to log out of the portal, so that I can secure my account._

**Acceptance Criteria:**
1. Logout button ends user session
2. User is redirected to homepage
3. Session is destroyed in backend

**Priority:** Medium  
**Story Points:** 2  
**Notes:**
- Session expiration should also auto-logout

---

**Title:**  
_As a patient, I want to book an hour-long appointment, so that I can consult with a doctor._

**Acceptance Criteria:**
1. User selects doctor and time slot
2. Slot is reserved for 60 minutes
3. Confirmation is shown upon booking

**Priority:** High  
**Story Points:** 5  
**Notes:**
- Prevent double-booking of same slot

---

**Title:**  
_As a patient, I want to view my upcoming appointments, so that I can prepare accordingly._

**Acceptance Criteria:**
1. Dashboard lists all upcoming appointments
2. Appointments show doctor name, date, and time
3. Option to reschedule or cancel is visible

**Priority:** High  
**Story Points:** 3  
**Notes:**
- Sort by soonest appointment

---

**Title:**  
_As a doctor, I want to log into the portal, so that I can manage my appointments._

**Acceptance Criteria:**
1. Doctor enters credentials on login screen
2. Successful login redirects to doctor dashboard
3. Login fails on incorrect credentials

**Priority:** High  
**Story Points:** 2  
**Notes:**
- Login must be role-specific (Doctor)

---

**Title:**  
_As a doctor, I want to log out of the portal, so that I can protect my data._

**Acceptance Criteria:**
1. Logout button ends session
2. Redirection to login screen
3. Session data is cleared

**Priority:** Medium  
**Story Points:** 2  
**Notes:**
- Auto-logout on inactivity

---

**Title:**  
_As a doctor, I want to view my appointment calendar, so that I can stay organized._

**Acceptance Criteria:**
1. Appointments shown in calendar view
2. Filters by date and patient
3. Calendar updates in real-time

**Priority:** High  
**Story Points:** 4  
**Notes:**
- Use color coding for status (upcoming/completed)

---

**Title:**  
_As a doctor, I want to mark my unavailability, so that patients only see available slots._

**Acceptance Criteria:**
1. Doctor can block dates/times on calendar
2. Unavailable slots are hidden from patients
3. Conflicting bookings are not allowed

**Priority:** High  
**Story Points:** 4  
**Notes:**
- Allow recurring unavailability (e.g., weekends)

---

**Title:**  
_As a doctor, I want to update my profile with specialization and contact info, so that patients have up-to-date information._

**Acceptance Criteria:**
1. Editable profile form with validation
2. Changes are saved and displayed to patients
3. Invalid inputs are rejected

**Priority:** Medium  
**Story Points:** 3  
**Notes:**
- Specialization should use dropdown/select list

---

**Title:**  
_As a doctor, I want to view patient details for upcoming appointments, so that I can be prepared._

**Acceptance Criteria:**
1. Appointment list includes patient name and history
2. Clicking appointment shows full details
3. Sensitive data is protected

**Priority:** High  
**Story Points:** 4  
**Notes:**
- Restrict access to own appointments only
