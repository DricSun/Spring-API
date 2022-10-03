package com.example.Employee.api.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Employee.api.model.Employee;
import com.example.Employee.api.model.EmployeeCategory;
import com.example.Employee.api.service.EmployeeService;

@RestController
public class EmployeeController {

    @Autowired // permet d'appeler les méthodes en du service pour comm avec la bdd//
               // (getEmployees)
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

    @GetMapping("/employees/{uuid}")
    public ResponseEntity<Employee> getEmployee(@PathVariable UUID uuid) {
        try {
            Optional<Employee> employee = employeeService.getEmployee(uuid);
            if (employee.isPresent()) {
                return new ResponseEntity<Employee>(employee.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<Employee>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employees/{uuid}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID uuid) {
        employeeService.deleteEmployee(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/data") // Cela signifie que les requetes HTTP renverrons la liste de tous les employees
    public ResponseEntity<Employee> loadData() {
        Employee newEmployee = employeeService
                .saveEmployee(
                        new Employee("Nicolas", "Simon", "test@test.fr", "password", 0, EmployeeCategory.CATEGORYA));
        return new ResponseEntity<>(newEmployee, HttpStatus.NO_CONTENT);
    }

    @GetMapping("employees/categories")
    public Iterable<Employee> getEmployeesByCategory(EmployeeCategory employeecategory) {
        return employeeService.getEmployeesByCategory(employeecategory);
    }

    @PutMapping("employees/{uuid}")
    public void updateSalary(@PathVariable UUID uuid, @RequestParam double salary) {
        employeeService.updateSalary(uuid, salary);
    }
}
