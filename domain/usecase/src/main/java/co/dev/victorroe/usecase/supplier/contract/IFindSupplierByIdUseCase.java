package co.dev.victorroe.usecase.supplier.contract;

import co.dev.victorroe.model.supplier.Supplier;
import reactor.core.publisher.Mono;

public interface IFindSupplierByIdUseCase {

    Mono<Supplier> findSupplierById (Long id);
}
