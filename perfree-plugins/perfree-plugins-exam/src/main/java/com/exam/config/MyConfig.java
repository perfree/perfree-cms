package com.exam.config;

import com.exam.commons.TestConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public TestConfig getTestConfig() {
        return new TestConfig();
    }
}
