package se.klarna.business.customer.registry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.klarna.business.customer.registry.exception.ChildNotFoundException;
import se.klarna.business.customer.registry.exception.CustomerNotFoundException;
import se.klarna.business.customer.registry.persistence.domain.Child;
import se.klarna.business.customer.registry.persistence.domain.Customer;
import se.klarna.business.customer.registry.persistence.repository.CustomerRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service("customerRegistryService")
public class CustomerRegistryServiceImpl implements CustomerRegistryService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomerBySsn(String ssn) {
        return customerRepository.findById(ssn)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Child getOldestChild(String ssn) {
        Customer customer = customerRepository.findById(ssn).orElseThrow(CustomerNotFoundException::new);
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
