package se.klarna.business.customer.registry.service;

import se.klarna.business.customer.registry.domain.Child;
import se.klarna.business.customer.registry.domain.Customer;

public interface CustomerRegistryService {
        Customer getCustomerBySsn(String ssn);
        Customer addCustomer(Customer customer);
        Child getOldestChildByCustomerSsn(String ssn);
}
