package co.dev.victorroe.model.supplier.gateways;

import co.dev.victorroe.model.supplier.Supplier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface SupplierRepository {

    Mono<Supplier> findById(Long id);
    Flux<Supplier> findByIdIn(Collection<Long> ids);
}
