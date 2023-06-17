package com.emids.springbatchkafka.config;

import com.emids.springbatchkafka.entity.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class EmployeeBatchConfig {

    @Value("${outputFilePath}")
    private String filePath;
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    private static final String topic = "quartzTopic";
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean(name = "processEmployeeData")
    public Job job(
            ItemReader<Employee> itemReader,
            ItemProcessor<Employee,Employee> itemProcessor,
            ItemWriter<Employee> itemWriter) {

        Step step = stepBuilderFactory.get("Load-employee") //name for step
                .<Employee,Employee>chunk(100)
                .reader(itemReader)
                .writer(flatFileItemWriter())
                .build();

        return jobBuilderFactory.get("Employee data batch processing") //name for job
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();

    }

    @Bean
    public FlatFileItemWriter<Employee> flatFileItemWriter(){
        FlatFileItemWriter<Employee> flatFileItemWriter =
                new FlatFileItemWriter<>();

        flatFileItemWriter.setResource
                (new FileSystemResource
                        (filePath));
        flatFileItemWriter
                .setLineAggregator(new DelimitedLineAggregator<Employee>()
                {{
                    setDelimiter(",");
                    setFieldExtractor(new BeanWrapperFieldExtractor<Employee>()
                    {{
                        String[] tokens = { "employeeId", "firstName", "lastName", "email", "gender", "department", "jobTitle", "yearsOfExperience", "salary" };
                        setNames(tokens);
                    }});

                }});

        flatFileItemWriter.setHeaderCallback
                (writer -> writer.write("Employee Id,First Name,Last Name,Email,Gender,Department,Job Title,Years Of Experience,Salary"));
            //kafkaTemplate.send(topic, "Data processing completed and data is generated to file : " + filePath);
        kafkaTemplate.send(topic, filePath);
        return flatFileItemWriter;
    }
}
