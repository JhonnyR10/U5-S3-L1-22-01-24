package giovannilongo.U5S3L1220124.controllers;

import giovannilongo.U5S3L1220124.entities.User;
import giovannilongo.U5S3L1220124.exceptions.BadRequestException;
import giovannilongo.U5S3L1220124.payloads.NewUserDTO;
import giovannilongo.U5S3L1220124.payloads.NewUserResponseDTO;
import giovannilongo.U5S3L1220124.payloads.UserLoginDTO;
import giovannilongo.U5S3L1220124.payloads.UserLoginResponseDTO;
import giovannilongo.U5S3L1220124.services.AuthService;
import giovannilongo.U5S3L1220124.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    UsersService usersService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        String accessToken = authService.authenticateUser(body);
        return new UserLoginResponseDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO createUser(@RequestBody @Validated NewUserDTO newUserPayload, BindingResult validation) throws IOException {
        // Per completare la validazione devo in qualche maniera fare un controllo del tipo: se ci sono errori -> manda risposta con 400 Bad Request
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!"); // L'eccezione arriverà agli error handlers tra i quali c'è quello che manderà la risposta con status code 400
        } else {
            User newUser = usersService.save(newUserPayload);

            return new NewUserResponseDTO(newUser.getId());
        }
    }
}
