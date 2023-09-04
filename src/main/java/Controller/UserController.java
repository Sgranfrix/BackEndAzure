package Controller;


import Services.AzureDatabaseService;
import Services.UserService;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import entities.DatiSportivi;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*import com.nimbusds.jwt.JWTParser;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.http.HttpHeaders;*/



import javax.management.monitor.GaugeMonitor;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api/User")
public class UserController {

    String token;
    @Autowired
    private UserService userService; //qui mi istanzio il servizio
    @Autowired
    private AzureDatabaseService azureDatabaseService;

    @ResponseStatus(code= HttpStatus.OK)
    @GetMapping("/aggiungi")
    public ResponseEntity addUser(@RequestParam(value = "id_token") String token, HttpServletResponse response) throws ParseException, IOException {
        //Estraggo le informazioni necessarie del token ricevuto
        System.out.println("CAVALLO");
        JWTClaimsSet claims= JWTParser.parse(token).getJWTClaimsSet();
        System.out.println("CAVALLO"+claims);
        System.out.println(token);
        String email=claims.getStringListClaim("emails").get(0);
        String nome=claims.getStringClaim("given_name");
        System.out.println((email));
        //Reindirizzo la richiesta alla pagina di flutter
        String SecondPage="http://localhost:4200/#/welcome?name"+nome+"&"+"email="+email+"&token="+token;
        response.setStatus(HttpServletResponse.SC_FOUND);

        response.setHeader("Location", SecondPage);
        response.setContentLength(0);


        response.sendRedirect(SecondPage);
        User user=new User(email,nome);
        User userAggiunto = userService.registerUser(user);
        return new ResponseEntity(userAggiunto, HttpStatus.OK);
    }




    @GetMapping("/get/mail")
    public List<User> getDataObjectsByMail(@PathVariable String mail) {
        return userService.getUserByMail(mail);
    }
}
