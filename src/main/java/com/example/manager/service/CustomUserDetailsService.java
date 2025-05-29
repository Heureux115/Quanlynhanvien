package com.example.manager.service;

import com.example.manager.nhanvien.Employee;
import com.example.manager.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByName(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(employee.getName())
                .password(employee.getPassword()) // nhớ password phải được mã hóa
                .roles(employee.getChucvu()) // Vai trò (ROLE_QuanLy hoặc ROLE_NhanVien)
                .build();
    }
}
