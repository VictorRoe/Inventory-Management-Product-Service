package co.dev.victorroe.api.dto.supplier;

public record ResponseSupplierDTO(
        Long id,
        String name,
        String lastName,
        String address,
        String company
) {
}
