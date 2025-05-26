package com.example.manager.controller;

import com.example.manager.nhanvien.Employee;
import com.example.manager.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

    @GetMapping("/sort/name")
    public List<Employee> sortByName() {
        return employeeRepository.findAll(Sort.by("name"));
    }

    @GetMapping("/sort/id")
    public List<Employee> sortById() {
        return employeeRepository.findAll(Sort.by("id"));
    }

    @GetMapping("/search/name")
    public List<Employee> searchByName(@RequestParam String name) {
        return employeeRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}