package com.example.jdbc_app.controller;

import com.example.jdbc_app.model.MysqlConnector;
import com.example.jdbc_app.model.MysqlModel;
import com.example.jdbc_app.model.TestMysqlModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class MysqlController implements Initializable {

    @FXML
    private Button searchBtn;

    //select 관련 fxml
    @FXML
    private CheckBox checkBoxAll, checkBoxFNAME, checkBoxMINIT, checkBoxLNAME, checkBoxSSN, checkBoxBDATE, checkBoxADDRESS, checkBoxSEX, checkBoxSUPER_SSN, checkBoxDNAME;

    @FXML
    private TextField textFieldSsn, textFieldFNAME, textFieldSalary;

    @FXML
    private ChoiceBox choiceBoxSex, choiceBoxDNAME, choiceBoxSymbol;

    // insert 관련 fxml
    @FXML
    private TextField insertTxtFNAME, insertTxtMINIT, insertTxtLNAME, insertTxtSSN, insertTxtBDATE, insertTxtADDRESS, insertTxtSALARY, insertTxtSUPPER_SSN, insertTxtDNO;

    @FXML
    private ChoiceBox insertCBoxSex;

    @FXML
    private Button insertBtn;

    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ArrayList<TextField> textFields = new ArrayList<>();

    Set<String> selectedAttr = new HashSet<>();
    private final MysqlModel mysqlModel;

    public MysqlController() {
        mysqlModel = new TestMysqlModel();
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
            ArrayList<Map<String, Object>> result = mysqlModel.select(selectAttr, ssn, fname, sex, salary, symbol, dname);
            for(Map<String, Object> map:result){
                for(String key : map.keySet()) {
                    System.out.println("key : " + key + " value : " + map.get(key));
                }
            }

        }catch (Exception e){

        }
    }

    @FXML
    protected void onAllCheckboxClick(ActionEvent event) {
        System.out.println(((CheckBox)event.getSource()).getText());
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // insert 이후 TEXT 필드값 초기화
        for (TextField textField : textFields) {
            textField.setText(null);
        }
        insertCBoxSex.setValue(null);

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

        // choiceBox 내용 채우기
        choiceBoxSex.getItems().addAll('M', 'F');
        choiceBoxSymbol.getItems().addAll(">", "<", "=", ">=", "<=");
        choiceBoxDNAME.getItems().addAll("Research", "Administration", "Headquarters");

        // insertBox 내용 채우기
        insertCBoxSex.getItems().addAll('M', 'F');

    }
}
