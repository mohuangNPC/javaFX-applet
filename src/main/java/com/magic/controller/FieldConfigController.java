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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
                System.err.println("test button click");
                Template.generatedAll(tableName);
            }
        });
        List<Map<String, String>> tableInfomation = DataSource.getTableInfo(tableName);
        ObservableList<Map<String, String>> data = FXCollections.observableArrayList(
                tableInfomation
        );
        System.err.println(tableInfomation.toString());
        field.setCellValueFactory(new MapValueFactory("Name"));
        type.setCellValueFactory(new MapValueFactory("Type"));
        comment.setCellValueFactory(new MapValueFactory("Comment"));
        allTableView.setItems(data);
        allTableView.getColumns().set(0, field);
        allTableView.getColumns().set(1, type);
        allTableView.getColumns().set(2, comment);
        queryType.setCellFactory(col -> {
            TableCell<ComboBox, StringProperty> c = new TableCell<>();
            ComboBox comboBox = new ComboBox(options);
            comboBox.setValue("chose type");
            c.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (oldValue != null) {
                    comboBox.valueProperty().unbindBidirectional(oldValue);
                }
                if (newValue != null) {
                    comboBox.valueProperty().bindBidirectional(newValue);
                }
            });
            c.graphicProperty().bind(Bindings.when(c.emptyProperty()).then((Node) null).otherwise(comboBox));
            return c;
        });
        allTableView.getColumns().set(3, queryType);
    }
}
