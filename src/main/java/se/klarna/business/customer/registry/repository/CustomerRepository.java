package se.klarna.business.customer.registry.repository;


import se.klarna.business.customer.registry.domain.Customer;

public interface CustomerRepository {

    Customer saveCustomer(Customer customer);

    Customer findCustomerBySsn(String ssn);

    Customer removeCustomer(String ssn);

}
