package com.example.manager.config;

import com.example.manager.nhanvien.Employee;
import com.example.manager.nhanvien.Salary;
import com.example.manager.repository.EmployeeRepository;
import com.example.manager.repository.SalaryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(EmployeeRepository employeeRepo, SalaryRepository salaryRepo, BCryptPasswordEncoder encoder) {
        return args -> {
            Employee manager = new Employee();
            manager.setName("admin");
            manager.setPassword(encoder.encode("123456"));
            manager.setChucvu("QUANLY");
            manager.setAge(35);
            manager.setGender("Nam");
            manager.setDonvicongtac("Phòng Kế hoạch");
            employeeRepo.save(manager);

            Employee staff = new Employee();
            staff.setName("staff");
            staff.setPassword(encoder.encode("123456"));
            staff.setChucvu("NHANVIEN");
            staff.setAge(25);
            staff.setGender("Nữ");
            staff.setDonvicongtac("Phòng Nhân sự");
            employeeRepo.save(staff);

            // Tạo bảng lương cho mỗi người
            Salary salary1 = new Salary();
            salary1.setEmployee(manager);
            salary1.setLuongcoban(30000);
            salary1.setNgaylam(22);
            salary1.setNgaynghi(2);
            salary1.setHesoluong(3.2);
            salary1.setThue(0.1);

            salaryRepo.save(salary1);

            Salary salary2 = new Salary();
            salary2.setEmployee(staff);
            salary2.setLuongcoban(30000);
            salary2.setNgaylam(20);
            salary2.setNgaynghi(1);
            salary2.setThue(0.1);
            salary2.setHesoluong(2.5);
            salaryRepo.save(salary2);

        };
    };
};

