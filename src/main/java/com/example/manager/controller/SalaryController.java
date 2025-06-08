package com.example.manager.controller;

import com.example.manager.entity.Employee;
import com.example.manager.entity.Salary;
import com.example.manager.entity.User;
import com.example.manager.repository.SalaryRepository;
import com.example.manager.repository.UserRepository;
import com.example.manager.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/view")
    public String viewSalary(@RequestParam(value = "month", required = false) Integer month,
                             @RequestParam(value = "year", required = false) Integer year,
                             Model model) {

        if (month == null) month = 1;
        if (year == null) year = 2025;

        List<Salary> salaries = salaryRepository.findByMonthAndYear(month, year);
        Map<Long, Double> luongMap = new HashMap<>();

        for (Salary s : salaries) {
            double luong = salaryService.tinhLuong(s);
            luongMap.put(s.getId(), luong);
        }

        model.addAttribute("salaries", salaries);
        model.addAttribute("luongMap", luongMap);
        model.addAttribute("month", month);
        model.addAttribute("year", year);

        return "salary_view";
    }

    @GetMapping("/salary/personal")
    public String viewPersonalSalary(
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            Model model,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails
    ) {
        if (month == null) month = 1;
        if (year == null) year = 2025;

        String username = userDetails.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Employee employee = user.getEmployee();
        if (employee == null) {
            model.addAttribute("error", "Tài khoản chưa được gán cho nhân viên nào.");
            return "salary_personal";
        }

        Long employeeId = employee.getId();

        List<Salary> salaries = salaryService.getSalariesByUserAndMonthYear(employeeId, month, year);

        Map<Long, Double> luongMap = new HashMap<>();
        for (Salary s : salaries) {
            double luong = salaryService.tinhLuong(s);
            luongMap.put(s.getId(), luong);
        }

        model.addAttribute("salaries", salaries);
        model.addAttribute("luongMap", luongMap);
        model.addAttribute("month", month);
        model.addAttribute("year", year);

        return "salary_personal";
    }


    @GetMapping("/update")
    public String showUpdateForm() {
        return "salary_update";
    }

    @PostMapping("/update")
    public String updateSalary(@RequestParam Long employeeId,
                               @RequestParam("hesoluong") double hesoluong,
                               @RequestParam("thue") double thuePercent,
                               @RequestParam("ngaylam") int ngaylam,
                               @RequestParam("ngaynghi") int ngaynghi,
                               @RequestParam("thang") int thang,
                               @RequestParam("nam") int nam,
                               Model model) {

        Optional<Employee> empOpt = salaryService.findEmployeeById(employeeId);
        if (empOpt.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy nhân viên với ID: " + employeeId);
            return "salary_update";
        }

        Employee employee = empOpt.get();

        Optional<Salary> salaryOpt = salaryService.findByEmployeeAndMonthAndYear(employee, thang, nam);

        Salary salary = salaryOpt.orElse(new Salary());

        salary.setEmployee(employee);
        salary.setHesoluong(hesoluong);
        salary.setThue(thuePercent / 100.0);
        salary.setNgaylam(ngaylam);
        salary.setNgaynghi(ngaynghi);
        salary.setMonth(thang);
        salary.setYear(nam);
        salary.setLuongcoban(100000);

        double tongLuong = salaryService.tinhLuong(salary);

        salaryService.saveSalary(salary);

        model.addAttribute("message", "Cập nhật lương cho nhân viên '" + employee.getName() + "' thành công. Lương thực nhận: " + tongLuong);
        return "salary_update";
    }



    @GetMapping("/employeeName")
    @ResponseBody
    public Map<String, Object> getEmployeeName(@RequestParam Long employeeId) {
        Optional<Employee> empOpt = salaryService.findEmployeeById(employeeId);
        Map<String, Object> result = new HashMap<>();
        if (empOpt.isPresent()) {
            result.put("exists", true);
            result.put("name", empOpt.get().getName());
        } else {
            result.put("exists", false);
        }
        return result;
    }

}
