package com.emids.kafka.service;

import com.emids.kafka.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class EmployeeProducer {

    private static final String topic = "employeeData";
    @Autowired
    KafkaTemplate<String, Employee> kafkaTemplate;

    public void publishEmployeeData(Employee employee){
        kafkaTemplate.send(topic, employee);
    }

}
