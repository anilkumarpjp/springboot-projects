package com.emids.springbatchkafka.service;

import com.emids.springbatchkafka.entity.Employee;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.List;

@Service
public class EmployeeConsumer {

    @KafkaListener(topics = "quartzTopic", groupId = "quartzGroup")
    public void consumeFromTopic(String message){
        System.out.println("Employee Message consumed :" + message);
        if (null != message){
            readAllDataAtOnce(message);
        }
    }

    public static void readAllDataAtOnce(String file)
    {
        try {
            FileReader filereader = new FileReader(file);

            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            for (String[] row : allData) {
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
