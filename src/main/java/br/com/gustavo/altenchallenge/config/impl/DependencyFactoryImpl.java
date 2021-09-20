package br.com.gustavo.altenchallenge.config.impl;

import br.com.gustavo.altenchallenge.config.DependencyFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class DependencyFactoryImpl implements DependencyFactory {

    @Bean
    private Clock clock() {
        return Clock.systemDefaultZone();
    }
}
