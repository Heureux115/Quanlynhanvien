package com.example.manager.service;

import com.example.manager.nhanvien.Employee;
import com.example.manager.nhanvien.Salary;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
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

}
