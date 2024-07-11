package com.javatechie.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    // fetch the variable values from application.properties(yml) file by using @Value("${variable_name}")

    @Value("${app.topic.name}") // it fetches the app.topic.name variable value in the field topicName
    private String topicName;

    @Bean   // create a bean of kafka topic using NewTopic object with 3 partitions and 1 replication-factor
    public NewTopic createTopic() {
        return new NewTopic(topicName, 3, (short) 1);
    }

}