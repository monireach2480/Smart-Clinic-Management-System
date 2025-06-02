package smart_clinic.com.Smart_Clinic.repository;


import smart_clinic.com.Smart_Clinic.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Find a patient by email
    Patient findByEmail(String email);

    // Find a patient by email or phone number
    Patient findByEmailOrPhone(String email, String phone);
}