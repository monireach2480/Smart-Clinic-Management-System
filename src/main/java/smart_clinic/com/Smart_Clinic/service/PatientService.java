package smart_clinic.com.Smart_Clinic.service;



import smart_clinic.com.Smart_Clinic.dto.AppointmentDTO;
import smart_clinic.com.Smart_Clinic.models.Appointment;
import smart_clinic.com.Smart_Clinic.models.Doctor;
import smart_clinic.com.Smart_Clinic.models.Patient;
import smart_clinic.com.Smart_Clinic.models.TokenService;
import smart_clinic.com.Smart_Clinic.repository.AppointmentRepository;
import smart_clinic.com.Smart_Clinic.repository.DoctorRepository;
import smart_clinic.com.Smart_Clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TokenService tokenService;

    public int createPatient(Patient patient) {
        try {
            patientRepository.save(patient);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public ResponseEntity<Map<String, Object>> getPatientAppointment(Long id, String token) {
        String email = tokenService.extractEmail(token);
        Patient patient = patientRepository.findByEmail(email);
        Map<String, Object> response = new HashMap<>();

        if (patient == null || !patient.getId().equals(id)) {
            response.put("message", "Unauthorized");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        List<Appointment> appointments = appointmentRepository.findByPatientId(id);
        List<AppointmentDTO> dtos = appointments.stream().map(AppointmentDTO::new).collect(Collectors.toList());
        response.put("appointments", dtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> filterByCondition(String condition, Long id) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(id);
        List<AppointmentDTO> filtered;

        if (condition.equalsIgnoreCase("past")) {
            filtered = appointments.stream()
                    .filter(a -> a.getAppointmentTime().isBefore(LocalDateTime.now()))
                    .map(AppointmentDTO::new).collect(Collectors.toList());
        } else {
            filtered = appointments.stream()
                    .filter(a -> a.getAppointmentTime().isAfter(LocalDateTime.now()))
                    .map(AppointmentDTO::new).collect(Collectors.toList());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("appointments", filtered);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> filterByDoctor(String name, Long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        List<AppointmentDTO> filtered = appointments.stream()
                .filter(a -> a.getDoctor().getName().toLowerCase().contains(name.toLowerCase()))
                .map(AppointmentDTO::new).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("appointments", filtered);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> filterByDoctorAndCondition(String condition, String name, long patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        LocalDateTime now = LocalDateTime.now();
        List<AppointmentDTO> filtered = appointments.stream()
                .filter(a -> a.getDoctor().getName().toLowerCase().contains(name.toLowerCase()) &&
                        (condition.equalsIgnoreCase("past") ? a.getAppointmentTime().isBefore(now) : a.getAppointmentTime().isAfter(now)))
                .map(AppointmentDTO::new).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("appointments", filtered);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> getPatientDetails(String token) {
        String email = tokenService.extractEmail(token);
        Patient patient = patientRepository.findByEmail(email);
        Map<String, Object> response = new HashMap<>();

        if (patient != null) {
            response.put("patient", patient);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.put("message", "Patient not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
