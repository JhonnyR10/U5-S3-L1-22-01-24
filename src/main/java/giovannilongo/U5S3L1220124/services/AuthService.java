package giovannilongo.U5S3L1220124.services;

import giovannilongo.U5S3L1220124.entities.User;
import giovannilongo.U5S3L1220124.exceptions.UnauthorizedException;
import giovannilongo.U5S3L1220124.payloads.UserLoginDTO;
import giovannilongo.U5S3L1220124.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUser(UserLoginDTO body) {
        User user = usersService.findByEmail(body.email());
        if (body.password().equals(user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }
}
