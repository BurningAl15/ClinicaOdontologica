package BackEndC2.ClinicaOdontologica.controller;

import BackEndC2.ClinicaOdontologica.model.Paciente;
import BackEndC2.ClinicaOdontologica.service.PacienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //cambiamos pq no necesitamos tecnologia de vista.
@RequestMapping("/paciente")
public class PacienteController {
    private PacienteService pacienteService;

    public PacienteController() {
        pacienteService= new PacienteService();
    }

    /*@GetMapping
    public String buscarPacientePorCorreo(Model model, @RequestParam("email") String email){
        //vamos a pasar la solicitud atraves del http, osea va a ir en la url
        Paciente paciente= pacienteService.buscarPorCorreo(email);
        model.addAttribute("nombre",paciente.getNombre());
        model.addAttribute("apellido",paciente.getApellido());
        return "index";
        //return pacienteService.buscarPorCorreo(email);
    } */
    @PostMapping //nos permite crear o registrar un paciente
    public Paciente registrarUnPaciente(@RequestBody Paciente paciente){
        return pacienteService.guardarPaciente(paciente);
    }

    @PutMapping
    public String actualizarPaciente(@RequestBody Paciente paciente){
        //necesitamos primeramente validar si existe o  no
        Paciente pacienteBuscado= pacienteService.buscarPaciente(paciente.getId());
        if(pacienteBuscado!=null){
            pacienteService.actualizarPaciente(paciente);
            return "paciente actualizado";
        }else{
            return "paciente no encontrado";
        }

    }

    @GetMapping("/{id}")
    public Paciente buscarPaciente(@PathVariable Integer id) {
       Paciente paciente = pacienteService.buscarPaciente(id);
        return paciente;
    }

    @GetMapping
    public List<Paciente> buscarPacientes(){
        return pacienteService.buscarTodos();
    }

    @DeleteMapping
    public String eliminarPaciente(@RequestParam("id") Integer id) {
        Paciente pacienteBuscado = pacienteService.buscarPaciente(id);
        if (pacienteBuscado != null) {
            pacienteService.eliminarPorId(id);
            return "Paciente eliminado";
        } else {
            return "Paciente no encontrado";
        }
    }
}
