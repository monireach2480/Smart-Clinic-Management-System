package smart_clinic.com.Smart_Clinic.Controller;


import smart_clinic.com.Smart_Clinic.service.TokenValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private TokenValidationService tokenValidationService;

    // Admin Dashboard
    @GetMapping("/adminDashboard/{token}")
    public String adminDashboard(@PathVariable String token) {
        Map<String, Object> validationResult = tokenValidationService.validateToken(token, "admin");

        if (validationResult.isEmpty()) {
            // Token is valid for admin
            return "admin/adminDashboard"; // Thymeleaf view path: src/main/resources/templates/admin/adminDashboard.html
        } else {
            // Token invalid or role mismatch
            return "redirect:http://localhost:8080";
        }
    }

    // Doctor Dashboard
    @GetMapping("/doctorDashboard/{token}")
    public String doctorDashboard(@PathVariable String token) {
        Map<String, Object> validationResult = tokenValidationService.validateToken(token, "doctor");

        if (validationResult.isEmpty()) {
            // Token is valid for doctor
            return "doctor/doctorDashboard"; // Thymeleaf view path: src/main/resources/templates/doctor/doctorDashboard.html
        } else {
            // Token invalid or role mismatch
            return "redirect:http://localhost:8080";
        }
    }
}
