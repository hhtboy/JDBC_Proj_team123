package com.example.jdbc_app.controller;

import com.example.jdbc_app.model.MysqlConnector;
import com.example.jdbc_app.model.MysqlModel;
import com.example.jdbc_app.model.TestMysqlModel;

public class MysqlController {

    private final MysqlModel mysqlModel;

    public MysqlController() {
        mysqlModel = new TestMysqlModel();
    }

}
