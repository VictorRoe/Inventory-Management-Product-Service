package co.dev.victorroe.api.dto.supplier;

public record RequestSupplierDTO(
        String name,
        String lastName,
        String address,
        String company
) {
}
