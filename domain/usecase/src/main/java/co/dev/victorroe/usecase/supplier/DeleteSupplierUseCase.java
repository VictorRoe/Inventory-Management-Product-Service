package co.dev.victorroe.usecase.supplier;

import co.dev.victorroe.model.supplier.gateways.SupplierRepository;
import co.dev.victorroe.usecase.supplier.contract.IDeleteSupplierUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DeleteSupplierUseCase implements IDeleteSupplierUseCase {

    private final SupplierRepository repository;

    @Override
    public Mono<Void> deleteSupplierById(Long id) {
        return repository.delete(id);
    }
}
