package co.dev.victorroe.usecase.supplier;

import co.dev.victorroe.model.supplier.Supplier;
import co.dev.victorroe.model.supplier.gateways.SupplierRepository;
import co.dev.victorroe.usecase.supplier.contract.IUpdateSupplierUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
public class UpdateSupplierUseCase implements IUpdateSupplierUseCase {

    private final SupplierRepository repository;

    @Override
    public Mono<Supplier> updateSupplier(Long id ,Optional<String> name, Optional<String> lastname, Optional<String> address, Optional<String> company) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Proovedor no encontrado con ID: " + id)))
                .flatMap(ifSupplierExist -> {
                    Supplier.SupplierBuilder supplierBuilder = ifSupplierExist.toBuilder();

                    name.ifPresent(supplierBuilder::name);
                    lastname.ifPresent(supplierBuilder::lastName);
                    address.ifPresent(supplierBuilder::address);
                    company.ifPresent(supplierBuilder::company);

                    Supplier updateSupplier = supplierBuilder.build();

                    return repository.update(updateSupplier);
                });
    }
}
