package Configurations;

import com.azure.core.exception.ClientAuthenticationException;
//import com.example.progettoAzienda.Support.exceptions.AuthenticationException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;

    public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Verifica del token JWT
        System.out.println("RINOCERONTEEEEEEEEEEE");
        String token = request.getHeader("authorization");
        if(token == null)
            return false;
        if(!isTokenValid(token.substring(7))){
            //return false;
            throw new AuthenticationException("Token non valido");
        }
        return true;
        }
        private static final String CLIENT_ID = "ab08ec79-5edd-427b-99fd-1afb2805b60f";
        private static final String TENANT_ID = "ce915b14-793c-4754-9f79-85b938e167bd ";
        private static final String CLIENT_SECRET = "Qp98Q~4cesMjWxpX9tfG1j.~23whPA5lK5LYtdwo";

        public boolean isTokenValid(String token) {
            try {
                System.out.println("CI HO PROVATOOOOOOOOOOO");
                SignedJWT signedJWT = SignedJWT.parse(token);
                String issuer = signedJWT.getJWTClaimsSet().getIssuer();
                if (!issuer.equals("https://serviziob2c.b2clogin.com/ce915b14-793c-4754-9f79-85b938e167bd/v2.0/")) {
                    return false;
                }
                String audience = signedJWT.getJWTClaimsSet().getAudience().get(0);
                if (!audience.equals(CLIENT_ID)) {
                    return false;
                }
                JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
                if (claimsSet.getExpirationTime().before(new Date()) ||
                        claimsSet.getNotBeforeTime().after(new Date())) {
                    return false;
                }
                return true;


        } catch (ClientAuthenticationException | ParseException ex) {
            // Gestione dell'errore di autenticazione del client
            ex.printStackTrace();
            return false;//ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}


