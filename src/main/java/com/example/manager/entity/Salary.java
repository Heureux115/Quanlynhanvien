package com.example.manager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "salaries")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int ngaylam;
    private int ngaynghi;
    private int luongcoban; //theo ngay
    private double thue;
    private double hesoluong;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Salary() {
    }

    public Salary(int ngaylam, int ngaynghi, int luongcoban, double thue, double hesoluong) {
        this.ngaylam = ngaylam;
        this.ngaynghi = ngaynghi;
        this.luongcoban = luongcoban;
        this.thue = thue;
        this.hesoluong = hesoluong;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNgaylam() {
        return ngaylam;
    }

    public void setNgaylam(int ngaylam) {
        this.ngaylam = ngaylam;
    }

    public int getNgaynghi() {
        return ngaynghi;
    }

    public void setNgaynghi(int ngaynghi) {
        this.ngaynghi = ngaynghi;
    }

    public int getLuongcoban() {
        return luongcoban;
    }

    public void setLuongcoban(int luongcoban) {
        this.luongcoban = luongcoban;
    }

    public double getThue() {
        return thue;
    }

    public void setThue(double thue) {
        this.thue = thue;
    }

    public double getHesoluong() {return hesoluong;}
    public void setHesoluong(double hesoluong) {this.hesoluong = hesoluong;}

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
