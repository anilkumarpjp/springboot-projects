package com.emids.springbatchkafka.batch;

import com.emids.springbatchkafka.entity.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Employee(rs.getInt("employee_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("gender"),
                rs.getString("department"),
                rs.getString("job_title"),
                rs.getInt("years_of_experience"),
                rs.getInt("salary"));
    }
}
