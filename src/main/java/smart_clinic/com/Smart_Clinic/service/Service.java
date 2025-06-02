package smart_clinic.com.Smart_Clinic.service;



import smart_clinic.com.Smart_Clinic.dto.AppointmentDTO;
import smart_clinic.com.Smart_Clinic.models.*;
import smart_clinic.com.Smart_Clinic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Service {

    private final TokenService tokenService;
    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public Service(TokenService tokenService, AdminRepository adminRepository, DoctorRepository doctorRepository,
                   PatientRepository patientRepository, DoctorService doctorService, PatientService patientService) {
        this.tokenService = tokenService;
        this.adminRepository = adminRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    public ResponseEntity<Map<String, String>> validateToken(String token, String user) {
        if (!tokenService.validateToken(token, user)) {
            Map<String, String> res = new HashMap<>();
            res.put("error", "Token is expired or invalid");
            return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    public ResponseEntity<Map<String, String>> validateAdmin(Admin receivedAdmin) {
        Admin admin = adminRepository.findByUsername(receivedAdmin.getUsername());
        Map<String, String> res = new HashMap<>();

        if (admin != null && admin.getPassword().equals(receivedAdmin.getPassword())) {
            String token = tokenService.generateToken(admin.getUsername());
            res.put("token", token);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        res.put("error", "Invalid credentials");
        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }

    public Map<String, Object> filterDoctor(String name, String specialty, String time) {
        if (name != null && specialty != null && time != null) {
            return doctorService.filterDoctorsByNameSpecilityandTime(name, specialty, time);
        } else if (name != null && time != null) {
            return doctorService.filterDoctorByNameAndTime(name, time);
        } else if (name != null && specialty != null) {
            return doctorService.filterDoctorByNameAndSpecility(name, specialty);
        } else if (time != null && specialty != null) {
            return doctorService.filterDoctorByTimeAndSpecility(specialty, time);
        } else if (specialty != null) {
            return doctorService.filterDoctorBySpecility(specialty);
        } else if (time != null) {
            return doctorService.filterDoctorsByTime(time);
        } else {
            Map<String, Object> res = new HashMap<>();
            res.put("doctors", doctorService.getDoctors());
            return res;
        }
    }

    public int validateAppointment(Appointment appointment) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(appointment.getDoctor().getId());
        if (doctorOptional.isEmpty()) {
            return -1;
        }

        List<String> availableSlots = doctorService.getDoctorAvailability(appointment.getDoctor().getId(), appointment.getDate());
        return availableSlots.contains(appointment.getTime()) ? 1 : 0;
    }

    public boolean validatePatient(Patient patient) {
        return patientRepository.findByEmailOrPhone(patient.getEmail(), patient.getPhone()) == null;
    }

    public ResponseEntity<Map<String, String>> validatePatientLogin(Login login) {
        Patient patient = patientRepository.findByEmail(login.getEmail());
        Map<String, String> res = new HashMap<>();

        if (patient != null && patient.getPassword().equals(login.getPassword())) {
            String token = tokenService.generateToken(patient.getEmail());
            res.put("token", token);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        res.put("error", "Invalid credentials");
        return new ResponseEntity<>(res, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<Map<String, Object>> filterPatient(String condition, String name, String token) {
        String email = tokenService.extractEmail(token);
        Patient patient = patientRepository.findByEmail(email);

        if (patient == null) {
            Map<String, Object> res = new HashMap<>();
            res.put("error", "Patient not found");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        if (condition != null && name != null) {
            return patientService.filterByDoctorAndCondition(condition, name, patient.getId());
        } else if (condition != null) {
            return patientService.filterByCondition(condition, patient.getId());
        } else if (name != null) {
            return patientService.filterByDoctor(name, patient.getId());
        }

        Map<String, Object> res = new HashMap<>();
        res.put("error", "Invalid filter criteria");
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}
