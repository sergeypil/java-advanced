package net.serg.cloud.service.impl.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "net.serg.cloud.service.impl.repository")
@EntityScan("net.serg.cloud.service.impl.entity")
public class AppConfig {
}
