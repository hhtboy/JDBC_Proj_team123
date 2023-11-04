package com.example.jdbc_app.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MysqlModelImpl implements MysqlModel{
    private Connection conn = null;

    public MysqlModelImpl() {
        Scanner scanner = new Scanner(System.in);

        String dbacct, password, dbname;

        System.out.println("Enter database account: ");
        dbacct = scanner.nextLine();
        System.out.println("Enter password: ");
        password = scanner.nextLine();
        System.out.println("Enter database name: ");
        dbname = scanner.nextLine();

        String url = "jdbc:mysql://localhost:3306/" + dbname + "?serverTimezone=UTC";
        try{
            this.conn = DriverManager.getConnection(url, dbacct, password);
        } catch (SQLException e){
            System.err.println("연결할 수 없습니다.");
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Map<String, Object>> select(ArrayList<String> attrName, String ssn, String fname, Character sex, Double salary, String symbol, String dName) throws SQLException {
        return null;
    }

    @Override
    public void insert(Map<String, Object> tuple) throws SQLException {

    }

    @Override
    public void update(String ssn, String updateAttr, Object value) throws SQLException {
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

        // Query 준비
        stmt = conn.createStatement();

        // Query 실행 및 리턴
        // result = 1이면 정상적으로 업데이트됌
        int result = stmt.executeUpdate(sql);
    }

    @Override
    public void deleteBySsn(String ssn) throws SQLException {

    }
}
