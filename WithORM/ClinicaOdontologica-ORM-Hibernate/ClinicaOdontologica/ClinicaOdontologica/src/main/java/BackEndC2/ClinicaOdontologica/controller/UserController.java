package BackEndC2.ClinicaOdontologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/get_turnos_user")
    public String userHome() {
        return "/get_turnos_user";
    }
}