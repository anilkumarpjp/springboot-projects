package com.emids.kafka.controller;

import com.emids.kafka.entity.Employee;
import com.emids.kafka.service.EmployeeProducer;
import com.emids.kafka.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    EmployeeProducer employeeProducer;
    @GetMapping("/publish")
    public String sendMessage(@RequestParam("message") String message){
        kafkaProducer.publishToTopic(message);
        return "Message " + message + " published successfully";
    }

    @PostMapping("/employee")
    public String sendEmployeeMessage(@RequestBody Employee employee){
        employeeProducer.publishEmployeeData(employee);
        return "Employee Message " + employee + " published successfully";
    }
}
