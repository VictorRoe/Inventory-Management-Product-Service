package co.dev.victorroe.usecase.product;

import co.dev.victorroe.model.product.Product;
import co.dev.victorroe.model.supplier.gateways.SupplierRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

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
}
