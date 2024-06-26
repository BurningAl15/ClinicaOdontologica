package BackEndC2.ClinicaOdontologica.controller;

import BackEndC2.ClinicaOdontologica.entity.Paciente;
import BackEndC2.ClinicaOdontologica.exception.ResourceNotFoundException;
import BackEndC2.ClinicaOdontologica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController //cambiamos pq no necesitamos tecnologia de vista.
@RequestMapping("/pacientes")
public class PacienteController {
   @Autowired
    private PacienteService pacienteService;


    @PostMapping //nos permite crear o registrar un paciente
    public ResponseEntity<Paciente> registrarUnPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacienteID(@PathVariable Long id) throws ResourceNotFoundException {
       Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
       if(pacienteBuscado.isPresent()){
           return ResponseEntity.ok(pacienteBuscado.get());
       }else{
           throw new ResourceNotFoundException("No existe paciente con id : "+id);
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarPaciente(@PathVariable Long id, @RequestBody Paciente paciente) throws ResourceNotFoundException{
        //necesitamos primeramente validar si existe o  no
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(paciente.getId());
        if(pacienteBuscado.isPresent()){
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("paciente actualizado");
        }else{
            throw new ResourceNotFoundException("No existe paciente con id : "+id);
        }

    }
    @GetMapping("/email/{email}")
    public ResponseEntity<Paciente> buscarPorEmail(@PathVariable String email){
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorEmail(email);
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.ok(pacienteBuscado.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Paciente> pacienteBuscado= pacienteService.buscarPorID(id);
        if(pacienteBuscado.isPresent()){
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("paciente eliminado con exito");
        }else{
            throw new ResourceNotFoundException("No existe paciente con id : "+id);
        }
    }
}
