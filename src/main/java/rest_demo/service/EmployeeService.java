package rest_demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rest_demo.dto.Employee;
import rest_demo.dto.EmployeeArray;
import rest_demo.entity.EmployeeEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeService { // demo api: http://dummy.restapiexample.com/

    RestTemplate rt;

    @Autowired
    public EmployeeService(RestTemplate rt) {
        this.rt = rt;
    }

    /**
     * Two ways to call 3rd party APIs:
     *  - use RestTemplate (*flexible)
     *  - use openFeign.FeignClient
     */

    public EmployeeEntity getEmployeeById(int id) {
        ResponseEntity<Employee> employee = rt.getForEntity("http://dummy.restapiexample.com/api/v1/employee/" + id, Employee.class);
        System.out.println(employee.getStatusCode()); // 200 OK
        System.out.println(employee.getBody());
        System.out.println(Objects.requireNonNull(employee.getBody()).getEmployee());
        return employee.getBody().getEmployee();
    }

    public List<EmployeeEntity> getAllEmployees() {
        ResponseEntity<EmployeeArray> employee = rt.getForEntity("http://dummy.restapiexample.com/api/v1/employees", EmployeeArray.class);
        System.out.println(employee.getStatusCode()); // 200 OK
        System.out.println(employee.getBody());
        return employee.getBody().getEmployees();
    }

    public List<EmployeeEntity> getEmployeeByAge(int age) {
        ResponseEntity<EmployeeArray> employee = rt.getForEntity("http://dummy.restapiexample.com/api/v1/employees", EmployeeArray.class);
        System.out.println(employee.getStatusCode()); // 200 OK
        System.out.println(employee.getBody());
        List<EmployeeEntity> employees = Objects.requireNonNull(employee.getBody()).getEmployees();
        List<EmployeeEntity> filteredEmployees = employees.stream().filter(e -> e.getAge() >= age).collect(Collectors.toList());
        System.out.println(Arrays.deepToString(employees.stream().filter(e -> e.getAge() >= age).toArray()));
        return filteredEmployees;
    }
}
