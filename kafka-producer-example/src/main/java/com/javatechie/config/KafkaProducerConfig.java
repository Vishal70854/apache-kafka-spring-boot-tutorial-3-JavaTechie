package com.javatechie.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    // create a bean of NewTopic class so as to create topic in spring boot application provided by kafka

    @Bean
    public NewTopic newTopic(){
        return new NewTopic("javatechie-demo", 5, (short)1);
    }

    // provide Producer Configuration to publish messages to kafka topic
    // we will provide this configuration by creating a bean of ProducerConfig
    @Bean
    public Map<String, Object> producerConfig(){
        Map<String, Object> props = new HashMap<>();    // now all the key which were defined in application.yml needs to be defined here below
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");    // bootstrap-server is configured as localhost:9092
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  // key serializer is StringSerializer
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);    // value serializer is JsonSerializer since we want to send Json object

        return props;
    }

    // now by giving ProducerConfig object we can create ProducerFactory object/bean
    @Bean
    public ProducerFactory<String, Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    // now we will create KafkaTemplate<K,V> bean/object by giving ProducerFactory object
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
