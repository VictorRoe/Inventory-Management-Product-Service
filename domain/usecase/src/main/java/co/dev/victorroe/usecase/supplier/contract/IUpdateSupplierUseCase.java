package co.dev.victorroe.usecase.supplier.contract;

import co.dev.victorroe.model.supplier.Supplier;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface IUpdateSupplierUseCase {

    Mono<Supplier> updateSupplier (Long id, Optional<String> name , Optional<String> lastname, Optional<String> address, Optional<String> company);
}
