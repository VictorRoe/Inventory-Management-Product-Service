package co.dev.victorroe.usecase.supplier.contract;

import reactor.core.publisher.Mono;

public interface IDeleteSupplierUseCase {

    Mono<Void> deleteSupplierById (Long id);
}
