package BackEndC2.ClinicaOdontologica.controller;

import BackEndC2.ClinicaOdontologica.dto.TurnoDTO;
import BackEndC2.ClinicaOdontologica.entity.Odontologo;
import BackEndC2.ClinicaOdontologica.entity.Paciente;
import BackEndC2.ClinicaOdontologica.entity.Turno;
import BackEndC2.ClinicaOdontologica.exception.BadRequestException;
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
    public ResponseEntity<TurnoDTO> guardarTurno(@RequestBody Turno turno) throws BadRequestException {
        Long pacienteId = (long) -1;
        Long odontologoId = (long) -1;

        if(turno.getPaciente() != null)
            pacienteId = turno.getPaciente().getId();
        if(turno.getOdontologo() != null)
            odontologoId = turno.getOdontologo().getId();
        
        Optional<Paciente> pacienteOpt = pacienteService.buscarPorID(pacienteId);
        Optional<Odontologo> odontologoOpt = odontologoService.buscarPorID(odontologoId);

        if (pacienteOpt.isPresent() && odontologoOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            Odontologo odontologo = odontologoOpt.get();

            turno.setPaciente(paciente);
            turno.setOdontologo(odontologo);

            TurnoDTO turnoGuardado = turnoService.registrarTurno(turno);

            return ResponseEntity.ok(turnoGuardado);
        } else {
            // Log para depuración
            if (!pacienteOpt.isPresent()) {
                throw new BadRequestException("Error al registrar turno, paciente incorrecto");
                //System.out.println("Paciente no encontrado.");
            }
            if (!odontologoOpt.isPresent()) {
                throw new BadRequestException("Error al registrar turno, odontologo incorrecto");
                //System.out.println("Odontologo no encontrado.");
            }
            throw new BadRequestException("Error al registrar turno, paciente y odontologo incorrecto");
        }
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTodosLosTurnos(){
        return ResponseEntity.ok(turnoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscarTurnoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurnoDTOId(id);
        if(turnoBuscado.isPresent()){
            return ResponseEntity.ok(turnoBuscado.get());
        }else{
            throw new ResourceNotFoundException("No existe odontologo con id : "+id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnoDTO> actualizarTurno(@PathVariable Long id, @RequestBody Turno turno) throws ResourceNotFoundException {
        Optional<Turno> turnoExistenteOpt = turnoService.buscarTurnoId(id);

        Long pacienteId = (long) -1;
        Long odontologoId = (long) -1;
        
        
        if (turnoExistenteOpt.isPresent()) {
            Turno turnoExistente = turnoExistenteOpt.get();

            pacienteId = turno.getPaciente().getId();
            odontologoId = turno.getOdontologo().getId();

            Optional<Paciente> pacienteOpt = pacienteService.buscarPorID(pacienteId);
            Optional<Odontologo> odontologoOpt = odontologoService.buscarPorID(odontologoId);

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
                //turnoExistente.setPacienteId(paciente.getId());
                
                turnoExistente.setOdontologo(odontologo);
                //turnoExistente.setOdontologoId(odontologo.getId());
                
                turnoExistente.setFecha(turno.getFecha());

                TurnoDTO turnoActualizado = turnoService.registrarTurno(turnoExistente);

                return ResponseEntity.ok(turnoActualizado);
            }
            
        } else {
            throw new ResourceNotFoundException("No existe turno con id : "+id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarTurnoDTOId(id);

        Long pacienteId = turnoBuscado.get().getPacienteId();
        Long odontologoId = turnoBuscado.get().getOdontologoId();

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
