package smart_clinic.com.Smart_Clinic.Controller;



import smart_clinic.com.Smart_Clinic.models.Doctor;
import smart_clinic.com.Smart_Clinic.models.Login;
import smart_clinic.com.Smart_Clinic.service.DoctorService;
import smart_clinic.com.Smart_Clinic.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.path}doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final Service centralService;

    @Autowired
    public DoctorController(DoctorService doctorService, Service centralService) {
        this.doctorService = doctorService;
        this.centralService = centralService;
    }

    @GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
    public ResponseEntity<Map<String, Object>> getDoctorAvailability(
            @PathVariable String user,
            @PathVariable Long doctorId,
            @PathVariable String date,
            @PathVariable String token
    ) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, user);
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Invalid or expired token"), HttpStatus.UNAUTHORIZED);
        }

        return doctorService.getDoctorAvailability(doctorId, date);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDoctors() {
        return doctorService.getDoctors();
    }

    @PostMapping("/{token}")
    public ResponseEntity<Map<String, String>> addDoctor(
            @PathVariable String token,
            @RequestBody Doctor doctor
    ) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, "admin");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }

        return doctorService.saveDoctor(doctor);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> doctorLogin(@RequestBody Login login) {
        return doctorService.validateDoctor(login);
    }

    @PutMapping("/{token}")
    public ResponseEntity<Map<String, String>> updateDoctor(
            @PathVariable String token,
            @RequestBody Doctor doctor
    ) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, "admin");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }

        return doctorService.updateDoctor(doctor);
    }

    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<Map<String, String>> deleteDoctor(
            @PathVariable Long id,
            @PathVariable String token
    ) {
        ResponseEntity<Map<String, String>> tokenValidation = centralService.validateToken(token, "admin");
        if (tokenValidation.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Map.of("error", "Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }

        return doctorService.deleteDoctor(id);
    }

    @GetMapping("/filter/{name}/{time}/{speciality}")
    public ResponseEntity<List<Doctor>> filterDoctors(
            @PathVariable String name,
            @PathVariable String time,
            @PathVariable String speciality
    ) {
        return centralService.filterDoctor(name, time, speciality);
    }
}
