package BackEndC2.ClinicaOdontologica.service;

import BackEndC2.ClinicaOdontologica.dao.OdontologoCollection;
import BackEndC2.ClinicaOdontologica.dao.OdontologoDaoH2;
import BackEndC2.ClinicaOdontologica.dao.iDao;
import BackEndC2.ClinicaOdontologica.model.Odontologo;
import BackEndC2.ClinicaOdontologica.model.Paciente;

import java.util.List;

public class OdontologoService {
    private iDao<Odontologo> odontologoIDao;

    public OdontologoService() {
        odontologoIDao= new OdontologoDaoH2();
    }

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoIDao.guardar(odontologo);
    }
    public Odontologo buscarOdontologo(Integer id){
        return odontologoIDao.buscarPorID(id);
    }
    public List<Odontologo> buscarTodos(){
        return odontologoIDao.buscarTodos();
    }
    public void actualizarOdontologo(Odontologo odontologo){
        odontologoIDao.actualizar(odontologo);
    }
    public void eliminarPorId(Integer id) { odontologoIDao.eliminar(id);}
}
