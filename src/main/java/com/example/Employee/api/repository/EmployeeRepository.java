package com.example.Employee.api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Employee.api.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Optional<Employee> findOneByUuid(UUID uuid);

    void deleteEmployeeByUuid(UUID uuid);
    
}