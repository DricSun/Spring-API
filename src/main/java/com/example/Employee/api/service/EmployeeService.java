package com.example.Employee.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Employee.api.model.Employee;
import com.example.Employee.api.model.EmployeeCategory;
import com.example.Employee.api.repository.EmployeeRepository;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Optional peut renvoyer ou non 
    public Optional<Employee> getEmployee(final Long id) {
        return employeeRepository.findById(id);
    }
    //fonction qui permet d'obtenir la liste d'employee par catégories
    public Iterable<Employee> getEmployeesByCategory(EmployeeCategory employeecategory){
        Iterable<Employee> tableauEmployees = getEmployees();
        List<Employee> tableauGetEmployee = new ArrayList<Employee>();
        // element du tableau : tableau
        for(Employee employee : tableauEmployees){
            if(employee.getCategory() == employeecategory){
                tableauGetEmployee.add(employee);
            }
        }
        Iterable<Employee> retour = tableauGetEmployee;

        return retour;
    }
    
    public Iterable<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public void deleteEmployee(final Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee saveEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee;
    }

    //s'assurer que les employees d'une catégories n'ont pas un salaire supérieur à un employé d'une catégorie plus élevé 
     public Boolean verifyCoherenceEmployeeSalary(){
        boolean verifD = verifyCoherenceEmployeeSalaryByCategory(EmployeeCategory.CATEGORYD, EmployeeCategory.CATEGORYC);
        boolean verifC = verifyCoherenceEmployeeSalaryByCategory(EmployeeCategory.CATEGORYC, EmployeeCategory.CATEGROYB);
        boolean verifB = verifyCoherenceEmployeeSalaryByCategory(EmployeeCategory.CATEGROYB, EmployeeCategory.CATEGORYA);
        return verifD && verifC && verifB;
    }

    public Boolean verifyCoherenceEmployeeSalaryByCategory(EmployeeCategory employeeCategory1, EmployeeCategory employeeCategory2){
        Iterable<Employee> employeeCategory1list = getEmployeesByCategory(employeeCategory1);
        double minCategory2 = getMinSalarybyCategory(employeeCategory2);
        for(Employee employee : employeeCategory1list){
            if(employee.getSalary() > minCategory2){
                return false;
            }
        }
        return true;
    }

    //obtenir le salaire max d'un employee par catégorie 
    public double getMaxSalarybyCategory( EmployeeCategory employeeCategory){

        Iterable<Employee> listeEmployee = getEmployeesByCategory(employeeCategory);
        //prendre le prmeier element du tableau 
        double maxiSalaryByCategory = listeEmployee.iterator().next().getSalary();

        for(Employee employee : listeEmployee){
            if(employee.getSalary() > maxiSalaryByCategory){
                maxiSalaryByCategory = employee.getSalary();
            }
        }

        return maxiSalaryByCategory;
    }

    //obtenir le salaire min d'un employee par catégorie 
    public double getMinSalarybyCategory(EmployeeCategory employeeCategory){
        
        Iterable<Employee> listeEmployee = getEmployeesByCategory(employeeCategory);
        //prendre le prmeier element du tableau 
        double minSalaryByCategory = listeEmployee.iterator().next().getSalary();

        for(Employee employee : listeEmployee){
            if(employee.getSalary() < minSalaryByCategory){
                minSalaryByCategory = employee.getSalary();
            }
        }

        return minSalaryByCategory;
    }
    // augmentation salaire , diminution salaire en % des employees par catégories
    public void variationSalary(EmployeeCategory employeeCategory, double augmentationSalaryPourcentage){

        Iterable<Employee> listeEmployee = getEmployeesByCategory(employeeCategory);

        for(Employee employee : listeEmployee){

            double employeeSalary = employee.getSalary();
            
            double calculAugmentation = employeeSalary * (100 + augmentationSalaryPourcentage) /100;
            
            employee.setSalary(calculAugmentation);
        }

        //diminution salary
        variationSalary(employeeCategory, -2);
        
    }




    public void updateSalary(long id, double salary){
        
        
        Optional<Employee> optionnalEmployee = getEmployee(id);
        // si jamais un employe a ete trouver (optionnal pas vide) on execute
       if(optionnalEmployee.isPresent()){
        // .get() permet d avoir l'objt stocké dans l'optionnal
            Employee employee = optionnalEmployee.get();
            employee.setSalary(salary);
            // mettre a jour l'objet en base 
            employeeRepository.save(employee);
       }else{

       }
        


    }


}





