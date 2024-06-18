package BackEndC2.ClinicaOdontologica.controller;

import BackEndC2.ClinicaOdontologica.entity.Odontologo;
import BackEndC2.ClinicaOdontologica.entity.Paciente;
import BackEndC2.ClinicaOdontologica.entity.Turno;
import BackEndC2.ClinicaOdontologica.exception.ResourceNotFoundException;
import BackEndC2.ClinicaOdontologica.service.OdontologoService;
import BackEndC2.ClinicaOdontologica.service.PacienteService;
import BackEndC2.ClinicaOdontologica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<?> guardarTurno(@RequestBody Turno turno){
        Long pacienteId = turno.getPaciente().getId();
        Long odontologoId = turno.getOdontologo().getId();

        Optional<Paciente> pacienteOpt = pacienteService.buscarPorID(pacienteId);
        Optional<Odontologo> odontologoOpt = odontologoService.buscarPorID(odontologoId);

        if (pacienteOpt.isPresent() && odontologoOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            Odontologo odontologo = odontologoOpt.get();

            turno.setPaciente(paciente);
            turno.setOdontologo(odontologo);

            Turno turnoGuardado = turnoService.guardarTurno(turno);

            return ResponseEntity.ok(turnoGuardado);
        } else {
            // Log para depuración
            if (!pacienteOpt.isPresent()) {
                System.out.println("Paciente no encontrado.");
            }
            if (!odontologoOpt.isPresent()) {
                System.out.println("Odontologo no encontrado.");
            }
            return ResponseEntity.badRequest().body("Paciente o Odontologo no encontrado");
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTodosLosTurnos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado = turnoService.buscarPorID(id);
        if(turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        }else{
            throw new ResourceNotFoundException("No existe odontologo con id : "+id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turno> actualizarTurno(@PathVariable Long id, @RequestBody Turno turno) throws ResourceNotFoundException {
        Optional<Turno> turnoExistenteOpt = turnoService.buscarPorID(id);

        Long pacienteId = (long) -1;
        Long odontologoId = (long) -1;
        
        
        if (turnoExistenteOpt.isPresent()) {
            Turno turnoExistente = turnoExistenteOpt.get();

            pacienteId = turno.getPaciente().getId();
            odontologoId = turno.getOdontologo().getId();

            Optional<Paciente> pacienteOpt = pacienteService.buscarPorID(pacienteId);
            Optional<Odontologo> odontologoOpt = odontologoService.buscarPorID(odontologoId);

            /*if (pacienteOpt.isPresent() && odontologoOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                Odontologo odontologo = odontologoOpt.get();

                turnoExistente.setPaciente(paciente);
                turnoExistente.setOdontologo(odontologo);
                turnoExistente.setFecha(turno.getFecha());

                Turno turnoActualizado = turnoService.guardarTurno(turnoExistente);

                return ResponseEntity.ok(turnoActualizado);
            } else {
                return ResponseEntity.badRequest().body("Paciente o Odontologo no encontrado");
            }*/
            
            if(pacienteId == -1 ){
                throw new ResourceNotFoundException("No existe paciente con id : "+id);
            }
            else if(odontologoId == -1) {
                throw new ResourceNotFoundException("No existe odontologo con id : "+id);
            }
            else{
                Paciente paciente = pacienteOpt.get();
                Odontologo odontologo = odontologoOpt.get();

                turnoExistente.setPaciente(paciente);
                turnoExistente.setOdontologo(odontologo);
                turnoExistente.setFecha(turno.getFecha());

                Turno turnoActualizado = turnoService.guardarTurno(turnoExistente);

                return ResponseEntity.ok(turnoActualizado);
            }
            
        } else {
            throw new ResourceNotFoundException("No existe turno con id : "+id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Turno> turnoBuscado = turnoService.buscarPorID(id);

        Long pacienteId = turnoBuscado.get().getPaciente().getId();
        Long odontologoId = turnoBuscado.get().getOdontologo().getId();

        Optional<Paciente> pacienteOpt = pacienteService.buscarPorID(pacienteId);
        Optional<Odontologo> odontologoOpt = odontologoService.buscarPorID(odontologoId);

        if(turnoBuscado.isPresent()){
            pacienteService.guardarPaciente(pacienteOpt.get());
            odontologoService.guardarOdontologo(odontologoOpt.get());
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado con exito");
        }else{
            throw new ResourceNotFoundException("No existe odontologo con id : "+id);
        }
    }
}
