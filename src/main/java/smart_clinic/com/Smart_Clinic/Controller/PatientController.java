package smart_clinic.com.Smart_Clinic.Controller;



import smart_clinic.com.Smart_Clinic.models.Login;
import smart_clinic.com.Smart_Clinic.models.Patient;
import smart_clinic.com.Smart_Clinic.service.PatientService;
import smart_clinic.com.Smart_Clinic.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
    private final Service centralService;

    @Autowired
    public PatientController(PatientService patientService, Service centralService) {
        this.patientService = patientService;
        this.centralService = centralService;
    }

    // 1. Get Patient Details
    @GetMapping("/{token}")
    public ResponseEntity<?> getPatientDetails(@PathVariable String token) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, "patient");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Invalid or expired token"), HttpStatus.UNAUTHORIZED);
        }

        return patientService.getPatientDetails(token);
    }

    // 2. Create a New Patient
    @PostMapping
    public ResponseEntity<Map<String, String>> createPatient(@RequestBody Patient patient) {
        if (patientService.isPatientExist(patient)) {
            return new ResponseEntity<>(Map.of("message", "Patient with email id or phone no already exist"), HttpStatus.CONFLICT);
        }

        return patientService.createPatient(patient);
    }

    // 3. Patient Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginPatient(@RequestBody Login login) {
        return centralService.validatePatientLogin(login);
    }

    // 4. Get Patient Appointments
    @GetMapping("/{id}/{token}")
    public ResponseEntity<?> getPatientAppointments(@PathVariable Long id, @PathVariable String token) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, "patient");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Invalid or expired token"), HttpStatus.UNAUTHORIZED);
        }

        return patientService.getPatientAppointment(id);
    }

    // 5. Filter Patient Appointments
    @GetMapping("/filter/{condition}/{name}/{token}")
    public ResponseEntity<?> filterAppointments(
            @PathVariable String condition,
            @PathVariable String name,
            @PathVariable String token
    ) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, "patient");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Invalid or expired token"), HttpStatus.UNAUTHORIZED);
        }

        return centralService.filterPatient(condition, name, token);
    }
}

