package com.javatechie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    // to publish/push messages to kafka topic from a class we have to use
    // KafkaTemplate<key, value> so that it can send messages to kafka topic
    @Autowired  // inject dependency of KafkaTemplate in this class
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessageToTopic(String message){

        // we have not configured topic name or topic manually till now, we want to see whether our application creates it on our behalf
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("javatechie-demo-2", message);
        future.whenComplete((result, ex) -> {
                    if (ex == null) {   // message is sent to topic without exception. we are printing the metadata of message
                        System.out.println("Sent Message=[" + message +
                                "] with offset=[" + result.getRecordMetadata().offset() + "]");
                    }
                    else{   // we have exception while sending message to the topic
                        System.out.println("Sent Message=[" + message +
                                "] due to " + ex.getMessage());
                    }
        });

    }

}
