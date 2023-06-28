package se.klarna.business.customer.registry.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.klarna.business.customer.registry.exception.ChildNotFoundException;
import se.klarna.business.customer.registry.exception.CustomerNotFoundException;
import se.klarna.business.customer.registry.persistence.domain.Child;
import se.klarna.business.customer.registry.persistence.domain.Customer;
import se.klarna.business.customer.registry.service.CustomerRegistryService;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;


@RestController
@RequestMapping(value = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerRegistryController {

    private static final String SSN_PATTERN = "^(19|20)(\\d{6}\\d{4})$";
    private final CustomerRegistryService customerRegistryService;

    public CustomerRegistryController(@Qualifier("customerRegistryService") CustomerRegistryService customerRegistryService) {
        this.customerRegistryService = customerRegistryService;
    }
    @GetMapping("/{ssn}/oldestChild")
    public ResponseEntity<Child> getOldestChild(@PathVariable @Pattern(regexp = SSN_PATTERN) String ssn) {
        try {
            Child child = customerRegistryService.getOldestChild(ssn);
            return ResponseEntity.ok(child);
        } catch (ChildNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{ssn}")
    public ResponseEntity<Customer> getCustomer(@PathVariable @Pattern(regexp = SSN_PATTERN) String ssn) {
        try {
            Customer customer = customerRegistryService.getCustomerBySsn(ssn);
            return ResponseEntity.ok(customer);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer customer) {
        Customer savedCustomer = customerRegistryService.addCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }
}
