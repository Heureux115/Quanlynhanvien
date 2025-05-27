package com.example.manager.controller;

import com.example.manager.nhanvien.Salary;
import com.example.manager.repository.SalaryRepository;
import com.example.manager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Salary addSalary(@RequestBody Salary salary) {
        return salaryRepository.save(salary);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Salary> getSalariesByEmployee(@PathVariable Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId);
    }

    @GetMapping("/calculate/{salaryId}")
    public double calculateSalary(@PathVariable long salaryId) {
        Salary salary = salaryRepository.findById(salaryId).orElseThrow();
        return employeeService.tinhLuong(salary);
    }



}