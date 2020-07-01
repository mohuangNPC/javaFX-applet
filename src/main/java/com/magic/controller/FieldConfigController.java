package com.magic.controller;

import com.magic.generated.datasource.DataSource;
import com.magic.util.BashAction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/29 8:08
 */
public class FieldConfigController extends BashAction implements Initializable {
    @FXML
    public TableColumn queryType;
    @FXML
    public TableColumn field;
    @FXML
    public TableColumn type;
    @FXML
    public TableColumn comment;
    private String tableName;

    public FieldConfigController() {
    }

    public FieldConfigController(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Map<String,String>> tableInfomation = DataSource.getTableInfo(tableName);
    }
}
