package rest_demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import rest_demo.entity.EmployeeEntity;

import java.util.List;

@Data
public class EmployeeArray {
    private String status;
    @JsonProperty("data")
    private List<EmployeeEntity> employees;
    private String message;
}
