package com.adriangraczyk.employeeservice.web.rest;

import com.adriangraczyk.employeeservice.EmployeeserviceApp;
import com.adriangraczyk.employeeservice.domain.Address;
import com.adriangraczyk.employeeservice.repository.AddressRepository;
import com.adriangraczyk.employeeservice.service.AddressService;
import com.adriangraczyk.employeeservice.service.dto.AddressDTO;
import com.adriangraczyk.employeeservice.service.mapper.AddressMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.adriangraczyk.employeeservice.domain.enumeration.AddressType;
/**
 * Integration tests for the {@link AddressResource} REST controller.
 */
@SpringBootTest(classes = EmployeeserviceApp.class)
@AutoConfigureWebTestClient
@WithMockUser
public class AddressResourceIT {

    private static final AddressType DEFAULT_ADDRESS_TYPE = AddressType.REGISTERED_ADDRESS;
    private static final AddressType UPDATED_ADDRESS_TYPE = AddressType.HOME_ADDRESS;

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_BUILDING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FLAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLAT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private WebTestClient webTestClient;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity() {
        Address address = new Address()
            .addressType(DEFAULT_ADDRESS_TYPE)
            .street(DEFAULT_STREET)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .buildingNumber(DEFAULT_BUILDING_NUMBER)
            .flatNumber(DEFAULT_FLAT_NUMBER);
        return address;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity() {
        Address address = new Address()
            .addressType(UPDATED_ADDRESS_TYPE)
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .buildingNumber(UPDATED_BUILDING_NUMBER)
            .flatNumber(UPDATED_FLAT_NUMBER);
        return address;
    }

    @BeforeEach
    public void initTest() {
        addressRepository.deleteAll().block();
        address = createEntity();
    }

    @Test
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().collectList().block().size();
        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        webTestClient.post().uri("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(addressDTO))
            .exchange()
            .expectStatus().isCreated();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddressType()).isEqualTo(DEFAULT_ADDRESS_TYPE);
        assertThat(testAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAddress.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testAddress.getBuildingNumber()).isEqualTo(DEFAULT_BUILDING_NUMBER);
        assertThat(testAddress.getFlatNumber()).isEqualTo(DEFAULT_FLAT_NUMBER);
    }

    @Test
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().collectList().block().size();

        // Create the Address with an existing ID
        address.setId("existing_id");
        AddressDTO addressDTO = addressMapper.toDto(address);

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient.post().uri("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(addressDTO))
            .exchange()
            .expectStatus().isBadRequest();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkAddressTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().collectList().block().size();
        // set the field null
        address.setAddressType(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);


        webTestClient.post().uri("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(addressDTO))
            .exchange()
            .expectStatus().isBadRequest();

        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().collectList().block().size();
        // set the field null
        address.setStreet(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);


        webTestClient.post().uri("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(addressDTO))
            .exchange()
            .expectStatus().isBadRequest();

        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().collectList().block().size();
        // set the field null
        address.setPostalCode(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);


        webTestClient.post().uri("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(addressDTO))
            .exchange()
            .expectStatus().isBadRequest();

        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().collectList().block().size();
        // set the field null
        address.setCity(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);


        webTestClient.post().uri("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(addressDTO))
            .exchange()
            .expectStatus().isBadRequest();

        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().collectList().block().size();
        // set the field null
        address.setCountry(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);


        webTestClient.post().uri("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(addressDTO))
            .exchange()
            .expectStatus().isBadRequest();

        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAddresses() {
        // Initialize the database
        addressRepository.save(address).block();

        // Get all the addressList
        webTestClient.get().uri("/api/addresses?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id").value(hasItem(address.getId()))
            .jsonPath("$.[*].addressType").value(hasItem(DEFAULT_ADDRESS_TYPE.toString()))
            .jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET))
            .jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE))
            .jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY))
            .jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY))
            .jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE))
            .jsonPath("$.[*].buildingNumber").value(hasItem(DEFAULT_BUILDING_NUMBER))
            .jsonPath("$.[*].flatNumber").value(hasItem(DEFAULT_FLAT_NUMBER));
    }
    
    @Test
    public void getAddress() {
        // Initialize the database
        addressRepository.save(address).block();

        // Get the address
        webTestClient.get().uri("/api/addresses/{id}", address.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").value(is(address.getId()))
            .jsonPath("$.addressType").value(is(DEFAULT_ADDRESS_TYPE.toString()))
            .jsonPath("$.street").value(is(DEFAULT_STREET))
            .jsonPath("$.postalCode").value(is(DEFAULT_POSTAL_CODE))
            .jsonPath("$.city").value(is(DEFAULT_CITY))
            .jsonPath("$.country").value(is(DEFAULT_COUNTRY))
            .jsonPath("$.stateProvince").value(is(DEFAULT_STATE_PROVINCE))
            .jsonPath("$.buildingNumber").value(is(DEFAULT_BUILDING_NUMBER))
            .jsonPath("$.flatNumber").value(is(DEFAULT_FLAT_NUMBER));
    }
    @Test
    public void getNonExistingAddress() {
        // Get the address
        webTestClient.get().uri("/api/addresses/{id}", Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.save(address).block();

        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).block();
        updatedAddress
            .addressType(UPDATED_ADDRESS_TYPE)
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .buildingNumber(UPDATED_BUILDING_NUMBER)
            .flatNumber(UPDATED_FLAT_NUMBER);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        webTestClient.put().uri("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(addressDTO))
            .exchange()
            .expectStatus().isOk();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddressType()).isEqualTo(UPDATED_ADDRESS_TYPE);
        assertThat(testAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddress.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testAddress.getBuildingNumber()).isEqualTo(UPDATED_BUILDING_NUMBER);
        assertThat(testAddress.getFlatNumber()).isEqualTo(UPDATED_FLAT_NUMBER);
    }

    @Test
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().collectList().block().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient.put().uri("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(addressDTO))
            .exchange()
            .expectStatus().isBadRequest();

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAddress() {
        // Initialize the database
        addressRepository.save(address).block();

        int databaseSizeBeforeDelete = addressRepository.findAll().collectList().block().size();

        // Delete the address
        webTestClient.delete().uri("/api/addresses/{id}", address.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNoContent();

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll().collectList().block();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
