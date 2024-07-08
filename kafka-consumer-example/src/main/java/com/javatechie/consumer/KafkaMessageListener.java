package com.javatechie.consumer;

import com.javatechie.dto.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
public class KafkaMessageListener {

    // create logger to read and print all data from topic to console of spring boot application
    Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    // now we will define a method consume(String message) which will consume String data from kafka topic
    // as the producer is publishing String data to topic so consumer will consume String data from topic

    // we will read data from topic with the help of @KafkaListener(topics = "the topic name", groupId="mention the consumer group id")
    // by @KafkaListener the application will know that it will read messages from the mentioned topic name which was produced by producer application

//    @KafkaListener(topics = "javatechie-topic", groupId = "jt-group")
//    public void consumeEvents(Customer customer){   // here it will consume/read Customer object(json object) from kafka topic
//        logger.info("consumer consumed the message user : {}", customer.toString()); // print the consumed message from topic to console
//    }

    // kafka listener to listen to String value from kafka topic
    // overload @KafkaListener() to tell our consumer that it should read messages from specific partition from the mentioned kafka topic
    // below is the overloaded @KafkaListener() annotation which will read messages from specific topic
    // @KafkaListener(topics = "javatechie-topic", groupId = "jt-group")    // default @KafkaListener() annotation to read messages from any partitions in topic
    @KafkaListener(topics = "javatechie-topic1", groupId = "jt-group",
                topicPartitions = {@TopicPartition(topic = "javatechie-topic1",partitions = {"2"})})        // get messages from topic -"javatechie-topic1" from partition "2"
    public void consumeEvents(String customer){   // here it will consume/read Customer object(json object) from kafka topic
        logger.info("consumer consumed the message from specific partion with user : {}", customer); // print the consumed message from topic to console
    }

//    ====================================================================

    // below methods are for understanding purpose of consumer group
//    // created multiple consumers in a single consumer group i.e jt-group-1 to consume messages from all partitions to different consumers
//    // in real time we should not create kafka listener in same
//    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group")
//    public void consume2(String message){
//        logger.info("consumer2 consumed the message user : {}", message); // print the consumed message from topic to console
//    }
//
//    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group")
//    public void consume3(String message){
//        logger.info("consumer3 consumed the message user : {}", message); // print the consumed message from topic to console
//    }
//
//    @KafkaListener(topics = "javatechie-demo-2", groupId = "jt-group")
//    public void consume4(String message){
//        logger.info("consumer4 consumed the message user : {}", message); // print the consumed message from topic to console
//    }

}
