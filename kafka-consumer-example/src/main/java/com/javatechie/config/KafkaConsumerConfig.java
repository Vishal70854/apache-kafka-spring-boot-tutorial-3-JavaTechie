package com.javatechie.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    // provide Consumer Configuration to consume messages from kafka topic
    // we will provide this configuration by creating a bean of ConsumerConfig
    @Bean
    public Map<String, Object> consumerConfig(){
        Map<String, Object> props = new HashMap<>(); // now all the key which were defined in application.yml needs to be defined here below
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");  // bootstrap-server is configured as localhost:9092
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);  // key Deserializer is StringDeserializer
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);    // value serializer is JsonDeserializer
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.javatechie.dto"); // trust the mentioned package and accept json messages from that package

        return props;

//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.javatechie.dto");
//
//        return props;
    }

    // now by giving ConsumerConfig object we can create ConsumerFactory object/bean
    @Bean
    public ConsumerFactory<String,Object> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    // now we will create KafkaListenerConsumerFactory<> which will listen/read our messages from kafka topic
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
