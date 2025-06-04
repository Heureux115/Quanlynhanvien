package com.example.manager.controller;

import com.example.manager.entity.Salary;
import com.example.manager.repository.SalaryRepository;
import com.example.manager.service.EmployeeService;
import com.example.manager.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.manager.entity.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/view")
    public String viewSalary(Model model) {
        List<Salary> salaries = salaryRepository.findAll();
        Map<Long, Double> luongMap = new HashMap<>();
        for (Salary s : salaries) {
            double luong = employeeService.tinhLuong(s);
            luongMap.put(s.getId(), luong);
        }
        model.addAttribute("salaries", salaries);
        model.addAttribute("luongMap", luongMap);
        return "salary_view";
    }


}
