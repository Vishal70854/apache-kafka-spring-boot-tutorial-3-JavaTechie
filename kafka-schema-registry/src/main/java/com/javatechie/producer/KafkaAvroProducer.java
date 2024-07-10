package com.javatechie.producer;

import com.javatechie.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class KafkaAvroProducer {

    // get the topic name from application.yml/(application.properties) using @Value() annotation
    @Value("${topic.name}") // fetch the value mentioned under {} from application.yml
    private String topicName;   // topic name will contain "javatechie-avro" fetched from application.yml


    // inject dependency of KafkaTemplate<>
    @Autowired
    private KafkaTemplate<String, Employee> template;

    public void send(Employee employee){
        CompletableFuture<SendResult<String, Employee>> future = template.send(topicName, UUID.randomUUID().toString(),employee);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + employee +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        employee + "] due to : " + ex.getMessage());
            }
        });
    }


}
