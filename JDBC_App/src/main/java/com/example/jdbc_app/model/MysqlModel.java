package com.example.jdbc_app.model;

import java.util.ArrayList;
import java.util.Map;

public interface MysqlModel {
    ArrayList<Map<String, Object>> select(ArrayList<String> attrName, String ssn, String fname, Character sex, Double salary, String symbol, String dName) throws Exception;

    void insert(Map<String, Object> tuple) throws Exception;

    void update(String ssn, String updateAttr, Object value) throws Exception;

    void delete(String ssn) throws Exception;
}
