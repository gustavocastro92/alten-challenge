package br.com.gustavo.altenchallenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class DependencyFactory {

    @Bean
    private Clock clock() {
        return Clock.systemDefaultZone();
    }
}
