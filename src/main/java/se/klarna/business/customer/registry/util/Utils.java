package se.klarna.business.customer.registry.util;

import se.klarna.business.customer.registry.domain.Customer;

import java.util.regex.Pattern;

public class Utils {

    private static final String SSN_PATTERN = "^(19|20)(\\d{6}\\d{4})$";
    public static boolean isValid(String st) {
        return st != null && !st.isBlank() && Pattern.matches(SSN_PATTERN, st);
    }

    public static boolean isValid(Customer customer) {
        return customer != null
                && customer.getSsn() != null
                && !customer.getSsn().isBlank()
                && Pattern.matches(SSN_PATTERN, customer.getSsn());
    }
}
