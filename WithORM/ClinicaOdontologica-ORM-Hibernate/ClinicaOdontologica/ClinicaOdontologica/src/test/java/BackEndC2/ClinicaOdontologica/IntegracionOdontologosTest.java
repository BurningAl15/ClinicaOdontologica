package BackEndC2.ClinicaOdontologica;

import BackEndC2.ClinicaOdontologica.entity.Odontologo;
import BackEndC2.ClinicaOdontologica.service.OdontologoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionOdontologosTest {
    @Autowired
    private OdontologoService odontologoService;
    
    @Autowired
    private MockMvc mockMvc;
    
    public void cargarDatos(){
        Odontologo odontologoGuardado= odontologoService.guardarOdontologo(new Odontologo("MP10","Ivan","Bustamante"));
    }
    
    @Test
    public void registrarOdontologo() throws Exception {
        // Crear un nuevo odontólogo
        Odontologo odontologo = new Odontologo("MP10", "Ivan", "Bustamante");

        // Convertir el objeto a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String odontologoJson = objectMapper.writeValueAsString(odontologo);

        // Realizar la solicitud POST
        MvcResult resultado = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(odontologoJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verificar que la respuesta no esté vacía
        assertFalse(resultado.getResponse().getContentAsString().isEmpty());

        // Opcional: Verificar que los datos del odontólogo en la respuesta sean correctos
        Odontologo odontologoRespuesta = objectMapper.readValue(resultado.getResponse().getContentAsString(), Odontologo.class);
        assertEquals(odontologo.getNombre(), odontologoRespuesta.getNombre());
        assertEquals(odontologo.getApellido(), odontologoRespuesta.getApellido());
        assertEquals(odontologo.getMatricula(), odontologoRespuesta.getMatricula());
    }
}
