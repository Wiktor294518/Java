package com.kolko;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Board {
    public Button[][] buttons = new Button[3][3];

    public void setButton(int col, int row, Button button) {
        buttons[col][row] = button;
    }

    public void handleButtonClick(int col, int row) {
        Button button = buttons[col][row];
        if(TicTacToe_Ultimate.czy_kom)
        TicTacToe_Ultimate.GraZKom(button);
        else
        TicTacToe_Ultimate.GraZGraczem(button);
    }
    public void WygranaPlanszyX() {
        // Czyszczenie planszy i oznaczenie wygranej dla X
        for (Button[] rowArr : buttons) {
            for (Button button : rowArr) {
                Image x = new Image("file:///C:\\Users\\Kret\\Desktop\\Projekt_3\\src\\com\\kolko\\x_icon.png");
                ImageView image_x = new ImageView(x);
                image_x.setFitWidth(40);
                image_x.setFitHeight(40);
                button.setGraphic(image_x);
                button.setDisable(true);
            }
        }
    }
    public void Remis() {
        for (Button[] rowArr : buttons) {
            for (Button button : rowArr) {
                Image x = new Image("file:///C:\\Users\\Kret\\Desktop\\Projekt_3\\src\\com\\kolko\\r_icon.png");
                ImageView image_x = new ImageView(x);
                image_x.setFitWidth(40);
                image_x.setFitHeight(40);
                button.setGraphic(image_x);
                button.setDisable(true);
            }
        }
    }

    public void WygranaPlanszyO() {
        // Czyszczenie planszy i oznaczenie wygranej dla O
        for (Button[] rowArr : buttons) {
            for (Button button : rowArr) {
                Image o = new Image("file:///C:\\Users\\Kret\\Desktop\\Projekt_3\\src\\com\\kolko\\o_icon.png");
                ImageView image_o = new ImageView(o);
                image_o.setFitWidth(40);
                image_o.setFitHeight(40);
                button.setGraphic(image_o);
                button.setDisable(true);
            }
        }
    }
}
