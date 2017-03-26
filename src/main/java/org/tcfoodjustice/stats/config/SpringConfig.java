package org.tcfoodjustice.stats.config;

/**
 * Created by andrew.larsen on 3/25/2017.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("org.tcfoodjustice")
public class SpringConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
