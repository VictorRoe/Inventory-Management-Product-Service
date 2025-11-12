package co.dev.victorroe.api.dto.category;

import java.util.Optional;

public record UpdateCategoryDTO(
        Optional<String> name
) {
}
