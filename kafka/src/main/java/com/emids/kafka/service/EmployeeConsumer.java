package com.emids.kafka.service;

import com.emids.kafka.entity.Employee;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumer {

    @KafkaListener(topics = "employeeData", groupId = "emidsGroup")
    public void consumeFromTopic(Employee employee){
        System.out.println("Employee Message consumed :" + employee);
    }
}
