package com.adriangraczyk.employeeservice.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.adriangraczyk.employeeservice.domain.enumeration.AddressType;

/**
 * A DTO for the {@link com.adriangraczyk.employeeservice.domain.Address} entity.
 */
public class AddressDTO implements Serializable {
    
    private String id;

    @NotNull
    private AddressType addressType;

    @NotNull
    @Size(max = 500)
    private String street;

    @NotNull
    @Size(max = 50)
    private String postalCode;

    @NotNull
    @Size(max = 100)
    private String city;

    @NotNull
    @Size(max = 100)
    private String country;

    @Size(max = 100)
    private String stateProvince;

    @Size(max = 20)
    private String buildingNumber;

    @Size(max = 20)
    private String flatNumber;


    private String employeeId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressDTO)) {
            return false;
        }

        return id != null && id.equals(((AddressDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressDTO{" +
            "id=" + getId() +
            ", addressType='" + getAddressType() + "'" +
            ", street='" + getStreet() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", buildingNumber='" + getBuildingNumber() + "'" +
            ", flatNumber='" + getFlatNumber() + "'" +
            ", employeeId='" + getEmployeeId() + "'" +
            "}";
    }
}
