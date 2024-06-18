package BackEndC2.ClinicaOdontologica.security;

import BackEndC2.ClinicaOdontologica.entity.Usuario;
import BackEndC2.ClinicaOdontologica.entity.UsuarioRole;
import BackEndC2.ClinicaOdontologica.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements ApplicationRunner {
@Autowired
private UsuarioRepository usuarioRepository;
@Autowired
private BCryptPasswordEncoder passwordEncoder;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String passSinCifrar= "user";
        String passCifrado= passwordEncoder.encode(passSinCifrar);
        Usuario usuario= new Usuario("jorgito", UsuarioRole.ROLE_USER,passCifrado,"user@user.com","jpereyradh");
        System.out.println("pass cifrado: "+passCifrado);
        usuarioRepository.save(usuario);

        String passSinCifrar2= "admin";
        String passCifrado2= passwordEncoder.encode(passSinCifrar2);
        Usuario usuario2= new Usuario("daniel", UsuarioRole.ROLE_ADMIN,passCifrado2,"admin@admin.com","danieldh");
        System.out.println("pass cifrado: "+passCifrado2);
        usuarioRepository.save(usuario2);

    }
}
