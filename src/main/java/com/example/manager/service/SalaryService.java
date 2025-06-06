package com.example.manager.service;

import com.example.manager.entity.Employee;
import com.example.manager.entity.Salary;
import com.example.manager.repository.EmployeeRepository;
import com.example.manager.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.sql.Types.NULL;

@Service
public class SalaryService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    public Optional<Salary> findByEmployee(Employee employee) {
        return salaryRepository.findByEmployee(employee);
    }

    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public String addSalary(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        Employee employee = optEmp.get();
        Salary salary = salaryRepository.findByEmployee(employee)
                .orElseGet(() -> {
                    Salary s = new Salary();
                    s.setEmployee(employee);
                    s.setNgaylam(0);
                    s.setNgaynghi(0);
                    s.setLuongcoban(100000);
                    s.setThue(0.05);
                    s.setHesoluong(1);
                    s.setMonth(NULL);
                    s.setYear(NULL);
                    return s;
                });

        salaryRepository.save(salary);

        return "SUCCESS";
    }

    public double tinhLuong(Salary salary) {
        double luongNgay = salary.getLuongcoban();
        double phatNgayNghi = luongNgay * 0.5;
        double tong = ((luongNgay * salary.getNgaylam()) - (salary.getNgaynghi() * phatNgayNghi)) * salary.getHesoluong();
        if (tong < 0) tong = 0;
        double thuePhanTram = salary.getThue();
        tong = tong - (tong * thuePhanTram);
        tong = tong * salary.getHesoluong();
        return tong;
    }
    public Salary saveSalary(Salary salary) {
        return salaryRepository.save(salary);
    }

    public Optional<Salary> findByEmployeeAndMonthAndYear(Employee employee, int month, int year) {
        return salaryRepository.findByEmployeeAndMonthAndYear(employee, month, year);
    }

    public List<Salary> getSalariesByUserAndMonthYear(long employeeId, int month, int year) {
        return salaryRepository.findByEmployeeUserIdAndMonthAndYear(employeeId, month, year);
    }

}
