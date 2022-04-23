package pl.futurecollars.invoicing.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "pl.futurecollars.invoicing")
public class JpaConfiguration {
}
