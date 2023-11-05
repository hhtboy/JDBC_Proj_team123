package com.example.jdbc_app.model;

import javafx.scene.control.CheckBox;
import java.text.SimpleDateFormat;
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

    // DB에서 검색 결과 중 하나의 record로 Employee 생성하기
    public Employee(Map<String, Object> map){
        this.select = new CheckBox();
        // Company Schema를 바탕으로 null일수도 있는 경우에는 따로 예외처리하기
        for(String key : map.keySet()) {
            if(Objects.equals(key, "Fname")){
                this.fname = (String)map.get(key);
            } else if (Objects.equals(key, "Minit")){
                if(map.get(key) != null)
                    this.minit = map.get(key).toString();
            } else if (Objects.equals(key, "Lname")){
                this.lname = (String)map.get(key);
            } else if (Objects.equals(key, "Ssn")){
                this.ssn = (String)map.get(key);
            } else if (Objects.equals(key, "Bdate")){
                if(map.get(key) != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateStr = sdf.format(map.get(key));
                    this.bdate = dateStr;
                }
            } else if (Objects.equals(key, "Address")){
                if(map.get(key) != null)
                    this.address = (String)map.get(key);
            } else if (Objects.equals(key, "Sex")){
                if(map.get(key) != null){
                    this.sex = map.get(key).toString().charAt(0);
                }
            } else if (Objects.equals(key, "Salary")){
                if(map.get(key) != null){
                    this.salary = Double.parseDouble(String.valueOf(map.get(key)));
                }
            } else if (Objects.equals(key, "Super_ssn")){
                if(map.get(key) != null)
                    this.superSsn = (String)map.get(key);
            } else if (Objects.equals(key, "Dname")){
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
