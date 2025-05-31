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

    @GetMapping("/update")
    public String showUpdateForm() {
        return "salary_update";
    }

    @PostMapping("/update")
    public String updateSalary(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("ngaylam") int ngayLam,
            @RequestParam("ngaynghi") int ngayNghi,
            Model model) {

        Optional<Employee> empOpt = salaryService.findEmployeeById(employeeId);
        if (empOpt.isEmpty()) {
            model.addAttribute("message", "Nhân viên không tồn tại");
            return "salary_update";
        }

        String result = salaryService.updateSalaryDays(employeeId, ngayLam, ngayNghi);
        if ("SUCCESS".equals(result)) {
            model.addAttribute("message", "Cập nhật thành công");
            model.addAttribute("employeeName", empOpt.get().getName());
        } else {
            model.addAttribute("message", "Cập nhật thất bại");
        }

        return "salary_update";
    }

    // API trả về tên nhân viên theo ID
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
