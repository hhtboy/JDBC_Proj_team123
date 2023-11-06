package com.example.jdbc_app.model;

import java.sql.*;
import java.util.*;

public class MysqlModelImpl implements MysqlModel {
    private Connection conn = null;

    public MysqlModelImpl(String dbacct, String password, String dbname) throws SQLException {
        Scanner scanner = new Scanner(System.in);

//        System.out.println("Enter database account: ");
//        dbacct = scanner.nextLine();
//        System.out.println("Enter password: ");
//        password = scanner.nextLine();
//        System.out.println("Enter database name: ");
//        dbname = scanner.nextLine();

//        dbacct = "root";
//        password = "142857";
//        dbname = "COMPANY";

        String url = "jdbc:mysql://localhost:3306/" + dbname + "?serverTimezone=UTC";
        try {
            this.conn = DriverManager.getConnection(url, dbacct, password);
        } catch (SQLException e) {
            System.err.println("연결할 수 없습니다.");
            throw e;
        }
    }

    @Override
    public ArrayList<Map<String, Object>> select(ArrayList<String> attrName, String ssn, String fname, Character sex, Double salary, String symbol, String dName) throws SQLException {

        ArrayList<Map<String, Object>> memoryTable = new ArrayList<>(); //arraylist 선언 및 초기화
        // SQL 쿼리 생성
        // + " LEFT OUTER JOIN EMPLOYEE AS S ON E.Super_ssn = S.Ssn"
        String sql = "SELECT * FROM EMPLOYEE AS E"
                + " JOIN DEPARTMENT ON E.Dno = Dnumber"
                + " WHERE 1=1"; // 항상 true인 조건으로 두고, 뒤에 AND를 붙여 WHERE절에 조건 추가

        // 검색 조건을 담을 리스트 생성
        List<String> conditions = new ArrayList<>();

        // 파라미터 값들을 담을 리스트 생성
        List<Object> params = new ArrayList<>();

        // ssn 검색 조건이 비어있을 경우에 ssn을 선택하여 return하도록 SQL 쿼리에 추가
        if (ssn.isEmpty()) {
            sql += " AND E.Ssn IS NOT NULL";
        }

        // 제공된 파라미터에 기반하여 조건, 파라미터 추가
        if (!ssn.isEmpty()) {
            conditions.add("E.Ssn = ?"); //Ssn 검색조건 추가
            params.add(ssn); //Ssn 파라미터 추가
        }
        if (!fname.isEmpty()) {
            conditions.add("E.Fname = ?"); //Fname 검색조건 추가
            params.add(fname); //Fname 파라미터 추가
        }
        if (sex != null) {
            conditions.add("E.Sex = ?"); //Sex 검색조건 추가
            params.add(sex.toString()); //Sex 파라미터 추가
        }
        if (salary != null) { //salary 검색조건 추가
            if ("=".equals(symbol)) { //Salary 동일 조건
                conditions.add("E.Salary = ?");
            } else if (">=".equals(symbol)) { //Salary 이상 조건
                conditions.add("E.Salary <= ?");
            } else if (">".equals(symbol)) { //Salary 초과 조건
                conditions.add("E.Salary < ?");
            } else if ("<=".equals(symbol)) { //Salary 이하 조건
                conditions.add("E.Salary >= ?");
            } else if ("<".equals(symbol)) { //Salary 미만 조건
                conditions.add("E.Salary > ?");
            }
            params.add(salary); //Salary 파라미터 추가
        }
        if (dName != null) {
            conditions.add("Dname = ?"); //Dname 검색조건 추가
            params.add(dName); //Dname 파라미터 추가
        }

        // SQL 쿼리에 조건을 추가(AND를 이용해 WHERE절에 추가)
        if (!conditions.isEmpty()) {
            sql += " AND " + String.join(" AND ", conditions);
        }

        // PreparedStatement 객체 생성
        PreparedStatement stmt = conn.prepareStatement(sql);

        // 검색조건의 각 ?에 대입할 값을 설정
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        // SQL 쿼리 실행
        ResultSet resultSet = stmt.executeQuery();

        // 결과를 ArrayList<Map<String, Object>>에 매핑
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) { //결과 집합 반복
            Map<String, Object> row = new HashMap<>(); // 현재 행 데이터 저장
            for (int i = 1; i <= columnCount; i++) { // 칼럼수만큼 루프
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                row.put(columnName, columnValue); //결과를 매핑
            }
            memoryTable.add(row); // 매핑된 결과를 ArrayList에 추가
        }

        return memoryTable; // 최종 결과 반환
    }

    @Override
    public void insert(Map<String, Object> tuple) throws SQLException {
        // SQL 쿼리 생성
        String sql = "INSERT INTO EMPLOYEE (Ssn, Fname, Minit, Lname, Bdate, Address, Sex, Salary, Super_ssn, Dno) " +
                "VALUES (";

        String[] attr = {"SSN", "FNAME", "MINIT", "LNAME","BDATE","ADDRESS","SEX","SALARY","SUPER_SSN","DNO"};

        for(String s : attr){
            // Salary와 Dno는 숫자이므로 따로 분리해서 sql 쿼리에 넣기
            if(s.equals("SALARY") || s.equals("DNO")){
                if(tuple.get(s) != null)
                    sql = sql + tuple.get(s).toString();
                // 해당 속성에 넣을 값이 없으면 null 문자열 넣기
                else{
                    sql = sql + "null";
                }
                sql = sql + ", ";
            }
            else{
                if(tuple.get(s) != null && !tuple.get(s).toString().isEmpty()){
                    sql = sql + "'" + tuple.get(s).toString() + "', ";
                }
                // 해당 속성에 넣을 값이 없으면 null 문자열 넣기
                else{
                    sql = sql + "null, ";
                }
            }
        }

        // 불필요한 문자열 분리
        sql = sql.substring(0, sql.length() - 2) + ")";

        // Query 준비
        Statement stmt = conn.createStatement();

        // SQL 쿼리 실행
        int result = stmt.executeUpdate(sql);
        System.out.println("insert result : " + result);
    }

    @Override
    public void update(String ssn, String updateAttr, Object value) throws SQLException {
        // SQL쿼리 생성
        String sql = "UPDATE EMPLOYEE";

        // 급여(SALARY)를 수정하는 경우
        if (Objects.equals(updateAttr, "SALARY")) {
            sql = sql + " SET SALARY=" + value.toString()
                    + " WHERE SSN = '" + ssn + "'";
        }
        // 주소 등을 수정하는 경우
        else {
            sql = sql + " SET " + updateAttr + "='" + value.toString() + "'"
                    + " WHERE SSN ='" + ssn + "'";
        }

        Statement stmt = null;

        // Query 준비
        stmt = conn.createStatement();

        // Query 실행 및 리턴
        // result = 1이면 정상적으로 업데이트됨
        int result = stmt.executeUpdate(sql);
        System.out.println("update result : " + result);
    }

    @Override
    public void deleteBySsn(String ssn) throws SQLException {
        // SQL쿼리 생성
        String sql = "DELETE FROM EMPLOYEE WHERE Ssn = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);

        // SQL 문에 Ssn 값을 설정합니다.
        stmt.setString(1, ssn);

        // SQL DELETE 문을 실행하여 직원 튜플을 삭제
        stmt.executeUpdate();

        // PreparedStatement와 ResultSet을 닫음
        stmt.close();
    }
}