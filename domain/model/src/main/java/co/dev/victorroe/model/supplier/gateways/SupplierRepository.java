package co.dev.victorroe.model.supplier.gateways;

import co.dev.victorroe.model.supplier.Supplier;
import reactor.core.publisher.Mono;

public interface SupplierRepository {

    Mono<Supplier> findById(Long id);
}
