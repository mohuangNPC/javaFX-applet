package com.magic.controller;

import com.magic.Main;
import com.magic.util.BashAction;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/19 9:21
 */
public class LoginController extends BashAction {
    @FXML
    private Text actiontarget;
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;

    private Main main;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        String user = userField.getText();
        String pwd = passwordField.getText();
        if("admin".equals("admin")){
            main.gotoIndex("Index2.fxml");
        }
//        actiontarget.setText("Sign in button pressed");
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
