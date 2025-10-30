package co.dev.victorroe.usecase.supplier.contract;

import co.dev.victorroe.model.supplier.Supplier;
import reactor.core.publisher.Mono;

public interface ICreateSupplierUseCase {

    Mono<Supplier> create (Supplier supplier);
}
