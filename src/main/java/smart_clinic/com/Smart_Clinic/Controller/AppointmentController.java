package smart_clinic.com.Smart_Clinic.Controller;



import smart_clinic.com.Smart_Clinic.models.Appointment;
import smart_clinic.com.Smart_Clinic.service.AppointmentService;
import smart_clinic.com.Smart_Clinic.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final Service centralService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, Service centralService) {
        this.appointmentService = appointmentService;
        this.centralService = centralService;
    }

    @GetMapping("/{date}/{patientName}/{token}")
    public ResponseEntity<Map<String, Object>> getAppointments(
            @PathVariable String date,
            @PathVariable String patientName,
            @PathVariable String token
    ) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, "doctor");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Invalid or expired token"), HttpStatus.UNAUTHORIZED);
        }

        return appointmentService.getAppointment(date, patientName);
    }

    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> bookAppointment(
            @PathVariable String token,
            @RequestBody Appointment appointment
    ) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, "patient");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Invalid or expired token"), HttpStatus.UNAUTHORIZED);
        }

        int status = centralService.validateAppointment(appointment);
        if (status == 1) {
            return appointmentService.bookAppointment(appointment);
        } else if (status == 0) {
            return new ResponseEntity<>(Map.of("error", "Time slot is unavailable"), HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(Map.of("error", "Doctor not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateAppointment(
            @PathVariable String token,
            @RequestBody Appointment appointment
    ) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, "patient");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Invalid or expired token"), HttpStatus.UNAUTHORIZED);
        }

        return appointmentService.updateAppointment(appointment);
    }

    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> cancelAppointment(
            @PathVariable Long id,
            @PathVariable String token
    ) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, "patient");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Invalid or expired token"), HttpStatus.UNAUTHORIZED);
        }

        return appointmentService.cancelAppointment(id);
    }
}
