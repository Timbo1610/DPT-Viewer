package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("DPT - Dynamic Public Transport");

        Lock lock = new Lock();

        FXMLController maps = new FXMLController(lock);

        Controller controller = new Controller(lock,maps);
        Thread controlThread  = new Thread(controller);

        controlThread.start();

        Pane root = maps.getMapView();

        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();

        Button bt1 = new Button("refresh");

        Pane toolbox = new Pane();

        root.getChildren().add(toolbox);
        toolbox.setLayoutX(20);
        toolbox.setLayoutY(200);

        toolbox.getChildren().add(bt1);

        bt1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                maps.clear();
                maps.refresh();
                controller.select();
            }
        });










    }


    public static void main(String[] args) {
        launch(args);
    }
}
