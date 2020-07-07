package com.magic.controller;

import com.magic.generated.datasource.DataSource;
import com.magic.generated.util.Template;
import com.magic.util.BashAction;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.MapValueFactory;

import java.net.URL;
import java.util.*;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/29 8:08
 */
public class FieldConfigController extends BashAction implements Initializable {
    private static final  ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Integer",
                    "String",
                    "Date"
            );
    @FXML
    public TableColumn queryType;
    @FXML
    public TableColumn field;
    @FXML
    public TableColumn type;
    @FXML
    public TableColumn comment;
    @FXML
    public TableView allTableView;
    @FXML
    public Button confirmButton;
    private String tableName;

    public FieldConfigController() {
    }

    public FieldConfigController(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                allTableView.getItems().forEach(v->{
                    Map<String,Object> map = (Map<String, Object>) v;
                    System.err.println(v.toString());
                    ComboBox queryType = (ComboBox) map.get("queryType");
                    System.err.println(queryType.getValue());
                });
//                Template.generatedAll(tableName);
            }
        });
        List<Map<String, String>> tableInfomation = DataSource.getTableInfo(tableName);
        List<Map<String, Object>> newTableInfomation = new ArrayList<>();
        tableInfomation.forEach(v -> {
            Map<String,Object> map = new HashMap<>();
            ComboBox comboBox = new ComboBox(options);
            comboBox.setValue("chose type");
            v.forEach((key,value) -> {
                map.put(key,value);
            });
            map.put("queryType",comboBox);
            newTableInfomation.add(map);
        });
        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(
                newTableInfomation
        );
        System.err.println(tableInfomation.toString());
        field.setCellValueFactory(new MapValueFactory("Name"));
        type.setCellValueFactory(new MapValueFactory("Type"));
        comment.setCellValueFactory(new MapValueFactory("Comment"));
        queryType.setCellValueFactory(new MapValueFactory("queryType"));
        allTableView.setItems(data);
        allTableView.getColumns().set(0, field);
        allTableView.getColumns().set(1, type);
        allTableView.getColumns().set(2, comment);
//        queryType.setCellFactory(col -> {
//            TableCell<ComboBox, StringProperty> c = new TableCell<>();
//            ComboBox comboBox = new ComboBox(options);
//            comboBox.setValue("chose type");
//            c.itemProperty().addListener((observable, oldValue, newValue) -> {
//                if (oldValue != null) {
//                    comboBox.valueProperty().unbindBidirectional(oldValue);
//                }
//                if (newValue != null) {
//                    comboBox.valueProperty().bindBidirectional(newValue);
//                }
//            });
//            c.graphicProperty().bind(Bindings.when(c.emptyProperty()).then((Node) null).otherwise(comboBox));
//            return c;
//        });
        allTableView.getColumns().set(3, queryType);
    }
}
