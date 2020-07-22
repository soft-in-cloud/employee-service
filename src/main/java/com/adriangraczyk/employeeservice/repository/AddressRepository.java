package com.adriangraczyk.employeeservice.repository;

import com.adriangraczyk.employeeservice.domain.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Spring Data MongoDB reactive repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends ReactiveMongoRepository<Address, String> {


    Flux<Address> findAllBy(Pageable pageable);

}
