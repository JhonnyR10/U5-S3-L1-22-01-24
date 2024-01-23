package giovannilongo.U5S3L1220124.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewUserDTO(@NotEmpty(message = "L'username è obbligatorio")
                         @Size(min = 3, max = 18, message = "Username deve avere minimo 3 caratteri, massimo 18")
                         String username,
                         @NotEmpty(message = "Il nome è obbligatorio")
                         @Size(min = 3, max = 18, message = "Nome deve avere minimo 3 caratteri, massimo 18")
                         String firstName,
                         @NotEmpty(message = "Il cognome è obbligatorio")
                         String lastName,
                         @Email(message = "L'indirizzo inserito non è un indirizzo valido")
                         @NotEmpty(message = "La mail è un campo obbligatorio!")
                         String email,
                         @NotEmpty(message = "La password è obbligatoria")
                         @Size(min = 3, max = 18, message = "Password deve avere minimo 3 caratteri, massimo 18")
                         String password
) {
}
