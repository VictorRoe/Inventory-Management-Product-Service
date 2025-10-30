package co.dev.victorroe.usecase.supplier;

import co.dev.victorroe.model.supplier.Supplier;
import co.dev.victorroe.model.supplier.gateways.SupplierRepository;
import co.dev.victorroe.usecase.supplier.contract.IFindSupplierByIdUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindSupplierByIdUseCase implements IFindSupplierByIdUseCase {

    private final SupplierRepository repository;

    @Override
    public Mono<Supplier> findSupplierById(Long id) {
        return repository.findById(id);
    }
}
