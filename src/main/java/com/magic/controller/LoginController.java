package com.magic.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/19 9:21
 */
public class LoginController{
    @FXML
    private Text actiontarget;
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }
}
