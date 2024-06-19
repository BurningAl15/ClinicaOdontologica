package BackEndC2.ClinicaOdontologica.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Map<String, String> roleTargetUrlMap = new HashMap<>();

    public CustomAuthenticationSuccessHandler() {
        roleTargetUrlMap.put("ROLE_USER", "/get_turnos_user.html");
        roleTargetUrlMap.put("ROLE_ADMIN", "/");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        response.sendRedirect(targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(grantedAuthority -> roleTargetUrlMap.get(grantedAuthority.getAuthority()))
                .filter(url -> url != null)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No target URL found for the given role"));
    }
}