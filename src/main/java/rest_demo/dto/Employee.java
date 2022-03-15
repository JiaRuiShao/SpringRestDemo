package rest_demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import rest_demo.entity.EmployeeEntity;

@Data
public class Employee {
    private String status;
    @JsonProperty("data")
    private EmployeeEntity employee;
    private String message;
}
