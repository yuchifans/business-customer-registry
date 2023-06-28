package se.klarna.business.customer.registry.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("No customer Found!");
    }

}
