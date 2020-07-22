package com.adriangraczyk.employeeservice.service;

import com.adriangraczyk.employeeservice.domain.Address;
import com.adriangraczyk.employeeservice.repository.AddressRepository;
import com.adriangraczyk.employeeservice.service.dto.AddressDTO;
import com.adriangraczyk.employeeservice.service.mapper.AddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Service Implementation for managing {@link Address}.
 */
@Service
public class AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<AddressDTO> save(AddressDTO addressDTO) {
        log.debug("Request to save Address : {}", addressDTO);
        return addressRepository.save(addressMapper.toEntity(addressDTO))
            .map(addressMapper::toDto)
;    }

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<AddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        return addressRepository.findAllBy(pageable)
            .map(addressMapper::toDto);
    }


    /**
     * Returns the number of addresses available.
     *
     */
    public Mono<Long> countAll() {
        return addressRepository.count();
    }

    /**
     * Get one address by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<AddressDTO> findOne(String id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findById(id)
            .map(addressMapper::toDto);
    }

    /**
     * Delete the address by id.
     *
     * @param id the id of the entity.
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Address : {}", id);
        return addressRepository.deleteById(id);    }
}
