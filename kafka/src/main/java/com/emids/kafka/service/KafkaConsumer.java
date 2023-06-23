package com.emids.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "emids2", groupId = "emidsGroup")
    public void consumeFromTopic(String message){
        System.out.println("Message consumed :" + message);
    }
}
