package smart_clinic.com.Smart_Clinic.repository;

import smart_clinic.com.Smart_Clinic.models.Prescription;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PrescriptionRepository extends MongoRepository<Prescription, String> {

    // Find prescriptions by appointment ID
    List<Prescription> findByAppointmentId(Long appointmentId);
}