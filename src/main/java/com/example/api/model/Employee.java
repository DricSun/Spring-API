package com.example.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data // Permet d'importer Lombok pas besoin de getter et setter
@Entity // Indique que la classe Employee est une entité en BDD
@Table(name = "employees") // Indique le nom de la table associées en BDD
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    private double salary;

    @Enumerated(EnumType.STRING)
    private EmployeeCategory category;


    public Employee(String firstName, String lastName, String email, String password, double salary, EmployeeCategory employeeCategory) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.category = employeeCategory;
    }

    public void print(){
        System.out.println(this.firstName + this.lastName);
    }
}
