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


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SalaryService salaryService;


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
    @GetMapping("/update/salary")
    public String showUpdateForm() {
        return "salary_update";
    }

    @PostMapping("/update/salary")
    public String addSalary(@RequestParam Long employeeId,
                            @RequestParam("hesoluong") double hesoluong,
                            @RequestParam("thue") double thue,
                            @RequestParam("ngaylam") int ngaylam,
                            @RequestParam("ngaynghi") int ngaynghi,
                            Model model) {

        Optional<Employee> empOpt = salaryService.findEmployeeById(employeeId);
        if (empOpt.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy nhân viên với ID: " + employeeId);
            return "salary_update";
        }

        Employee employee = empOpt.get();

        // Tìm bản ghi Salary, nếu không có thì tạo mới
        Salary salary = salaryService.findByEmployee(employee)
                .orElse(new Salary());

        // Gán dữ liệu mới
        salary.setEmployee(employee);
        salary.setHesoluong(hesoluong);
        salary.setThue(thue / 100); // Nếu nhập phần trăm từ form
        salary.setNgaylam(ngaylam);
        salary.setNgaynghi(ngaynghi);

        // Không cần setLuongcoban nữa

        double tongLuong = employeeService.tinhLuong(salary);

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