package giovannilongo.U5S3L1220124.services;

import giovannilongo.U5S3L1220124.entities.Role;
import giovannilongo.U5S3L1220124.entities.User;
import giovannilongo.U5S3L1220124.exceptions.BadRequestException;
import giovannilongo.U5S3L1220124.exceptions.UnauthorizedException;
import giovannilongo.U5S3L1220124.payloads.NewUserDTO;
import giovannilongo.U5S3L1220124.payloads.UserLoginDTO;
import giovannilongo.U5S3L1220124.repositories.UsersRepository;
import giovannilongo.U5S3L1220124.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO body) {
        User user = usersService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            // 3. Se le credenziali sono OK --> Genere un token JWT e lo ritorno
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401 (Unauthorized)
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public User save(NewUserDTO body) {
        // Verifico se l'email è già in uso
		/*Optional<User> user = usersDAO.findByEmail(body.getEmail());
		if(user.isPresent()) throw new RuntimeException();*/

        usersRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già in uso!");
        });

        User newUser = new User();
        newUser.setUsername(body.username());
        newUser.setAvatar("https://ui-avatars.com/api/?name=" + body.firstName() + "+" + body.lastName());
        newUser.setFirstName(body.firstName());
        newUser.setEmail(body.email());
        //newUser.setPassword(body.password());
        newUser.setLastName(body.lastName());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setRole(Role.USER);
        return usersRepository.save(newUser);
    }
}
