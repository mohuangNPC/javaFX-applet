package com.magic.controller;

import com.magic.Main;
import com.magic.util.BashAction;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        TreeItem<String> rootItem = new TreeItem<> ("Inbox", rootIcon);
        rootItem.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<> ("Message" + i);
            rootItem.getChildren().add(item);
        }
        main_treeview.setRoot(rootItem);
    }

    public void setDefaultAnchorPane() {
        Button buttonSave = new Button("Save");
        Button buttonCancel = new Button("Cancel");
        HBox hb = new HBox();
        hb.setPadding(new Insets(0, 10, 10, 10));
        hb.setSpacing(10);
        hb.getChildren().addAll(buttonSave, buttonCancel);
        TableView table = new TableView();
        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Email");
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(hb, table);
        main_pane_under_scroll.getChildren().addAll(vbox);
    }

    @FXML
    protected void mainTreeViewClick(){
        logger(getClass()).info("点击TreeView");
        TreeItem<String> selectedItem = main_treeview.getSelectionModel().getSelectedItem();
        logger(getClass()).info(selectedItem.getValue());
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
        setDefaultAnchorPane();
    }
}
