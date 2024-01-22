package giovannilongo.U5S3L1220124.payloads.errors;

import java.util.Date;
import java.util.List;

public record ErrorsPayloadWithList(
        String message,
        Date timestamp,
        List<String> errorsList
) {
}
