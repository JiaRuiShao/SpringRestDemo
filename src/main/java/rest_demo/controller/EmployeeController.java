package rest_demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest_demo.dto.ErrorResponse;
import rest_demo.entity.EmployeeEntity;
import rest_demo.exception.EmployeeNotFoundException;
import rest_demo.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Api(value = "Employee", description = "REST API for Dummy API http://dummy.restapiexample.com/", tags = {"Employee"})
public class EmployeeController { // internally calls 3rd party APIs: http://dummy.restapiexample.com/

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(value = "gets an employee by id")
    @GetMapping("/id/{uid}")
    public EmployeeEntity getEmployee(@PathVariable("uid") int id) {
        EmployeeEntity employee = employeeService.getEmployeeById(id);
        if (employee == null) throw new EmployeeNotFoundException("EMPLOYEE_NOT_FOUND");
        return employee;
    }

    @ApiOperation(value = "gets all employees")
    @GetMapping("/")
    public List<EmployeeEntity> getAllEmployee() {
        List<EmployeeEntity> employees = employeeService.getAllEmployees();
        if (employees == null) throw new EmployeeNotFoundException("EMPLOYEE_NOT_FOUND");
        return employees;
    }

    @ApiOperation(value = "gets employees whose age is equal to or larger than the age provided")
    @GetMapping("/age")
    public List<EmployeeEntity> getEmployeeByAge(@RequestParam int age) {
        List<EmployeeEntity> employees = employeeService.getEmployeeByAge(age);
        if (employees == null) throw new EmployeeNotFoundException("EMPLOYEE_NOT_FOUND");
        return employees;
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandlerUserNotFound(Exception ex) {
        logger.error("Cannot find employee");
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
