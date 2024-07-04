package com.javatechie.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {
    // create a bean of NewTopic class so as to create topic in spring boot application provided by kafka

    @Bean
    public NewTopic newTopic(){
        return new NewTopic("javatechie-demo", 5, (short)1);
    }

}
