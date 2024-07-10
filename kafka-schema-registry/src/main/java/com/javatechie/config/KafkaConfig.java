package com.javatechie.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    // get the topic name from application.yml/(application.properties) using @Value() annotation
    @Value("${topic.name}") // fetch the value mentioned under {} from application.yml
    private String topicName;   // topic name will contain "javatechie-avro" fetched from application.yml

    // create a topic using NewTopic
    @Bean
    public NewTopic createTopic(){
        return new NewTopic(topicName,3, (short)1); // (topic_name, partitions, replication-factor)
    }

}
