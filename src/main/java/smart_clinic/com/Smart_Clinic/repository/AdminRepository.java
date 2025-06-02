package smart_clinic.com.Smart_Clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smart_clinic.com.Smart_Clinic.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Custom method to find an Admin by username
    Admin findByUsername(String username);
}