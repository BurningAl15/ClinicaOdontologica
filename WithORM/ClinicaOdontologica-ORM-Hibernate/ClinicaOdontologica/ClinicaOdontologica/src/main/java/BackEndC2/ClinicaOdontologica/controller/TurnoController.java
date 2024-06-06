package BackEndC2.ClinicaOdontologica.controller;

import BackEndC2.ClinicaOdontologica.entity.Turno;
import BackEndC2.ClinicaOdontologica.service.OdontologoService;
import BackEndC2.ClinicaOdontologica.service.PacienteService;
import BackEndC2.ClinicaOdontologica.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    @Autowired
    private TurnoService turnoService;


    @PostMapping
    public ResponseEntity<Turno> guardarTurno(@RequestBody Turno turno){
      PacienteService pacienteService= new PacienteService();
      OdontologoService odontologoService= new OdontologoService();

      System.out.println("ID PACIENTE: " + pacienteService.buscarPorID(turno.getPaciente().getId()));
      System.out.println("ID Odontologo: " + odontologoService.buscarPorID(turno.getOdontologo().getId()));

      if(pacienteService.buscarPorID(turno.getPaciente().getId()).isPresent() && odontologoService.buscarPorID(turno.getOdontologo().getId()).isPresent()){
          return ResponseEntity.ok(turnoService.guardarTurno(turno));
      }else{
          //bad request or not found
          return ResponseEntity.badRequest().build();
      }
    }


    @GetMapping
    public ResponseEntity<List<Turno>> listarTodosLosTurnos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }
}
