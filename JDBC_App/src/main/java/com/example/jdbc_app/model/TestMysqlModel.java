package com.example.jdbc_app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestMysqlModel implements MysqlModel{
    ArrayList<Map<String, Object>> memoryTable = new ArrayList<>();

    public TestMysqlModel() {
        HashMap<String, Object> tuple1 = new HashMap<>();
        tuple1.put("FNAME", "John");
        tuple1.put("SSN", "123456789");
        tuple1.put("BDATE", null);
        tuple1.put("ADDRESS", "seoul");
        tuple1.put("SEX", 'M');
        tuple1.put("SALARY", 1000);
        tuple1.put("SUPERVISOR", null);
        tuple1.put("DNAME", "Research");

        memoryTable.add(tuple1);
    }

    @Override
    public ArrayList<Map<String, Object>> select(ArrayList<String> attrName, String ssn, String fname, Character sex, Double salary, String symbol, String dName) throws SQLException {
        return memoryTable;
    }

    @Override
    public void insert(Map<String, Object> tuple) throws SQLException {
        memoryTable.add(tuple);
    }

    @Override
    public void update(String ssn, String updateAttr, Object value) throws SQLException {
        Connection conn = null;

        final String url = "jdbc:mysql://localhost:3306/mydb?serverTimeZone=UTC";
        final String user = "root";
        final String password = "3402";

        // 계정 연결
        conn = DriverManager.getConnection(url, user, password);

        // SQL문 작성
        String sql = "UPDATE EMPLOYEE";

        // 급여(SALARY)를 수정하는 경우
        if(Objects.equals(updateAttr, "SALARY")){
            sql = sql + " SET SALARY=" + value.toString()
                    + " WHERE SSN = '" + ssn + "'";
        }
        // 주소 등을 수정하는 경우
        else{
            sql = sql + " SET " + updateAttr + "='" + value.toString() + "'"
                    + " WHERE SSN ='" + ssn + "'";
        }

        Statement stmt = null;

        try{
            // Query 준비
            stmt = conn.createStatement();

            // Query 실행 및 리턴
            int result = stmt.executeUpdate(sql);
        }
        finally {
            // DB 종료
            stmt.close();
            conn.close();
        }
    }

    @Override
    public void deleteBySsn(String ssn) throws SQLException {
    }


}
