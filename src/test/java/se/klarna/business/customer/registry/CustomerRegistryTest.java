package se.klarna.business.customer.registry;


import org.junit.jupiter.api.Test;
import se.klarna.business.customer.registry.domain.Child;
import se.klarna.business.customer.registry.domain.Customer;
import se.klarna.business.customer.registry.exception.ChildNotFoundException;
import se.klarna.business.customer.registry.exception.CustomerNotFoundException;
import se.klarna.business.customer.registry.service.CustomerRegistryService;
import se.klarna.business.customer.registry.service.CustomerRegistryServiceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CustomerRegistryTest {

    private final CustomerRegistryService customerRegistryService = new CustomerRegistryServiceImpl();

    private static final String VALID_SSN = "195312545963";
    private static final String INVALID_SSN = "308112314545";

    @Test
    public void throwAnIllegalArgumentExceptionWhenAnNullCustomerIsSaved() {
        assertThrows(IllegalArgumentException.class, ()-> customerRegistryService.addCustomer(null));
    }

    @Test
    public void throwAnIllegalArgumentExceptionWhenAnEmptyCustomerIsSaved() {
        assertThrows(IllegalArgumentException.class, ()-> customerRegistryService.addCustomer(new Customer()));
    }

    @Test
    public void throwAnIllegalArgumentExceptionWhenACustomerWithAnEmptySsnIsSaved() {
        Customer customer = new Customer();
        customer.setSsn("");
        assertThrows(IllegalArgumentException.class, ()-> customerRegistryService.addCustomer(customer));
    }

    @Test
    public void throwAnIllegalArgumentExceptionWhenACustomerWithAnInvalidSsnIsSaved() {
        Customer customer = new Customer();
        customer.setSsn(INVALID_SSN);
        assertThrows(IllegalArgumentException.class, ()-> customerRegistryService.addCustomer(customer));
    }

    @Test
    public void returnNullWhenAValidNewCustomerIsSaved() {
        Customer customer = new Customer();
        customer.setSsn(VALID_SSN);
        assertNull(customerRegistryService.addCustomer(customer));
    }

    @Test
    public void returnTheUpdatedCustomerWhenAnExistedCustomerIsSaved() {
        Customer customer = createCustomerWithChildren();
        customerRegistryService.addCustomer(customer);
        assertNotNull(customerRegistryService.addCustomer(customer));
    }

    @Test
    public void throwAnIllegalArgumentExceptionWhenRetrievingCustomerWithANullSsn() {
        assertThrows(IllegalArgumentException.class, ()-> customerRegistryService.getCustomerBySsn(null));
    }

    @Test
    public void throwAnIllegalArgumentExceptionWhenRetrievingCustomerWithAnEmptySsn() {
        assertThrows(IllegalArgumentException.class, ()-> customerRegistryService.getCustomerBySsn(""));
    }

    @Test
    public void throwAnIllegalArgumentExceptionWhenRetrievingCustomerWithAnInvalidSsn() {
        assertThrows(IllegalArgumentException.class, ()-> customerRegistryService.getCustomerBySsn(INVALID_SSN));
    }


    @Test
    public void throwACustomerNotFoundExceptionWhenNoCustomerIsFoundAccordingToAValidSsn() {
        assertThrows(CustomerNotFoundException.class, ()-> customerRegistryService.getCustomerBySsn(VALID_SSN));
    }

    @Test
    public void returnACustomerWhenNoCustomerIsFoundAccordingToAValidSsn() {
        customerRegistryService.addCustomer(createCustomerWithChildren());
        Customer response = customerRegistryService.getCustomerBySsn(VALID_SSN);
        assertEquals(VALID_SSN, response.getSsn());
    }

    @Test
    public void throwAnIllegalArgumentExceptionWhenFetchingOldestChildByProvidingNullCustomerSsn() {
        assertThrows(IllegalArgumentException.class, ()-> customerRegistryService.getOldestChildByCustomerSsn(null));
    }

    @Test
    public void throwAnIllegalArgumentExceptionWhenFetchingOldestChildByProvidingEmptyCustomerSsn() {
        assertThrows(IllegalArgumentException.class, ()-> customerRegistryService.getOldestChildByCustomerSsn(" "));
    }

    @Test
    public void throwAnIllegalArgumentExceptionWhenFetchingOldestChildByProvidingInvalidCustomerSsn() {
        assertThrows(IllegalArgumentException.class, ()-> customerRegistryService.getOldestChildByCustomerSsn(INVALID_SSN));
    }

    @Test
    public void throwACustomerNotFoundExceptionWhenFetchingOldestChildOfAnUnavailableCustomer() {
        assertThrows(CustomerNotFoundException.class, ()-> customerRegistryService.getOldestChildByCustomerSsn(VALID_SSN));
    }

    @Test
    public void throwAChildNotFoundExceptionWhenFetchingOldestChildOfACustomerWhoHasNoChildren() {
        customerRegistryService.addCustomer(createCustomer());
        assertThrows(ChildNotFoundException.class, ()-> customerRegistryService.getOldestChildByCustomerSsn(VALID_SSN));
    }

    @Test
    public void returnTheOldestChildWhenFetchingOldestChildOfACustomer() {
        customerRegistryService.addCustomer(createCustomerWithChildren());
        Child oldestChild = customerRegistryService.getOldestChildByCustomerSsn(VALID_SSN);
        assertNotNull(oldestChild);
        assertEquals(Date.valueOf(LocalDate.now()), oldestChild.getBirthday());
    }

    private Customer createCustomerWithChildren() {
        final Customer customer = createCustomer();
        final Child child1 = createRadomChild("Daniel", Date.valueOf(LocalDate.now()));
        final Child child2 = createRadomChild("Susan", Date.valueOf(LocalDate.now().plusDays(1)));
        customer.setChildren(List.of(child1, child2));
        return customer;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setSsn(VALID_SSN);
        customer.setName("siqi");
        customer.setSpouse("yuting");
        return customer;
    }

    private Child createRadomChild(String name, Date birthday) {
        final Child child = new Child();
        child.setName(name);
        child.setBirthday(birthday);
        return child;
    }

}
