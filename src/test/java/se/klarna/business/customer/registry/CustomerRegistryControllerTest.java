package se.klarna.business.customer.registry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import se.klarna.business.customer.registry.controller.CustomerRegistryController;
import se.klarna.business.customer.registry.persistence.domain.Child;
import se.klarna.business.customer.registry.persistence.domain.Customer;
import se.klarna.business.customer.registry.service.CustomerRegistryService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerRegistryControllerTest {

    private final CustomerRegistryService customerRegistryService = Mockito.mock(CustomerRegistryService.class);
    private final CustomerRegistryController customerRegistryController = new CustomerRegistryController(customerRegistryService);

    private static final String SSN = "195312545963";
    @Test
    @Tag("unit")
    @DisplayName("responds with status 200 when customer is fetched successfully")
    public void respondsWithStatus200WhenCustomerIsFetchedSuccessfully() {
        Customer customer = createRadomCustomer();
        Mockito.when(customerRegistryService.getCustomerBySsn(SSN)).thenReturn(customer);
        ResponseEntity<Customer> response = customerRegistryController.getCustomer(SSN);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status code is not 200 OK.");
    }

    @Test
    @Tag("unit")
    @DisplayName("responds with status 200 when the oldest child is fetched successfully")
    public void respondsWithStatus200WhenTheOldestChildIsFetchedSuccessfully() {
        Child child = createRadomChild("Susan", Date.valueOf(LocalDate.now().plusDays(1)));
        Mockito.when(customerRegistryService.getOldestChild(SSN)).thenReturn(child);
        ResponseEntity<Child> response = customerRegistryController.getOldestChild(SSN);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response status code is not 200 OK.");
    }

    private Customer createRadomCustomer() {
        final Customer customer = new Customer();
        customer.setSsn(SSN);
        customer.setName("siqi");
        customer.setSpouse("yuting");
        final Child child1 = createRadomChild("Daniel", Date.valueOf(LocalDate.now()));
        final Child child2 = createRadomChild("Susan", Date.valueOf(LocalDate.now().plusDays(1)));
        customer.setChildren(List.of(child1, child2));
        return customer;
    }

    private Child createRadomChild(String name, Date birthday) {
        final Child child = new Child();
        child.setName(name);
        child.setBirthday(birthday);
        return child;
    }

}
