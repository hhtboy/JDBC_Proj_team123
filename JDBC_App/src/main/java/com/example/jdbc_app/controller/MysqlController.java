package com.example.jdbc_app.controller;

import com.example.jdbc_app.model.Employee;
import com.example.jdbc_app.model.MysqlModel;
import com.example.jdbc_app.model.MysqlModelImpl;
import com.example.jdbc_app.model.TestMysqlModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class MysqlController implements Initializable {

    @FXML
    private Button searchBtn;

    //select 관련 fxml
    @FXML
    private CheckBox checkBoxAll, checkBoxFNAME, checkBoxMINIT, checkBoxLNAME, checkBoxSSN, checkBoxBDATE, checkBoxADDRESS, checkBoxSEX, checkBoxSALARY, checkBoxSUPER_SSN, checkBoxDNAME;

    @FXML
    private TextField textFieldSsn, textFieldFNAME, textFieldSalary;

    @FXML
    private ChoiceBox choiceBoxSex, choiceBoxDNAME, choiceBoxSymbol, selectRecord;

    // insert 관련 fxml
    @FXML
    private TextField insertTxtFNAME, insertTxtMINIT, insertTxtLNAME, insertTxtSSN, insertTxtBDATE, insertTxtADDRESS, insertTxtSALARY, insertTxtSUPPER_SSN, insertTxtDNO;

    @FXML
    private ChoiceBox insertCBoxSex;

    @FXML
    private Button insertBtn;

    // table 관련 fxml
    @FXML
    private TableView<Employee> tableView;
    @FXML
    private TableColumn<Employee, String> select;
    @FXML
    private TableColumn<Employee, String> fname;
    @FXML
    private TableColumn<Employee, String> minit;
    @FXML
    private TableColumn<Employee, String> lname;
    @FXML
    private TableColumn<Employee, String> ssn;
    @FXML
    private TableColumn<Employee, String> bdate;
    @FXML
    private TableColumn<Employee, String> address;
    @FXML
    private TableColumn<Employee, Character> sex;
    @FXML
    private TableColumn<Employee, Double> salary;
    @FXML
    private TableColumn<Employee, String> superSsn;
    @FXML
    private TableColumn<Employee, String> dname;

    ObservableList<Employee> employees;

    // Update 관련 fxml
    @FXML
    private ChoiceBox updateCBox;
    @FXML
    private TextField updateTextField;

    ArrayList<TableColumn> tableColumns = new ArrayList<>();
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ArrayList<TextField> textFields = new ArrayList<>();

    Set<String> selectedAttr = new HashSet<>();
    private final MysqlModel mysqlModel;

    public MysqlController() {
        mysqlModel = new MysqlModelImpl();
    }

    @FXML
    protected void onSearchBtnClicked() {
        // 1. select 절
        ArrayList<String> selectAttr = new ArrayList<>();
        for(CheckBox checkBox:checkBoxes) {
            if(checkBox.isSelected()){
                selectAttr.add(checkBox.getText());
            }
        }

        // 2. 검색 조건
        String ssn = textFieldSsn.getText();
        String fname = textFieldFNAME.getText();
        Character sex = null;
        if(choiceBoxSex.getValue() != null) {
            sex = (char)choiceBoxSex.getValue();
        }
        Double salary;
        if(textFieldSalary.getText() == null || textFieldSalary.getText().trim().isEmpty()) {
            salary = null;
        }
        else {
            salary = Double.parseDouble(textFieldSalary.getText());
        }
        String symbol = (String)choiceBoxSymbol.getValue();
        String dname = (String)choiceBoxDNAME.getValue();

        try {
            employees = FXCollections.observableArrayList();
            ArrayList<Map<String, Object>> result = mysqlModel.select(selectAttr, ssn, fname, sex, salary, symbol, dname);
            for(Map<String, Object> map:result){
                employees.add(new Employee(map));
            }

            // 선택한 Column만 보여주기
            for(TableColumn tableColumn:tableColumns) {
                tableColumn.setVisible(false);
                for(String attr : selectAttr){
                    if(Objects.equals(attr, tableColumn.getText())){
                        tableColumn.setVisible(true);
                    }
                }
            }

            tableView.setItems(employees);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    protected void onAllCheckboxClick(ActionEvent event) {
        if(!checkBoxAll.isSelected()) {
            for(CheckBox checkBox : checkBoxes) {
                checkBox.setSelected(false);
            }
        }
        else {
            for(CheckBox checkBox : checkBoxes) {
                checkBox.setSelected(true);
            }
        }
    }

    @FXML
    protected void onCheckboxClick(ActionEvent event) {
        if(!((CheckBox)event.getSource()).isSelected()) {
            checkBoxAll.setSelected(false);
        }

        boolean bAllSelected = true;
        for(CheckBox checkBox:checkBoxes) {
            if(!checkBox.isSelected()) {
                bAllSelected = false;
                break;
            }
        }
        if(bAllSelected) {
            checkBoxAll.setSelected(true);
        }
    }

    // 직원 정보 추가!
    @FXML
    protected void onInsertBtnClick(ActionEvent event) {
        String fname = insertTxtFNAME.getText();
        String minit = insertTxtMINIT.getText();
        String lname = insertTxtLNAME.getText();
        String ssn = insertTxtSSN.getText();
        String bdate = insertTxtBDATE.getText();
        String address = insertTxtADDRESS.getText();
        Character sex = null;
        if(insertCBoxSex.getValue() != null) {
            sex = (Character)insertCBoxSex.getValue();
        }
        Double salary;
        if(insertTxtSALARY.getText() == null || insertTxtSALARY.getText().trim().isEmpty()) {
            salary = null;
        }
        else {
            salary = Double.parseDouble(insertTxtSALARY.getText());
        }
        String super_ssn = insertTxtSUPPER_SSN.getText();
        Long dno;
        if(insertTxtDNO.getText() == null || insertTxtDNO.getText().trim().isEmpty()) {
            dno = null;
        }
        else {
            dno = Long.parseLong(insertTxtDNO.getText());
        }

        // insert 할 튜플 생성
        Map<String, Object> tuple = new HashMap<>();
        tuple.put("FNAME", fname);
        tuple.put("MINIT", minit);
        tuple.put("LNAME", lname);
        tuple.put("SSN", ssn);
        tuple.put("BDATE", bdate);
        tuple.put("ADDRESS", address);
        tuple.put("SEX", sex);
        tuple.put("SALARY", salary);
        tuple.put("SUPER_SSN", super_ssn);
        tuple.put("DNO", dno);

        try {
            mysqlModel.insert(tuple);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("INSERT");
            alert.setContentText("직원 정보가 추가되었습니다.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("INSERT");
            alert.setContentText(String.valueOf(e));
            alert.showAndWait();
            throw new RuntimeException(e);
        }

        // insert 이후 TEXT 필드값 초기화
        for (TextField textField : textFields) {
            textField.setText(null);
        }
        insertCBoxSex.setValue(null);

    }

    @FXML
    protected void onDeleteBtnClick(ActionEvent event) {
        ArrayList<String> ssnList = getCheckedEmp();

        try{
            for(String ssn : ssnList){
                mysqlModel.deleteBySsn(ssn);
            }
            if(!ssnList.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("DELETE");
                alert.setContentText("직원 정보가 삭제되었습니다.");
                alert.showAndWait();
            }
        } catch (Exception e){
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("DELETE");
            alert.setContentText(String.valueOf(e));
            alert.showAndWait();
        }
    }

    @FXML
    protected void onUpdateBtnClick(ActionEvent event) {
        ArrayList<String> ssnList = getCheckedEmp();
        try{
            String attr = String.valueOf(updateCBox.getValue());
            Object value = updateTextField.getText();
            if(Objects.equals(attr, "SALARY")){
                value = Double.parseDouble(updateTextField.getText());
            }
            for(String ssn : ssnList){
                mysqlModel.update(ssn, attr, value);
            }
            updateCBox.setValue(null);
            updateTextField.setText(null);

            if(!ssnList.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("UPDATE");
                alert.setContentText("직원 정보가 업데이트되었습니다.");
                alert.showAndWait();
            }
        } catch (Exception e){
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("UPDATE");
            alert.setContentText(String.valueOf(e));
            alert.showAndWait();
        }
    }

    @FXML
    protected ArrayList<String> getCheckedEmp(){
        ArrayList<String> ssnList = new ArrayList<>();

        for(Employee emp: employees){
            if(emp.getSelect().isSelected()){
                ssnList.add(emp.getSsn());
            }
        }

        return ssnList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxes.add(checkBoxFNAME);
        checkBoxes.add(checkBoxMINIT);
        checkBoxes.add(checkBoxLNAME);
        checkBoxes.add(checkBoxSSN);
        checkBoxes.add(checkBoxBDATE);
        checkBoxes.add(checkBoxADDRESS);
        checkBoxes.add(checkBoxSEX);
        checkBoxes.add(checkBoxSALARY);
        checkBoxes.add(checkBoxSUPER_SSN);
        checkBoxes.add(checkBoxDNAME);

        textFields.add(insertTxtFNAME);
        textFields.add(insertTxtMINIT);
        textFields.add(insertTxtLNAME);
        textFields.add(insertTxtSSN);
        textFields.add(insertTxtBDATE);
        textFields.add(insertTxtADDRESS);
        textFields.add(insertTxtSUPPER_SSN);
        textFields.add(insertTxtDNO);
        textFields.add(insertTxtSALARY);

        tableColumns.add(fname);
        tableColumns.add(minit);
        tableColumns.add(lname);
        tableColumns.add(ssn);
        tableColumns.add(bdate);
        tableColumns.add(address);
        tableColumns.add(sex);
        tableColumns.add(salary);
        tableColumns.add(superSsn);
        tableColumns.add(dname);

        // choiceBox 내용 채우기
        choiceBoxSex.getItems().addAll('M', 'F');
        choiceBoxSymbol.getItems().addAll(">", "<", "=", ">=", "<=");
        choiceBoxDNAME.getItems().addAll("Research", "Administration", "Headquarters");


        // insertBox 내용 채우기
        insertCBoxSex.getItems().addAll('M', 'F');

        // update
        updateCBox.getItems().addAll("ADDRESS", "SALARY");

        // TableView 채우기
        select.setCellValueFactory(new PropertyValueFactory<Employee, String >("select"));
        fname.setCellValueFactory(new PropertyValueFactory<Employee, String >("fname"));
        minit.setCellValueFactory(new PropertyValueFactory<Employee, String>("minit"));
        lname.setCellValueFactory(new PropertyValueFactory<Employee, String>("lname"));
        ssn.setCellValueFactory(new PropertyValueFactory<Employee, String>("ssn"));
        bdate.setCellValueFactory(new PropertyValueFactory<Employee, String>("bdate"));
        address.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));
        sex.setCellValueFactory(new PropertyValueFactory<Employee, Character>("sex"));
        salary.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
        superSsn.setCellValueFactory(new PropertyValueFactory<Employee, String>("superSsn"));
        dname.setCellValueFactory(new PropertyValueFactory<Employee, String>("dname"));
    }
}
