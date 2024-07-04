package com.javatechie.controller;

import com.javatechie.dto.Customer;
import com.javatechie.service.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer-app")
public class EventController {
    @Autowired  // inject dependency of service class i.e KafkaMessagePublisher that will call the publish method to publish message to kafka topic
    private KafkaMessagePublisher publisher;

    // publish the message to the kafka topic via this endpoint
    @GetMapping("/publish/{messsage}")
    public ResponseEntity<?> publishMessage(@PathVariable String messsage){
        try {
            for(int i = 0; i < 10000; i++){ // run 10000 times to test whether our data goes to different partitions or not
                publisher.sendMessageToTopic(messsage + " : " +i); // call the sendMessageToTopic(message) to publish message to the topic
            }
            return ResponseEntity.ok("Message Published Successfully...");
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // endpoint for publishing/sending json object(customer object) to kafka topic
    @PostMapping("/publish")
    public void sendEvents(@RequestBody Customer customer){
        publisher.sendEventsToTopic(customer);  // send Customer object to kafka topic by calling publisher.sendEventsToTopic(customer);
    }
}
