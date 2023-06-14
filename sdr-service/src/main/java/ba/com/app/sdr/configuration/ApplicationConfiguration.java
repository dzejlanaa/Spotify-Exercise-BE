package ba.com.app.sdr.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import ba.com.app.commons.configuration.BaseApplicationConfiguration;
import ba.com.app.commons.configuration.N2bObjectMapper;

@EnableEurekaClient
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = { "ba.com.app.**.core.**" })
@EnableFeignClients(basePackages = "ba.com.app.sdr.core.client.feign")
public class ApplicationConfiguration extends BaseApplicationConfiguration {
    @Override
    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new N2bObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
