package com.javatechie.service;

import com.javatechie.dto.Customer;
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

    public void sendMessageToTopic(String message) {

        // we have not configured topic name or topic manually till now, we want to see whether our application creates it on our behalf
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("javatechie-demo-2", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {   // message is sent to topic without exception. we are printing the metadata of message
                System.out.println("Sent Message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {   // we have exception while sending message to the topic
                System.out.println("Sent Message=[" + message +
                        "] due to " + ex.getMessage());
            }
        });
    }

    // in the below method we want to send Customer object data(json object) using KafkaTemplate

    // kafka bydefault serializes String data into byte array to kafka topic and deserializes byte array data from topic to consumer in String
    // inorder to send object data(in json form) we need to configure the keySerializer and Value Serializer in application.yml/application.properties file
    // we need to tell kafka in application.yml that we will send json object and received json object to and from kafka topic
    public void sendEventsToTopic(Customer customer){
        try{
            // we have not configured topic name or topic manually till now, we want to see whether our application creates it on our behalf
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("javatechie-demo", customer);
            future.whenComplete((result, ex) -> {
                if (ex == null) {   // message is sent to topic without exception. we are printing the metadata of message
                    System.out.println("Sent Message=[" + customer.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                }
                else{   // we have exception while sending message to the topic
                    System.out.println("Sent Message=[" + customer.toString() +
                            "] due to " + ex.getMessage());
                }
            });
        }
        catch (Exception ex){
            System.out.println("ERROR : " +ex.getMessage());
        }
    }

}
