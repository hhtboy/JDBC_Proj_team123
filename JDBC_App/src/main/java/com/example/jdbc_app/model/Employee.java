package com.example.jdbc_app.model;

import javafx.scene.control.CheckBox;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Employee {
    private CheckBox select;
    private String fname = null;
    private String minit = null;
    private String lname = null;
    private String ssn = null;
    private String bdate = null;
    private String address = null;
    private Character sex = null;
    private Double salary = null;
    private String superSsn = null;
    private String dname = null;

    public Employee(String fname, String minit, String lname, String ssn, String bdate, String address, Character sex, Double salary, String superSsn, String dname) {
        this.select = new CheckBox();
        this.fname = fname;
        this.minit = minit;
        this.lname = lname;
        this.ssn = ssn;
        this.bdate = bdate;
        this.address = address;
        this.sex = sex;
        this.salary = salary;
        this.superSsn = superSsn;
        this.dname = dname;
    }

    public Employee(Map<String, Object> map){
        this.select = new CheckBox();
        for(String key : map.keySet()) {
            if(Objects.equals(key, "FNAME")){
                this.fname = (String)map.get(key);
            } else if (Objects.equals(key, "MINIT")){
                this.minit = (String)map.get(key);
            } else if (Objects.equals(key, "LNAME")){
                this.lname = (String)map.get(key);
            } else if (Objects.equals(key, "SSN")){
                this.ssn = (String)map.get(key);
            } else if (Objects.equals(key, "BDATE")){
                this.bdate = (String)map.get(key);
            } else if (Objects.equals(key, "ADDRESS")){
                this.address = (String)map.get(key);
            } else if (Objects.equals(key, "SEX")){
                if(map.get(key) != null){
                    this.sex = (Character)map.get(key);
                }
            } else if (Objects.equals(key, "SALARY")){
                if(map.get(key) != null){
                    this.salary = Double.parseDouble(String.valueOf(map.get(key)));
                }
            } else if (Objects.equals(key, "SUPER_SSN")){
                this.superSsn = (String)map.get(key);
            } else if (Objects.equals(key, "DNAME")){
                this.dname = (String)map.get(key);
            }
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMinit() {
        return minit;
    }

    public void setMinit(String minit) {
        this.minit = minit;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getSuperSsn() {
        return superSsn;
    }

    public void setSuperSsn(String superSsn) {
        this.superSsn = superSsn;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}
