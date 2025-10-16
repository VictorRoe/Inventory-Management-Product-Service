package co.dev.victorroe.api.dto.supplier;

public record SupplierDTO(
        Long id,
        String name,
        String lastName,
        String address,
        String company
) {
}
