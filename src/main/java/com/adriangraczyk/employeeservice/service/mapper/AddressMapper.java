package com.adriangraczyk.employeeservice.service.mapper;


import com.adriangraczyk.employeeservice.domain.*;
import com.adriangraczyk.employeeservice.service.dto.AddressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "employee.id", target = "employeeId")
    AddressDTO toDto(Address address);

    @Mapping(source = "employeeId", target = "employee")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(String id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
