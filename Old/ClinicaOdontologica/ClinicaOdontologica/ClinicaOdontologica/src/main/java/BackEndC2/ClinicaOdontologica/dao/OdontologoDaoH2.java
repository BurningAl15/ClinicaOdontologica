package BackEndC2.ClinicaOdontologica.dao;

import BackEndC2.ClinicaOdontologica.model.Odontologo;
import BackEndC2.ClinicaOdontologica.model.Paciente;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDaoH2 implements iDao<Odontologo> {
    /*private Integer id;
    private Integer numeroMatricula;
    private String nombre;
    private String apellido;*/

    private static final Logger logger= Logger.getLogger(OdontologoDaoH2.class);
    private static final String SQL_INSERT="INSERT INTO ODONTOLOGOS (NUMERO_MATRICULA, NOMBRE, APELLIDO) VALUES(?,?,?)";
    private static final String SQL_SELECT_ONE="SELECT * FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    private static final String SQL_UPDATE="UPDATE ODONTOLOGOS SET NUMERO_MATRICULA=?, NOMBRE=?, APELLIDO=? ";
    private static final String SQL_DELETE = "DELETE FROM ODONTOLOGOS WHERE ID=?";


    @Override
    public Odontologo guardar(Odontologo odontologo) {
        logger.info("inicando la operacion de guardado");
        Connection connection=null;
        try{
            connection= BD.getConnection();
            PreparedStatement psInsert= connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            psInsert.setInt(1, odontologo.getNumeroMatricula());
            psInsert.setString(2, odontologo.getNombre());
            psInsert.setString(3, odontologo.getApellido());
            psInsert.execute(); //CUANDO SE EJECUTE SE VAN A GENERAR ID

            ResultSet rs = psInsert.getGeneratedKeys();

            while (rs.next()) {
                odontologo.setId(rs.getInt(1));
            }
            logger.info("odontologo guardado con exito");
        }catch (Exception e){
            logger.warn(e.getMessage());
        }
        return odontologo;
    }

    @Override
    public Odontologo buscarPorID(Integer id) {
        logger.info("iniciando la busqueda de un paciente por id: "+id);
        Connection connection= null;
        Odontologo odontologo= null;

        try{
            connection= BD.getConnection();
            PreparedStatement psSelectOne= connection.prepareStatement(SQL_SELECT_ONE);
            psSelectOne.setInt(1,id);
            ResultSet rs= psSelectOne.executeQuery();
            while(rs.next()){
                odontologo= new Odontologo(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4));
            }

        }catch (Exception e){
            logger.warn(e.getMessage());
        }
        return odontologo;
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        logger.info("iniciando la actualizacion de : "+odontologo.getNombre()+" con id : "+odontologo.getId());
        Connection connection= null;

        try{
            connection= BD.getConnection();
            PreparedStatement psUpdate= connection.prepareStatement(SQL_UPDATE);
            psUpdate.setInt(1,odontologo.getNumeroMatricula());
            psUpdate.setString(2, odontologo.getNombre());
            psUpdate.setString(3, odontologo.getApellido());

            psUpdate.execute();

        }catch (Exception e){
            logger.warn(e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar la conexión", e);
                }
            }
        }

    }

    @Override
    public void eliminar(Integer id) {
        logger.info("Iniciando la eliminación del odontologo con id: " + id);
        Connection connection = null;
        try {
            connection = BD.getConnection();
            PreparedStatement psDelete = connection.prepareStatement(SQL_DELETE);
            psDelete.setInt(1, id);
            int rowsAffected = psDelete.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Odontologo con id " + id + " eliminado con éxito.");
            } else {
                logger.warn("No se encontró ningún odontologo con id " + id + " para eliminar.");
            }
        } catch (Exception e) {
            logger.warn("Error al eliminar el odontologo con id " + id, e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar la conexión", e);
                }
            }
        }
    }

    @Override
    public List<Odontologo> buscarTodos() {
        List<Odontologo> odontologos = new ArrayList<>();
        Odontologo odontologo = null;
        Connection connection= null;
        try{
            connection=BD.getConnection();
            Statement statement= connection.createStatement();
            ResultSet rs= statement.executeQuery(SQL_SELECT_ALL);

            while (rs.next()){
                odontologo = new Odontologo(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4));
                odontologos.add(odontologo);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar la conexión", e);
                }
            }
        }

        return odontologos;
    }

    @Override
    public Odontologo buscarPorString(String valor){
        return null;
    }

}
