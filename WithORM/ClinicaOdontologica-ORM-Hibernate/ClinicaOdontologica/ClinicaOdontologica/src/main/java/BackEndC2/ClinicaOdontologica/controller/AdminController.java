package BackEndC2.ClinicaOdontologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/index")
    public String adminHome() {
        return "index";
    }
}