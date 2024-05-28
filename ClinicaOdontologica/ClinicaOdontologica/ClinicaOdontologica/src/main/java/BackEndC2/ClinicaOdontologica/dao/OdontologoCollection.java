package BackEndC2.ClinicaOdontologica.dao;

import BackEndC2.ClinicaOdontologica.model.Odontologo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OdontologoCollection implements iDao<Odontologo>{

    private static final Logger logger= Logger.getLogger(OdontologoCollection.class);
    private static List<Odontologo> odontologos;

    @Override
    public Odontologo guardar(Odontologo odontologo) {
        if(odontologos == null)
            odontologos = new ArrayList<>();

        odontologos.add(odontologo);

        return odontologo;
    }

    @Override
    public Odontologo buscarPorID(Integer id) {
        return null;
    }

    @Override
    public void actualizar(Odontologo odontologo) {

    }

    @Override
    public void eliminar(Integer id) {

    }

    @Override
    public List<Odontologo> buscarTodos() {
        return odontologos;
    }

    @Override
    public Odontologo buscarPorString(String valor){
        return null;
    }
}
