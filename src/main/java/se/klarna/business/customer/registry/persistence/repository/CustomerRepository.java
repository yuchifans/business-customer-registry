package se.klarna.business.customer.registry.persistence.repository;


import org.springframework.data.repository.CrudRepository;
import se.klarna.business.customer.registry.persistence.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, String> {

}
