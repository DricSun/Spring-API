package com.example.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.model.Employee;
import com.example.api.model.EmployeeCategory;
import com.example.api.service.EmployeeService;

@RestController
public class EmployeeController {

    @Autowired // permet d'appeler les méthodes en du service pour comm avec la bdd// (getEmployees)
    private EmployeeService employeeService;

    /**
     * Read - Get all employees
     * 
     * @return - An Iterable object of Employee full filled
     */
    @GetMapping("/employees") // Cela signifie que les requetes HTTP renverrons la liste de tous les employees
    public Iterable<Employee> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/employees/{id}")
    public Optional<Employee> getEmployee(long id){
        return employeeService.getEmployee(id);
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/data") // Cela signifie que les requetes HTTP renverrons la liste de tous les employees
    public ResponseEntity<Employee> loadData() {
        Employee newEmployee = employeeService
        .saveEmployee(new Employee("Nicolas", "Simon", "test@test.fr", "password", 0, null));
        return new ResponseEntity<>(newEmployee, HttpStatus.NO_CONTENT);
    }

    @GetMapping("employees/categories")
    public Iterable<Employee> getEmployeesByCategory(EmployeeCategory employeecategory) {
        return  employeeService.getEmployeesByCategory(employeecategory);
    }
    
    @PutMapping("employees/updatesalary")
    public void updateSalary(long id, double salary){
        
    }

}
