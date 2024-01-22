package giovannilongo.U5S3L1220124.payloads;

import jakarta.validation.constraints.NotNull;

public record NewDeviceDTO(
        @NotNull(message = "L'id dell'user è obbligatorio")
        Long user_Id,
        @NotNull(message = "Il tipo è obbligatorio")
        String type,
        @NotNull(message = "Lo status è obbligatorio")
        String status
) {
}
