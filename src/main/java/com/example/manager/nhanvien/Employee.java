package com.example.manager.nhanvien;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table (name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private int age;
    private String gender;
    private String donvicongtac;
    private double hesoluong;
    private String chucvu;    //cong chuc hay quan ly

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Salary> salaries;

    public Employee() {
    }

    public Employee(String id, String name, int age, String gender, String donvicongtac, double hesoluong, String chucvu) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.donvicongtac = donvicongtac;
        this.hesoluong = hesoluong;
        this.chucvu = chucvu;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getDonvicongtac() {
        return donvicongtac;
    }
    public void setDonvicongtac(String donvicongtac) {
        this.donvicongtac = donvicongtac;
    }
    public double getHesoluong() {
        return hesoluong;
    }
    public void setHesoluong(double hesoluong) {
        this.hesoluong = hesoluong;
    }
    public String getChucvu() {
        return chucvu;
    }
    public void setChucvu(String loai) {
        this.chucvu = loai;
    }
    public List<Salary> getSalaries() {
        return salaries;
    }
    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }
}

