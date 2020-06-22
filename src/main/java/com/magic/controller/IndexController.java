package com.magic.controller;

import com.magic.Main;
import com.magic.generated.datasource.DataSource;
import com.magic.util.BashAction;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/19 10:49
 */
public class IndexController extends BashAction implements Initializable {

    private Main main;

    private final Node rootIcon =
            new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("static/image/folder_16.png")));
    private final Image depIcon =
            new Image(getClass().getClassLoader().getResourceAsStream("static/image/folder_16.png"));
    @FXML
    private TreeView<String> main_treeview;
    @FXML
    private ScrollPane main_scroll_pane;
    @FXML
    private AnchorPane main_pane_under_scroll;

    public void setTreeView() {
        TreeItem<String> rootItem = new TreeItem<> ("Features", rootIcon);
        rootItem.setExpanded(true);
        TreeItem<String> item = new TreeItem<> ("Code generation");
        rootItem.getChildren().add(item);
        main_treeview.setRoot(rootItem);
    }

    public void setDefaultAnchorPane(List<String> database) {
        Button buttonSave = new Button("Save");
        buttonSave.setOnAction(new EventHandler<ActionEvent>() {//注册事件handler
             public void handle(ActionEvent e) {
                logger(getClass()).info(e.toString());
             }
         }
        );
        Button buttonCancel = new Button("Cancel");
        HBox hb = new HBox();
        hb.setPadding(new Insets(0, 10, 10, 10));
        hb.setSpacing(10);
        hb.getChildren().addAll(buttonSave, buttonCancel);
        TableView table = new TableView();
        List<Person> list = new ArrayList<>();
        database.forEach(value -> {
            Person person =  new Person(value, "generate");
            list.add(person);
        });
        logger(getClass()).info(list.toString());
        ObservableList<Person> data = FXCollections.observableArrayList(
                list
        );
        TableColumn tableNameCol = new TableColumn("Table Name");
        tableNameCol.setCellValueFactory(
            new PropertyValueFactory<>("tableName")
        );
        TableColumn operationalCol = new TableColumn("operational");
        operationalCol.setCellValueFactory(
            new PropertyValueFactory<>("operational")
        );
        table.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<Person>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Person emailInfo = row.getItem();
                    System.out.println(emailInfo.getTableName());
                }
            });
         return row ;
        });
        table.setItems(data);
        table.getColumns().addAll(tableNameCol, operationalCol);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hb, table);
        main_pane_under_scroll.getChildren().addAll(vbox);
    }

    @FXML
    protected void mainTreeViewClick(){
        logger(getClass()).info("点击TreeView");
        TreeItem<String> selectedItem = main_treeview.getSelectionModel().getSelectedItem();
        logger(getClass()).info(selectedItem.getValue());
        if("Code generation".equals(selectedItem.getValue())){
            List<String> database = DataSource.getDatabase();
            setDefaultAnchorPane(database);
        }else if("Features".equals(selectedItem.getValue())){
            ObservableList<Node> scrolChildren = main_pane_under_scroll.getChildren();
            scrolChildren.clear();
        }
    }
    private void skipView(String pagePath) throws IOException {
        ObservableList<Node> scrolChildren = main_pane_under_scroll.getChildren();
        scrolChildren.clear();
        scrolChildren.add(FXMLLoader.load(getClass().getResource(pagePath)));
    }
    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTreeView();
//        setDefaultAnchorPane();
    }
    public static class Person {

        private final SimpleStringProperty tableName;
        private final SimpleStringProperty operational;

        private Person(String fName, String lName) {
            this.tableName = new SimpleStringProperty(fName);
            this.operational = new SimpleStringProperty(lName);
        }

        public String getTableName() {
            return tableName.get();
        }

        public void setTableName(String fName) {
            tableName.set(fName);
        }

        public String getOperational() {
            return operational.get();
        }

        public void setOperational(String lName) {
            operational.set(lName);
        }
    }
}
