package com.magic.controller;

import com.magic.Main;
import com.magic.generated.datasource.DataSource;
import com.magic.generated.util.Template;
import com.magic.util.AlertBox;
import com.magic.util.BashAction;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.function.Function;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/21 10:49
 */
public class Index2Controller extends BashAction implements Initializable {
    private Main main;
    private final Node rootIcon =
            new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("static/image/folder_16.png")));
    private final Image depIcon =
            new Image(getClass().getClassLoader().getResourceAsStream("static/image/folder_16.png"));
    @FXML
    public TableView tableMysql;
    @FXML
    public TableView tableInfo;
    @FXML
    public TreeView<String> functionList;
    @FXML
    public AnchorPane centerAnchorPane;
    @FXML
    public AnchorPane rightAnchorPane;
    @FXML
    public VBox vBox;
    @FXML
    public SplitPane allInfo;
    @FXML
    public MenuItem showInfo;
    public void setMain(Main main) {
        this.main = main;
    }
    /**
     * Initialize the page
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showInfo.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                vBox.getChildren().add(1,allInfo);
            }
        });
        ObservableList<Node> scrolChildren = centerAnchorPane.getChildren();
        scrolChildren.clear();
        Label placeholder = new Label();
        placeholder.setText("No INFORMATION");
        tableInfo.setPlaceholder(placeholder);
        new Thread(() -> initTable()).start();
        setTreeView();
    }
    /**
     * Initialize the function tree
     */
    public void setTreeView() {
        TreeItem<String> rootItem = new TreeItem<> ("Features", rootIcon);
        rootItem.setExpanded(true);
        TreeItem<String> item = new TreeItem<> ("Code generation");
        rootItem.getChildren().add(item);
        functionList.setRoot(rootItem);
    }
    /**
     * Click event on the left tree
     * @param mouseEvent
     */
    public void mainTreeViewClick(MouseEvent mouseEvent) {
        TreeItem<String> selectedItem = functionList.getSelectionModel().getSelectedItem();
        logger(getClass()).info(selectedItem.getValue());
        if("Code generation".equals(selectedItem.getValue())){
            setDefaultAnchorPane();
        }else if("Features".equals(selectedItem.getValue())){
            ObservableList<Node> scrolChildren = centerAnchorPane.getChildren();
            scrolChildren.clear();
        }
    }
    /**
     * Initialization table
     * @return
     */
    public TableView initTable(){
        Label placeholder = new Label();
        placeholder.setText("NO TABLE");
        tableMysql.setPlaceholder(placeholder);
        List<String> database = DataSource.getDatabase();
        List<Person> list = new ArrayList<>();
        database.forEach(value -> {
            Person person =  new Person(value, "Double click to generate code");
            list.add(person);
        });
        ObservableList<Person> data = FXCollections.observableArrayList(
                list
        );
        TableColumn tableNameCol = new TableColumn("Table Name");
        tableNameCol.setCellValueFactory(
                new PropertyValueFactory<>("tableName")
        );
        tableNameCol.setPrefWidth(199);
        TableColumn operationalCol = new TableColumn("operational");
        operationalCol.setCellValueFactory(
                new PropertyValueFactory<>("operational")
        );
        operationalCol.setPrefWidth(199);
        /**
         * The button is defined in the entity class, but the column information cannot be obtained
         */
        TableColumn colBtn = new TableColumn("Generate configuration");
        colBtn.setCellValueFactory(
                new PropertyValueFactory<>("entityButton")
        );
        colBtn.setCellFactory(col -> {
            TableCell<EditButton, EditButton> cell = new TableCell<EditButton, EditButton>() {
                @Override
                public void updateItem(EditButton person, boolean empty) {
                    EditButton button = new EditButton("Configuration");
                    button.setOnAction(event -> {
                        Person p = (Person) getTableRow().getItem();
                        jumpPage(p.getTableName());
                    });
                    super.updateItem(person, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(button);
                    }
                }
            };
            return cell;
        });
        /**
         * Find the method on the Internet and then modify it,
         * the "hashmap" information contained in the button is stored in the entity class
         */
        TableColumn<Map<String,String>, Map<String,String>> editColumn = new TableColumn<>("Custom configuration");
        editColumn.setCellValueFactory(
                new PropertyValueFactory<>("customButton")
        );
        editColumn.setCellFactory(col -> {
            Button editButton = new Button("Edit");
            TableCell<Map<String,String>, Map<String,String>> cell = new TableCell<Map<String,String>, Map<String,String>>() {
                @Override
                public void updateItem(Map<String,String> person, boolean empty) {
                    super.updateItem(person, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                    }
                }
            };
            editButton.setOnAction(e -> System.err.println(cell.getItem().toString()));
            return cell ;
        });
        /**
         * The method of searching online is effective
         */
        TableColumn<Person, Person> networkColumn = column("Network configuration", ReadOnlyObjectWrapper<Person>::new);
        networkColumn.setCellFactory(col -> {
            Button editButton = new Button("Edit");
            TableCell<Person, Person> cell = new TableCell<Person, Person>() {
                @Override
                public void updateItem(Person person, boolean empty) {
                    super.updateItem(person, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                    }
                }
            };
            editButton.setOnAction(e -> System.err.println(cell.getItem().getTableName()));
            return cell ;
        });
        tableMysql.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<Person>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    logger(getClass()).info("once");
                    Person info = row.getItem();
                    //Get table information
                    ObservableList<Node> children = rightAnchorPane.getChildren();
                    children.clear();
                    getTableInformation(info.getTableName());
                    rightAnchorPane.getChildren().addAll(tableInfo);
                }
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    vBox.getChildren().removeAll(allInfo);
//                    Person info = row.getItem();
//                    Thread thread = new Thread(() -> {
//                        Template.generatedAll(info.getTableName());
//                        Platform.runLater(()->{
//                            AlertBox.close();
//                        });
//                    });
//                    thread.start();
//                    new AlertBox().display("Code Generation","Generating");
                }
            });
            return row ;
        });
        tableMysql.setItems(data);
        tableMysql.getColumns().addAll(tableNameCol, operationalCol,colBtn,editColumn,networkColumn);
        return tableMysql;
    }
    private <S,T> TableColumn<S,T> column(String title, Function<S, ObservableValue<T>> property) {
        TableColumn<S,T> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        return col ;
    }
    /**
     * Display table information
     * @param tableName
     * @return
     */
    public TableView getTableInformation(String tableName){
        //Clear the original table data
        tableInfo.getColumns().clear();
        tableInfo.getItems().clear();
        List<Map<String,String>> tableInfomation = DataSource.getTableInfo(tableName);
        List<TableInfo> list = new ArrayList<>();
        tableInfomation.forEach(value -> {
            TableInfo table =  new TableInfo(value.get("Name"),value.get("Type"),value.get("Length"),value.get("Null"),value.get("Key"),value.get("Comment"));
            list.add(table);
        });
        ObservableList<TableInfo> data = FXCollections.observableArrayList(
                list
        );
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("Name")
        );
        TableColumn typeCol = new TableColumn("Type");
        typeCol.setCellValueFactory(
                new PropertyValueFactory<>("Type")
        );
        TableColumn lengthCol = new TableColumn("Length");
        lengthCol.setCellValueFactory(
                new PropertyValueFactory<>("Length")
        );
        TableColumn nullCol = new TableColumn("Null");
        nullCol.setCellValueFactory(
                new PropertyValueFactory<>("Null")
        );
        TableColumn keyCol = new TableColumn("Key");
        keyCol.setCellValueFactory(
                new PropertyValueFactory<>("Key")
        );
        TableColumn commentCol = new TableColumn("Comment");
        commentCol.setCellValueFactory(
                new PropertyValueFactory<>("Comment")
        );
        tableInfo.setRowFactory(tv -> {
            TableRow<TableInfo> row = new TableRow<TableInfo>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    logger(getClass()).info("once");
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        InputStream in = Main.class.getClassLoader().getResourceAsStream("FieldConfiguration.fxml");
                        loader.setBuilderFactory(new JavaFXBuilderFactory());
                        loader.setLocation(Main.class.getClassLoader().getResource("FieldConfiguration.fxml"));
                        loader.setControllerFactory((Class<?> param) -> {
                            return new FieldConfigController("type_test");
                        });
                        Parent layout = loader.load();
                        FieldConfigController controller = loader.getController();
                        Scene scene = new Scene(layout);
                        Stage popupStage = new Stage();
                        if(this.main!=null) {
                            popupStage.initOwner(main.getStage());
                        }
                        popupStage.initModality(Modality.WINDOW_MODAL);
                        popupStage.setScene(scene);
                        popupStage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        tableInfo.setItems(data);
        tableInfo.getColumns().addAll(nameCol, typeCol,lengthCol,nullCol,keyCol,commentCol);
        return tableInfo;
    }
    /**
     * Set whether the middle table view is displayed
     */
    public void setDefaultAnchorPane() {
        centerAnchorPane.getChildren().clear();
        centerAnchorPane.getChildren().addAll(tableMysql);
    }
    /**
     * Custom button
     */
    public static class EditButton extends Button {
        private String tableName;
        public EditButton(String fileName) {
            super(fileName);
            setOnAction((event) -> {
                System.err.println(event.toString());
                System.err.println(event.getSource().toString());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Hey!");
                alert.setHeaderText(null);
                alert.setContentText("You're editing \"" + "entity" + "\"");
                alert.showAndWait();
            });
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
    }
    public void jumpPage(String tableNameDE){
        try {
            FXMLLoader loader = new FXMLLoader();
            InputStream in = Main.class.getClassLoader().getResourceAsStream("FieldConfiguration.fxml");
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(Main.class.getClassLoader().getResource("FieldConfiguration.fxml"));
            loader.setControllerFactory((Class<?> param) -> {
                return new FieldConfigController(tableNameDE);
            });
            Parent layout = loader.load();
            FieldConfigController controller = loader.getController();
            Scene scene = new Scene(layout);
            Stage popupStage = new Stage();
            if(this.main!=null) {
                popupStage.initOwner(main.getStage());
            }
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setScene(scene);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Each row of table information
     */
    public static class Person {
        private final SimpleStringProperty tableName;
        private final SimpleStringProperty operational;
        private final SimpleObjectProperty<Map<String,String>> customButton;
        private final SimpleObjectProperty<EditButton> entityButton;

        private Person(String fName, String lName) {
            this.tableName = new SimpleStringProperty(fName);
            this.operational = new SimpleStringProperty(lName);
            EditButton configuration = new EditButton("Configuration");
            configuration.setTableName(fName);
            this.entityButton = new SimpleObjectProperty(configuration);
            Map<String,String> map = new HashMap<>();
            map.put("name",fName);
            this.customButton = new SimpleObjectProperty(map);
        }

        public String getTableName() {
            return tableName.get();
        }

        public SimpleStringProperty tableNameProperty() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName.set(tableName);
        }

        public String getOperational() {
            return operational.get();
        }

        public SimpleStringProperty operationalProperty() {
            return operational;
        }

        public void setOperational(String operational) {
            this.operational.set(operational);
        }

        public Map<String, String> getCustomButton() {
            return customButton.get();
        }

        public SimpleObjectProperty<Map<String, String>> customButtonProperty() {
            return customButton;
        }

        public void setCustomButton(Map<String, String> customButton) {
            this.customButton.set(customButton);
        }

        public EditButton getEntityButton() {
            return entityButton.get();
        }

        public SimpleObjectProperty<EditButton> entityButtonProperty() {
            return entityButton;
        }

        public void setEntityButton(EditButton entityButton) {
            this.entityButton.set(entityButton);
        }
    }
    /**
     * Database table information
      */
    public static class TableInfo {
        private final SimpleStringProperty Name;
        private final SimpleStringProperty Type;
        private final SimpleStringProperty Length;
        private final SimpleStringProperty Null;
        private final SimpleStringProperty Key;
        private final SimpleStringProperty Comment;

        public TableInfo(String name, String type, String length, String aNull, String key, String comment) {
            Name = new SimpleStringProperty(name);;
            Type = new SimpleStringProperty(type);;
            Length = new SimpleStringProperty(length);;
            Null = new SimpleStringProperty(aNull);;
            Key = new SimpleStringProperty(key);;
            Comment = new SimpleStringProperty(comment);;
        }

        public String getName() {
            return Name.get();
        }

        public SimpleStringProperty nameProperty() {
            return Name;
        }

        public void setName(String name) {
            this.Name.set(name);
        }

        public String getType() {
            return Type.get();
        }

        public SimpleStringProperty typeProperty() {
            return Type;
        }

        public void setType(String type) {
            this.Type.set(type);
        }

        public String getLength() {
            return Length.get();
        }

        public SimpleStringProperty lengthProperty() {
            return Length;
        }

        public void setLength(String length) {
            this.Length.set(length);
        }

        public String getNull() {
            return Null.get();
        }

        public SimpleStringProperty nullProperty() {
            return Null;
        }

        public void setNull(String aNull) {
            this.Null.set(aNull);
        }

        public String getKey() {
            return Key.get();
        }

        public SimpleStringProperty keyProperty() {
            return Key;
        }

        public void setKey(String key) {
            this.Key.set(key);
        }

        public String getComment() {
            return Comment.get();
        }

        public SimpleStringProperty commentProperty() {
            return Comment;
        }

        public void setComment(String comment) {
            this.Comment.set(comment);
        }
        //        private TableInfo(String fName, String lName) {
//            this.tableName = new SimpleStringProperty(fName);
//            this.operational = new SimpleStringProperty(lName);
//        }
//
//        public String getTableName() {
//            return tableName.get();
//        }
//
//        public void setTableName(String fName) {
//            tableName.set(fName);
//        }
//
//        public String getOperational() {
//            return operational.get();
//        }
//
//        public void setOperational(String lName) {
//            operational.set(lName);
//        }
    }
}
