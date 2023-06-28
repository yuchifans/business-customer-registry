package se.klarna.business.customer.registry.exception;

public class ChildNotFoundException extends RuntimeException {

    public ChildNotFoundException() {
        super("No Child Found!");
    }

}
