package com.example.Employee.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Employee.api.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    
}