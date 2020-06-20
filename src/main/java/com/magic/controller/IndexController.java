package com.magic.controller;

import com.magic.Main;
import com.magic.util.BashAction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/19 10:49
 */
public class IndexController extends BashAction{

    private Main main;

    @FXML
    protected void mainTreeViewClick(ActionEvent event){

    }

    public void setMain(Main main) {
        this.main = main;
    }
}
