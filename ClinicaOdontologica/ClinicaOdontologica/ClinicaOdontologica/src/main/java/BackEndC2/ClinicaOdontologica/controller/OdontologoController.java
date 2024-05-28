package BackEndC2.ClinicaOdontologica.controller;

import BackEndC2.ClinicaOdontologica.dao.OdontologoDaoH2;
import BackEndC2.ClinicaOdontologica.model.Odontologo;
import BackEndC2.ClinicaOdontologica.model.Paciente;
import BackEndC2.ClinicaOdontologica.service.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //cambiamos pq no necesitamos tecnologia de vista.
@RequestMapping("/odontologo")
public class OdontologoController {
    private static final org.apache.log4j.Logger logger= Logger.getLogger(OdontologoDaoH2.class);


    private OdontologoService odontologoService;

    public OdontologoController() {
        odontologoService= new OdontologoService();
    }

   
    @PostMapping //nos permite crear o registrar un Odontologo
    public Odontologo registrarUnOdontologo(@RequestBody Odontologo odontologo){
        return odontologoService.guardarOdontologo(odontologo);
    }

    @PutMapping
    public String actualizarOdontologo(@RequestBody Odontologo odontologo){
        //necesitamos primeramente validar si existe o  no
        Odontologo odontologoBuscado= odontologoService.buscarOdontologo(odontologo.getId());
        if(odontologoBuscado!=null){
            odontologoService.actualizarOdontologo(odontologo);
            return "paciente actualizado";
        }else{
            return "paciente no encontrado";
        }

    }

    @GetMapping("/{id}")
    public Odontologo buscarOdontologo(@PathVariable Integer id) {
        Odontologo odontologo = odontologoService.buscarOdontologo(id);
        return odontologo;
    }

    @GetMapping
    public List<Odontologo> buscarOdontologos(){
        return odontologoService.buscarTodos();
    }

    @DeleteMapping
    public String eliminarOdontologo(@RequestParam("id") Integer id) {
        Odontologo odontologoBuscado = odontologoService.buscarOdontologo(id);
        if (odontologoBuscado != null) {
            odontologoService.eliminarPorId(id);
            return "Odontologo eliminado";
        } else {
            return "Odontologo no encontrado";
        }
    }
}
