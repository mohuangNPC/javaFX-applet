package com.magic;

import com.magic.controller.Index2Controller;
import com.magic.controller.IndexController;
import com.magic.controller.LoginController;
import com.magic.util.BashAction;
import com.magic.util.StaticResourcesConfig;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author mohuangNPC
 * @version 1.0
 * @date 2020/6/19 9:22
 */
public class Main extends Application {
    private static Logger log = Logger.getLogger(Main.class.getClass());
    private Stage stage;
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
        stage = primaryStage;
//        Scene scene = new Scene(root, 300, 275);
        stage.setTitle("Welcome to the applet");
//        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("static/image/folder_16.png")));
        gotoLogin("Main.fxml");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //对话框 Alert Alert.AlertType.CONFIRMATION：反问对话框
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                //设置对话框标题
                alert2.setTitle("Exit");
                //设置内容
                alert2.setHeaderText("Are you sure to exit");
                //显示对话框
                Optional<ButtonType> result = alert2.showAndWait();
                //如果点击OK
                if (result.get() == ButtonType.OK){
                    stage.close();
                } else {
                    event.consume();
                }
            }
        });
        stage.show();
    }
    public void gotoLogin(String fxml){
        LoginController controller = (LoginController) loadGetController(fxml);
        controller.setMain(this);
    }
    public void gotoIndex(String fxml){
        Index2Controller controller = (Index2Controller) loadGetController(fxml);
        controller.setMain(this);
    }
    public BashAction loadGetController(String fxml){
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getClassLoader().getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getClassLoader().getResource(fxml));
        try {
            Parent load = loader.load(in);
            //Scene scene = new Scene(load, (Double) StaticResourcesConfig.getStyle(fxml).get("width"), (Double) StaticResourcesConfig.getStyle(fxml).get("height"));
            Scene scene = new Scene(load);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader.getController();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public Stage getStage() {
        return stage;
    }
}
