package se.klarna.business.customer.registry.service;

import se.klarna.business.customer.registry.persistence.domain.Child;
import se.klarna.business.customer.registry.persistence.domain.Customer;

public interface CustomerRegistryService {
        Customer getCustomerBySsn(String ssn);
        Customer addCustomer(Customer customer);
        Child getOldestChild(String ssn);
}
