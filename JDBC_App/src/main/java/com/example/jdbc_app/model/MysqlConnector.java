package com.example.jdbc_app.model;

import java.sql.*;

public class MysqlConnector {
    Connection con = null;

    String server = "localhost"; // MySQL 서버 주소
    String database = ""; // MySQL DATABASE 이름
    String user_name = "root"; //  MySQL 서버 아이디
    String password = "1234"; // MySQL 서버 비밀번호

    public void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database + "?useSSL=false", user_name, password);
            System.out.println("정상적으로 연결되었습니다.");
        } catch(
                SQLException e) {
            System.err.println("con 오류:" + e.getMessage());
            e.printStackTrace();
        }

        try {
            if(con != null)
                con.close();
        } catch (SQLException e) {}
    }
}
