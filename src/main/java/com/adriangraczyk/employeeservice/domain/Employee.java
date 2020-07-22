package com.adriangraczyk.employeeservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.adriangraczyk.employeeservice.domain.enumeration.Role;

/**
 * A Employee.
 */
@Document(collection = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 255)
    @Field("first_name")
    private String firstName;

    @NotNull
    @Size(max = 255)
    @Field("last_name")
    private String lastName;

    @NotNull
    @Min(value = 1)
    @Max(value = 200)
    @Field("age")
    private Integer age;

    @NotNull
    @Pattern(regexp = "[0-9]{11}")
    @Field("p_esel")
    private String pESEL;

    @NotNull
    @Field("role")
    private Role role;

    @DBRef
    @Field("address")
    private Set<Address> addresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public Employee age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getpESEL() {
        return pESEL;
    }

    public Employee pESEL(String pESEL) {
        this.pESEL = pESEL;
        return this;
    }

    public void setpESEL(String pESEL) {
        this.pESEL = pESEL;
    }

    public Role getRole() {
        return role;
    }

    public Employee role(Role role) {
        this.role = role;
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public Employee addresses(Set<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public Employee addAddress(Address address) {
        this.addresses.add(address);
        address.setEmployee(this);
        return this;
    }

    public Employee removeAddress(Address address) {
        this.addresses.remove(address);
        address.setEmployee(null);
        return this;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", age=" + getAge() +
            ", pESEL='" + getpESEL() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
