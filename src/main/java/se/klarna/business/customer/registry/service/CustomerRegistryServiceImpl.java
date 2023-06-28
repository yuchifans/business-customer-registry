package se.klarna.business.customer.registry.service;

import se.klarna.business.customer.registry.domain.Child;
import se.klarna.business.customer.registry.domain.Customer;
import se.klarna.business.customer.registry.exception.ChildNotFoundException;
import se.klarna.business.customer.registry.repository.CustomerRepository;
import se.klarna.business.customer.registry.repository.CustomerRepositoryImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class CustomerRegistryServiceImpl implements CustomerRegistryService{

    private final CustomerRepository customerRepository;

    public CustomerRegistryServiceImpl() {
        this.customerRepository = new CustomerRepositoryImpl();
    }

    public Customer getCustomerBySsn(String ssn) {
        return customerRepository.findCustomerBySsn(ssn);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.saveCustomer(customer) ;
    }

    @Override
    public Child getOldestChildByCustomerSsn(String ssn) {
        Customer customer = customerRepository.findCustomerBySsn(ssn);
        List<Child> children = customer.getChildren();
        if (children == null || children.size() == 0) {
            throw new ChildNotFoundException();
        }
        return children.stream()
                .filter(Objects::nonNull)
                .filter(child -> child.getBirthday() != null)
                .min(Comparator.comparing(Child::getBirthday))
                .orElseThrow(ChildNotFoundException::new);
    }

}
