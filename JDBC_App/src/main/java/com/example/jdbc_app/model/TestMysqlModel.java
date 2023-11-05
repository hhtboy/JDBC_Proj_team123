package com.example.jdbc_app.model;

import java.sql.*;
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

    public ArrayList<Map<String, Object>> select(ArrayList<String> attrName, String ssn, String fname, Character sex, Double salary, String symbol, String dName) throws SQLException {
        Connection conn = null;
        ArrayList<Map<String, Object>> memoryTable = new ArrayList<>(); //arraylist 선언 및 초기화

        final String url = "jdbc:mysql://localhost:3306/mydb?serverTimeZone=UTC";
        final String user = "root";
        final String password = "3402";

        try {
            // DB 연결
            conn = DriverManager.getConnection(url, user, password);

            // SQL 쿼리 생성
            String sql = "SELECT * FROM EMPLOYEE AS E"
                    + " LEFT OUTER JOIN EMPLOYEE AS S ON E.Super_ssn = S.Ssn"
                    + " JOIN DEPARTMENT ON E.Dno = Dnumber"
                    + " WHERE 1=1"; // 항상 true인 조건으로 두고, 뒤에 AND를 붙여 WHERE절에 조건 추가

            // 검색 조건에 따라 SQL WHERE 절 추가
            if (ssn != null) {
                sql += " AND E.Ssn = ?"; // Ssn이 검색조건
            }
            if (fname != null) {
                sql += " AND E.Fname = ?"; // Fname이 검색조건
            }
            if (sex != null) {
                sql += " AND E.Sex = ?"; // Sex가 검색조건
            }
            if (salary != null) {
                if ("=".equals(symbol)) {
                    sql += " AND E.Salary = ?"; // Salary가 검색조건 (등호 비교)
                } else if (">=".equals(symbol)) {
                    sql += " AND E.Salary >= ?"; // Salary가 검색조건 (이상 비교)
                } else if (">".equals(symbol)) {
                    sql += " AND E.Salary > ?"; // Salary가 검색조건 (초과 비교)
                } else if ("=<".equals(symbol)) {
                    sql += " AND E.Salary =< ?"; // Salary가 검색조건 (이하 비교)
                } else if ("<".equals(symbol)) {
                    sql += " AND E.Salary < ?"; // Salary가 검색조건 (미만 비교)
                }
            }
            if (dName != null) {
                sql += " AND Dname = ?"; // Dname이 검색조건
            }

            // PreparedStatement 객체 생성
            PreparedStatement stmt = conn.prepareStatement(sql);

            // 검색조건 ? 파라미터 인덱스 선언
            int paramIndex = 1;

            // 검색조건의 ?에 넣을 파라미터 값 설정
            if (ssn != null) {
                stmt.setString(paramIndex, ssn); // Ssn 파라미터 값 설정
                paramIndex++;
            }
            if (fname != null) {
                stmt.setString(paramIndex, fname); // Fname 파라미터 값 설정
                paramIndex++;
            }
            if (sex != null) {
                stmt.setString(paramIndex, sex.toString()); // Sex 파라미터 값 설정
                paramIndex++;
            }
            if (salary != null) {
                stmt.setDouble(paramIndex, salary); // Salary 파라미터 값 설정
                paramIndex++;
            }
            if (dName != null) {
                stmt.setString(paramIndex, dName); // Dname 파라미터 값 설정
            }

            // SQL 쿼리 실행
            ResultSet resultSet = stmt.executeQuery();

            // 결과를 ArrayList<Map<String, Object>>에 매핑
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) { //결과 집합 반복!
                Map<String, Object> row = new HashMap<>(); //현재 행 데이터 저장
                for (int i = 1; i <= columnCount; i++) { //칼럼수만큼 루프
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    row.put(columnName, columnValue); // 결과를 Map에 매핑
                }
                memoryTable.add(row); // 매핑된 결과를 ArrayList에 추가
            }
        } finally {
            if (conn != null) {
                conn.close(); // DB 연결 닫기
            }
        }
        return memoryTable; // 최종 결과 반환
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
