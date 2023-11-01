package com.example.jdbc_app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestMysqlModel implements MysqlModel{
    ArrayList<Map<String, Object>> memoryTable = new ArrayList<>();

    public TestMysqlModel() {
        HashMap<String, Object> tuple1 = new HashMap<>();
        tuple1.put("FNAME", "John");
        tuple1.put("SSN", "1234");
        tuple1.put("BDATE", null);
        tuple1.put("ADDRESS", "seoul");
        tuple1.put("SEX", 'M');
        tuple1.put("SALARY", 1000);
        tuple1.put("SUPERVISOR", null);
        tuple1.put("DNAME", "Research");

        memoryTable.add(tuple1);
    }


    @Override
    public ArrayList<Map<String, Object>> select(ArrayList<String> attrName, String ssn, String fname, Character sex, Double salary, String symbol, String dName) throws Exception {
        return memoryTable;
    }

    @Override
    public void insert(Map<String, Object> tuple) throws Exception {
        memoryTable.add(tuple);
    }

    @Override
    public void update(String ssn, String updateAttr, Object value) throws Exception {

    }

    @Override
    public void delete(String ssn) throws Exception {

    }
}
