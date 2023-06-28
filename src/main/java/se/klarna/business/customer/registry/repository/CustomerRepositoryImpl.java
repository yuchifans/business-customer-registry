package se.klarna.business.customer.registry.repository;

import se.klarna.business.customer.registry.domain.Customer;
import se.klarna.business.customer.registry.exception.CustomerNotFoundException;
import se.klarna.business.customer.registry.util.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomerRepositoryImpl implements CustomerRepository{

    private final Map<String, Customer> customerRepository;

    public CustomerRepositoryImpl() {
        customerRepository = new HashMap<String, Customer>();
    }

    public Customer saveCustomer(Customer customer) {
        if (Utils.isValid(customer)) {
            return customerRepository.put(customer.getSsn(), customer);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Customer findCustomerBySsn(String ssn) {
        if (Utils.isValid(ssn)) {
            return Optional.ofNullable(customerRepository.get(ssn))
                    .orElseThrow(CustomerNotFoundException::new);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Customer removeCustomer(String ssn) {
        if (Utils.isValid(ssn)) {
            return Optional.ofNullable(customerRepository.remove(ssn))
                    .orElseThrow(CustomerNotFoundException::new);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
