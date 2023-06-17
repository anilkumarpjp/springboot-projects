package com.emids.springbatchkafka.batch;

import com.emids.springbatchkafka.entity.Employee;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class EmployeeReader extends JdbcCursorItemReader<Employee> implements ItemReader<Employee> {

    public EmployeeReader(@Autowired DataSource dataSource){
        setDataSource(dataSource);
        setSql("select employee_id,first_name,last_name,email,gender,"
                + "department,job_title,years_of_experience,salary from employee");
        setFetchSize(100);
        setRowMapper(new EmployeeRowMapper());
    }
}
