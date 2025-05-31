package com.example.manager.config;

import com.example.manager.entity.Employee;
import com.example.manager.entity.Salary;
import com.example.manager.entity.User;
import com.example.manager.repository.EmployeeRepository;
import com.example.manager.repository.SalaryRepository;
import com.example.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Dataload implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Tạo user
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(passwordEncoder.encode("123456")); // mã hóa password
        userRepository.save(user1);

        // Tạo employee liên kết với user
        Employee emp1 = new Employee();
        emp1.setName("Nguyen Van A");
        emp1.setAge(30);
        emp1.setGender("Nam");
        emp1.setDonvicongtac("Phòng Kỹ Thuật");
        emp1.setChucvu("QUANLY");
        emp1.setUser(user1);

        // Cũng phải set employee cho user để đồng bộ 2 chiều
        user1.setEmployee(emp1);

        employeeRepository.save(emp1);

        // Tạo salary liên kết với employee
        Salary salary1 = new Salary();
        salary1.setNgaylam(22);
        salary1.setNgaynghi(2);
        salary1.setLuongcoban(300000);
        salary1.setThue(0.1);
        salary1.setHesoluong(1.5);
        salary1.setEmployee(emp1);

        salaryRepository.save(salary1);

        // Tương tự tạo thêm 1 user, employee, salary khác nếu muốn
        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword(passwordEncoder.encode("123456"));
        userRepository.save(user2);

        Employee emp2 = new Employee("Le Thi B", 25, "Nu", "Phong Ke Toan", "NHANVIEN");
        emp2.setUser(user2);
        user2.setEmployee(emp2);
        employeeRepository.save(emp2);

        Salary salary2 = new Salary(20, 4, 200000, 0.05, 1.0);
        salary2.setEmployee(emp2);
        salaryRepository.save(salary2);

        System.out.println("Test data loaded.");
    }
}
