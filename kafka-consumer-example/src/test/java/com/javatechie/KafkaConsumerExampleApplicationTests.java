package com.javatechie;

import com.javatechie.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // run this Test application in random port everytime spring boot application starts
@Testcontainers // by using @Testcontainers annotation we are telling spring boot application that the below class is using some container
@Slf4j  // this annotation is used to print logs to the console
class KafkaConsumerExampleApplicationTests {

    // we are telling kafka container to provide latest version of kafka from the container
    @Container  // specifies that below kafka variable is a container which holds kafka container object
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    // configure the bootstrap server for kafka container
    @DynamicPropertySource
    // whenever we use test container we need to use this types of annotations. dont need to remember as of now
    public static void initKafkaProperties(DynamicPropertyRegistry registry){

        registry.add("spring.kafka.bootstrap-servers",kafka::getBootstrapServers); // get the bootstrap server for kafka from above kafka container
    }

    // inject dependency of KafkaTemplate<>
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // test if our Consumer is able to consume messages from kafka topic
    @Test
    public void testConsumeEvents(){
        log.info("testConsumeEvents logs execution started...");

        Customer customer = new Customer(34234, "test user", "test@gmail.com", "234234");
        // pass the customer object to our topic using kafka-template
        kafkaTemplate.send("javatechie-demo", customer);

        log.info("testConsumeEvents logs execution ended...");

        await().pollInterval(Duration.ofSeconds(3)) // define poll interval of 3 seconds
                .atMost(10, TimeUnit.SECONDS).untilAsserted(() -> { // we will wait minimum of 10 seconds once message is pushed to topic
                    // assert statement can be written to check object from db
                });

    }


}
