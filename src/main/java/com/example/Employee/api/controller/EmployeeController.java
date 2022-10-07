package com.example.Employee.api.controller;

import java.util.List;
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

    @GetMapping("employees/category/{employeecategory}")
    public ResponseEntity <Iterable<Employee>> getEmployeesByCategory(@PathVariable("employeecategory") String employeecategoryName) {
            // si j'ai rien
            if(employeecategoryName == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            //si j'ai  au moins 1 caractère mais ce n'est pas de l'alphabet
            if(employeecategoryName.length() >=1 && employeecategoryName.matches("^[a-zA-Z]*$")== false){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(EmployeeCategory.isValueisValueEnum(employeecategoryName)){
                EmployeeCategory employeeCategory = EmployeeCategory.valueOf(employeecategoryName);
                Iterable<Employee> listEmployee = employeeService.getEmployeesByCategory(employeeCategory);
                return new ResponseEntity<Iterable<Employee>>(listEmployee, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           
        }

            //condition verifier caractere speciaux / nombre 
        //    if(employeecategoryName.matches("^[a-zA-Z]*$")){
        //             return new ResponseEntity<>(HttpStatus.OK);
        //         }
        //         else{
        //             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //         }

        // if(employeecategoryName != null && employeecategoryName.length() > 0){
        //     EmployeeCategory employeeCategory = EmployeeCategory.valueOf(employeecategoryName.toUpperCase());
        //     Iterable<Employee> listEmployee = employeeService.getEmployeesByCategory(employeeCategory);
        //     return new ResponseEntity<Iterable<Employee>>(listEmployee, HttpStatus.OK);
        // }else{
        //     return new ResponseEntity<Iterable<Employee>>( HttpStatus.NO_CONTENT);
        // }

                //test2
                // try {
        //     // si j'ai rien 
        //     if(employeecategoryName == null){
        //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //     }
        //     // si j'ai quelque chose mais ce n'est pas de l'alphabet
        //     if(employeecategoryName.length() >=1 && employeecategoryName.matches("^[a-zA-Z]*$") == false){
        //         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //     }
        //     // Si ma category (parametres) n'est pas une valeur de ma category 
        //     EmployeeCategory employeeCategory = EmployeeCategory.valueOf(employeecategoryName.toUpperCase());
        //     Iterable<Employee> listEmployee = employeeService.getEmployeesByCategory(employeeCategory);
        //     return new ResponseEntity<Iterable<Employee>>(listEmployee, HttpStatus.OK);
        // } catch (Exception e) {
        //     return new ResponseEntity<Iterable<Employee>>(HttpStatus.INTERNAL_SERVER_ERROR);
        // }
        

    @GetMapping("employees/category/maxsalary")
    public ResponseEntity<Double> getMaxSalarybyCategory(EmployeeCategory employeeCategory){
        try {
            if(employeeCategory != null){
                double maxiSalaryByCategory = employeeService.getMaxSalarybyCategory(employeeCategory);
                return new ResponseEntity<Double>(maxiSalaryByCategory, HttpStatus.OK);
                
            }else{
                return new ResponseEntity<Double>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            return new ResponseEntity<Double>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("employees/{uuid}")
    public ResponseEntity<Void> updateSalary(@PathVariable UUID uuid, @RequestParam double salary) {
        employeeService.updateSalary(uuid, salary);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping("employees/employeescategory/")
    public ResponseEntity<Void> AugmentationSalaireByCategory(EmployeeCategory employeeCategory, double salary){
        try {
            if(salary > 0){
             employeeService.variationSalary(employeeCategory, 2);
             return new ResponseEntity<>(HttpStatus.OK); 
            
            }
            else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
    }

}

