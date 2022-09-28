package com.example.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.model.Employee;

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

    

}
