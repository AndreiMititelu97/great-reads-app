package org.greatreads.security.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DotenvConfig {

    private final ConfigurableEnvironment env;

    public DotenvConfig(ConfigurableEnvironment env) {
        this.env = env;
    }

    @PostConstruct
    public void loadDotenvIntoSpring() {
        Dotenv dotenv = Dotenv.load();
        Map<String, Object> dotenvProperties = new HashMap<>();

        dotenv.entries().forEach(entry -> dotenvProperties.put(entry.getKey(), entry.getValue()));

        env.getPropertySources().addFirst(new MapPropertySource("dotenvProperties", dotenvProperties));
    }
}
