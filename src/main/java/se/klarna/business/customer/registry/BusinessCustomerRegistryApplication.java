package se.klarna.business.customer.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("se.klarna.business.customer.registry.persistence.repository")
@EntityScan("se.klarna.business.customer.registry.persistence.domain")
public class BusinessCustomerRegistryApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessCustomerRegistryApplication.class, args);
    }
}
