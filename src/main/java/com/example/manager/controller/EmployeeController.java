package com.example.manager.controller;



import com.example.manager.entity.Employee;
import com.example.manager.entity.Salary;
import com.example.manager.repository.EmployeeRepository;
import com.example.manager.service.EmployeeService;
import com.example.manager.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import java.util.List;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/view")
    public String showAllEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee_view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee,
                              @ModelAttribute Salary salary,
                              @RequestParam String username,
                              Model model) {
        employeeService.saveEmployeeWithUser(employee, username);
        salaryService.addSalary(employee.getId());
        model.addAttribute("message", "Thêm nhân viên thành công");
        model.addAttribute("employee", new Employee()); // reset form
        return "employee_add";
    }
}