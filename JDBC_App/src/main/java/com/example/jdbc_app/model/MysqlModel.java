package com.example.jdbc_app.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface MysqlModel {
    ArrayList<Map<String, Object>> select(ArrayList<String> attrName, String ssn, String fname, Character sex, Double salary, String symbol, String dName) throws SQLException;

    void insert(Map<String, Object> tuple) throws SQLException;

    void update(String ssn, String updateAttr, Object value) throws SQLException;

    void deleteBySsn(String ssn) throws SQLException;
}
