package com.example.manager.config;

import com.example.manager.entity.Employee;
import com.example.manager.entity.Salary;
import com.example.manager.entity.User;
import com.example.manager.repository.EmployeeRepository;
import com.example.manager.repository.SalaryRepository;
import com.example.manager.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired private UserRepository userRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private SalaryRepository salaryRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        User user1 = new User("admin", passwordEncoder.encode("123456"));
        userRepository.save(user1);

        Employee emp1 = new Employee("Lưu Đức Anh", 30, "Nam", "Ban quản lý", "QUANLY");
        emp1.setUser(user1);
        user1.setEmployee(emp1);
        employeeRepository.save(emp1);

    }
}

