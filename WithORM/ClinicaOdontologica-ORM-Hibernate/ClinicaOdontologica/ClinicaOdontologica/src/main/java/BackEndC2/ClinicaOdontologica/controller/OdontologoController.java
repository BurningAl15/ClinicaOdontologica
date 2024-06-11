package BackEndC2.ClinicaOdontologica.controller;

import BackEndC2.ClinicaOdontologica.entity.Odontologo;
import BackEndC2.ClinicaOdontologica.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Odontologo> registrarUnOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Long id){
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Odontologo>> listarTodos(){
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarOdontologo(@PathVariable Long id, @RequestBody Odontologo odontologo){
        //necesitamos primeramente validar si existe o  no
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(odontologo.getId());
        if(odontologoBuscado.isPresent()){
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("Odontologo actualizado");
        }else{
            return  ResponseEntity.badRequest().body("no se encontro Odontologo");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id){
        Optional<Odontologo> odontologoBuscado= odontologoService.buscarPorID(id);
        if(odontologoBuscado.isPresent()){
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Odontologo eliminado con exito");
        }else{
            return ResponseEntity.badRequest().body("Odontologo no encontrado");
        }
    }
}
