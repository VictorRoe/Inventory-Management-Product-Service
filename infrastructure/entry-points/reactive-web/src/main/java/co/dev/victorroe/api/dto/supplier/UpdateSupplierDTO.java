package co.dev.victorroe.api.dto.supplier;

import java.util.Optional;

public record UpdateSupplierDTO(
        Optional<String> name,
        Optional<String> lastName,
        Optional<String> address,
        Optional<String> company
) {
}
