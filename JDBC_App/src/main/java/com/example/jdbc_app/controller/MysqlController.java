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

    @FXML
    private CheckBox checkBoxAll, checkBoxFNAME, checkBoxMINIT, checkBoxLNAME, checkBoxSSN, checkBoxBDATE, checkBoxADDRESS, checkBoxSEX, checkBoxSUPER_SSN, checkBoxDNAME;

    @FXML
    private TextField textFieldSsn, textFieldFNAME, textFieldSalary;

    @FXML
    private ChoiceBox choiceBoxSex, choiceBoxDNAME, choiceBoxSymbol;

    ArrayList<CheckBox> checkBoxes = new ArrayList<>();

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

        // choiceBox 내용 채우기
        choiceBoxSex.getItems().addAll('M', 'F');
        choiceBoxSymbol.getItems().addAll(">", "<", "=", ">=", "<=");
        choiceBoxDNAME.getItems().addAll("Research", "Administration", "Headquarters");

    }
}
