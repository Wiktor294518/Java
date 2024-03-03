package com.kolko;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wybór trybu gry");

        Label label = new Label("Wybierz tryb gry:");
        Button button1 = new Button("Gra z komputerem");
        Button button2 = new Button("Gra z innym graczem");

        button1.setOnAction(e -> {
            primaryStage.close();
            TicTacToe_Ultimate.czy_kom = true;
            TicTacToe_Ultimate.Gra(primaryStage);
        });

        button2.setOnAction(e -> {
            primaryStage.close();
            TicTacToe_Ultimate.czy_kom = false;
            TicTacToe_Ultimate.Gra(primaryStage);
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, button1, button2);

        Scene scene = new Scene(vbox, 300, 200);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public void window() {
        launch();
    }


}