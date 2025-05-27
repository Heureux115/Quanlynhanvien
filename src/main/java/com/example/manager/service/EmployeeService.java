package com.example.manager.service;

import com.example.manager.nhanvien.Salary;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    public double tinhLuong(Salary salary) {
        double luongNgay = salary.getLuongcoban() / 26.0;
        double phatNgayNghi = luongNgay * 0.5;
        double tong = (luongNgay * salary.getNgaylam()) - (salary.getNgaynghi() * phatNgayNghi);
        if (tong < 0) tong = 0;
        double thuePhanTram = salary.getThue();
        tong = tong - (tong * thuePhanTram);
        return tong;
    }

}
