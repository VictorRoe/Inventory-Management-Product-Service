package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.supplier.Supplier;
import co.dev.victorroe.model.supplier.gateways.SupplierRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Objects;


@RequiredArgsConstructor
public class SupplierEnricherUseCase implements IProductDataEnricherUseCase {

    private final SupplierRepository repository;

    @Override
    public Mono<Product> enrich(Product product) {
        if (product.getSupplier() == null || product.getSupplier().getId() == null) {
            return Mono.just(product);
        }

        return repository.findById(product.getSupplier().getId())
                .map(fullSupplier -> product.toBuilder().supplier(fullSupplier).build())
                .defaultIfEmpty(product);
    }

    @Override
    public Mono<List<Product>> enrich(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return Mono.just(products);
        }

        List<Long> supplierIds = products.stream()
                .map(p -> p.getSupplier() != null ? p.getSupplier().getId() : null)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        if (supplierIds.isEmpty()) {
            return Mono.just(products);
        }

        return repository.findByIdIn(supplierIds)
                .collectMap(Supplier::getId)
                .map(suppliersMap -> {
                    products.forEach(product -> {
                        if (product.getSupplier() != null && product.getSupplier().getId() != null) {
                            product.setSupplier(suppliersMap.get(product.getSupplier().getId()));
                        }
                    });
                    return products;
                });
    }
}
