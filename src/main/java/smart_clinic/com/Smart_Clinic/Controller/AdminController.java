package smart_clinic.com.Smart_Clinic.Controller;



import smart_clinic.com.Smart_Clinic.models.Admin;
import smart_clinic.com.Smart_Clinic.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.path}" + "admin")
public class AdminController {

    private final Service centralService;

    @Autowired
    public AdminController(Service centralService) {
        this.centralService = centralService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> adminLogin(@RequestBody Admin admin) {
        return centralService.validateAdmin(admin);
    }
}
