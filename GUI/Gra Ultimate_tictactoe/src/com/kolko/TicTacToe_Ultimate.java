package com.kolko;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class TicTacToe_Ultimate {
    private static ObservableList<Runda> history = FXCollections.observableArrayList();
    static Logic logic = new Logic();
    static int NrRundy = 1;
    static int KomX;
    static int KomY;
    private static GridPane gridPane = new GridPane();
    public static boolean czy_kom;
    private static Board[][] board = new Board[3][3];

    public static void Gra(Stage primaryStage) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(8);
        gridPane.setVgap(8);


        for (int mainCol = 0; mainCol < 3; mainCol++) {
            for (int mainRow = 0; mainRow < 3; mainRow++) {
                Board miniBoard = new Board();

                GridPane miniGridPane = new GridPane();
                miniGridPane.setAlignment(Pos.CENTER);
                miniGridPane.setHgap(2);
                miniGridPane.setVgap(2);

                for (int col = 0; col < 3; col++) {
                    final int fcol = col;
                    for (int row = 0; row < 3; row++) {
                        final int frow = row;
                        Button button = new Button();
                        button.setPrefSize(60, 60);
                        button.setOnAction(e -> {miniBoard.handleButtonClick(fcol, frow);});
                        miniGridPane.add(button, col, row);
                        miniBoard.setButton(col, row, button);
                    }
                }
                gridPane.add(miniGridPane, mainCol, mainRow);
                board[mainCol][mainRow] = miniBoard;
            }
        }

        ListView<Runda> historyListView = new ListView<>();
        historyListView.setItems(history);

        VBox vbox = new VBox(gridPane, historyListView);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);

        Scene scene = new Scene(vbox, 650, 900);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ultimate Tic Tac Toe");
        primaryStage.show();

        scene.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            if (keyCode.isKeypadKey()) {
                handleNumpadKeyPress(keyCode);}
        });
    }

    public static void handleNumpadKeyPress(KeyCode keyCode) {
        int row = -1;
        int col = -1;

        switch (keyCode) {
            case NUMPAD7:
                row = 0;
                col = 0;
                break;
            case NUMPAD8:
                row = 0;
                col = 1;
                break;
            case NUMPAD9:
                row = 0;
                col = 2;
                break;
            case NUMPAD4:
                row = 1;
                col = 0;
                break;
            case NUMPAD5:
                row = 1;
                col = 1;
                break;
            case NUMPAD6:
                row = 1;
                col = 2;
                break;
            case NUMPAD1:
                row = 2;
                col = 0;
                break;
            case NUMPAD2:
                row = 2;
                col = 1;
                break;
            case NUMPAD3:
                row = 2;
                col = 2;
                break;
        }

        //jeśli następna plansza jest już wybrana
        if (row != -1 && logic.NastępnaPlanszaY != 3 ) {
            board[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY].handleButtonClick(col, row);
        }
        //jesli następnej planszy jeszcze nie wybrano
        else if (row != -1) {
            logic.UstawNastepnaPlansza(col,row);
            if(logic.NastępnaPlanszaY == 3){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Uwaga");
                alert.setHeaderText(null);
                alert.setContentText("Wybrana plansza jest zajęta, wybierz jescze raz");
                alert.showAndWait();
            }
        }
    }

    public static void wygranaGraczaX() {
        gridPane.setDisable(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wygrana gracza X");
        alert.setHeaderText(null);
        alert.setContentText("Gracz X wygrał!");
        alert.showAndWait();
    }
    public static void wygranaGraczaO() {
        gridPane.setDisable(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wygrana gracza O");
        alert.setHeaderText(null);
        alert.setContentText("Gracz O wygrał!");
        alert.showAndWait();
    }
    public static void GraZGraczem(Button square) {
        //sprawdzanie źródła sygnału buttona
        int kafelekY = (GridPane.getRowIndex(square) == null ? 0 : GridPane.getRowIndex(square));
        int kafelekX = (GridPane.getColumnIndex(square) == null ? 0 : GridPane.getColumnIndex(square));
        int PlanszaY = (GridPane.getRowIndex(square.getParent()) == null ? 0 : GridPane.getRowIndex(square.getParent()));
        int PlanszaX = (GridPane.getColumnIndex(square.getParent()) == null ? 0 : GridPane.getColumnIndex(square.getParent()));
        //sprawdzanie czy dobry ruch, jeśli tak zaznaczanie x lub o
        if (logic.CzyDobryRuch(PlanszaX, PlanszaY)) {
            if (NrRundy % 2 == 1) {
                Image x = new Image("file:///C:\\Users\\Kret\\Desktop\\Projekt_3\\src\\com\\kolko\\x_icon.png");
                ImageView image_x = new ImageView(x);
                image_x.setFitWidth(40);
                image_x.setFitHeight(40);
                square.setGraphic(image_x);
                logic.setX(PlanszaX, PlanszaY, kafelekX, kafelekY);
                history.add(new Runda(NrRundy, 'X', PlanszaX, PlanszaY, kafelekX, kafelekY));
            } else {
                Image o = new Image("file:///C:\\Users\\Kret\\Desktop\\Projekt_3\\src\\com\\kolko\\o_icon.png");
                ImageView image_o = new ImageView(o);
                image_o.setFitWidth(40);
                image_o.setFitHeight(40);
                square.setGraphic(image_o);
                logic.setO(PlanszaX, PlanszaY, kafelekX, kafelekY);
                history.add(new Runda(NrRundy, 'O', PlanszaX, PlanszaY, kafelekX, kafelekY));
            }
            square.setDisable(true);
            if(logic.CzyPelnaPlansza(PlanszaX,PlanszaY)){
                board[PlanszaX][PlanszaY].Remis();
            }
            if (logic.CzyzwyciestwoPlanszyX(PlanszaX, PlanszaY)) {
                logic.UstawWygranaX(PlanszaX, PlanszaY);
                board[PlanszaX][PlanszaY].WygranaPlanszyX();
                if (logic.CzyWygranaGryX()) {
                    wygranaGraczaX();
                }
            }
            if(logic.CzyzwyciestwoPlanszyO(PlanszaX, PlanszaY)) {
                logic.UstawWygranaO(PlanszaX, PlanszaY);
                board[PlanszaX][PlanszaY].WygranaPlanszyO();
                if (logic.CzyWygranaGryO()) {
                    wygranaGraczaO();
                }
            }
            logic.UstawNastepnaPlansza(kafelekX,kafelekY);
            NrRundy++;
        }
    }

    public static void GraZKom(Button square) {
        //sprawdzanie źródła sygnału buttona
        int kafelekY = (GridPane.getRowIndex(square) == null ? 0 : GridPane.getRowIndex(square));
        int kafelekX = (GridPane.getColumnIndex(square) == null ? 0 : GridPane.getColumnIndex(square));
        int PlanszaY = (GridPane.getRowIndex(square.getParent()) == null ? 0 : GridPane.getRowIndex(square.getParent()));
        int PlanszaX = (GridPane.getColumnIndex(square.getParent()) == null ? 0 : GridPane.getColumnIndex(square.getParent()));

        //sprawdzanie czy dobry ruch, jeśli tak zaznaczanie x
        if (logic.CzyDobryRuch(PlanszaX, PlanszaY)) {

            Image x = new Image("file:///C:\\Users\\Kret\\Desktop\\Projekt_3\\src\\com\\kolko\\x_icon.png");
            ImageView image_x = new ImageView(x);
            image_x.setFitWidth(40);
            image_x.setFitHeight(40);
            square.setGraphic(image_x);
            logic.setX(PlanszaX, PlanszaY, kafelekX, kafelekY);
            history.add(new Runda(NrRundy, 'X', PlanszaX, PlanszaY, kafelekX, kafelekY));
            square.setDisable(true);
            //sprawdzanie remisu i wygrania
            if (logic.CzyPelnaPlansza(PlanszaX, PlanszaY)) {
                board[PlanszaX][PlanszaY].Remis();
            }
            if (logic.CzyzwyciestwoPlanszyX(PlanszaX, PlanszaY)) {
                logic.UstawWygranaX(PlanszaX, PlanszaY);
                board[PlanszaX][PlanszaY].WygranaPlanszyX();
                if (logic.CzyWygranaGryX()) {
                    wygranaGraczaX();
                    return;
                }
            }
            //ruch komputera, spwawdzanie remisu i wygrania
            logic.UstawNastepnaPlansza(kafelekX, kafelekY);
            NrRundy++;
            RuchKom();
            NrRundy++;
            if (logic.CzyPelnaPlansza(logic.NastępnaPlanszaX, logic.NastępnaPlanszaY)) {
                board[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY].Remis();
            }
            if (logic.CzyzwyciestwoPlanszyO(logic.NastępnaPlanszaX, logic.NastępnaPlanszaY)) {
                if (!logic.CzyPelnaPlansza(logic.NastępnaPlanszaX, logic.NastępnaPlanszaY)) {
                    logic.UstawWygranaO(logic.NastępnaPlanszaX, logic.NastępnaPlanszaY);
                    board[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY].WygranaPlanszyO();
                }
                if (logic.CzyWygranaGryO()) {
                    wygranaGraczaO();
                }
            }
            logic.UstawNastepnaPlansza(KomX, KomY);
        }
    }
        public static void RuchKom(){
            if(logic.NastępnaPlanszaY!=3) {
                AlgorytmKom();
            }
            else{
                //losowanie wybrania następnej planszy
                List<Integer> listPlanszaX = new ArrayList();
                List<Integer> listPlanszaY = new ArrayList();
                int ileWolnychPlansz = 0;
                for(int i =0; i<3;i++){
                    for(int j=0;j<3;j++){
                        if(logic.planszawygranych[i][j]==' ') {
                            listPlanszaX.add(i);
                            listPlanszaY.add(j);
                            ileWolnychPlansz++;
                        }
                    }
                }
                int c =(int)(Math.random()*ileWolnychPlansz);
                logic.NastępnaPlanszaY=listPlanszaY.get(c);
                logic.NastępnaPlanszaX=listPlanszaX.get(c);
                AlgorytmKom();
            }
            if(KomX ==4){
                //losowanie wybrania następnego ruchu
                List<Integer> listKafelkiX = new ArrayList();
                List<Integer> listKafelkiY = new ArrayList();
                int ileWolnychKafelkow=0;
                for(int i =0;i<3;i++){
                    for(int j =0;j<3;j++) {
                        if(logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][j] == ' '){
                            listKafelkiX.add(i);
                            listKafelkiY.add(j);
                            ileWolnychKafelkow++;
                        }
                    }
                }
                int m =(int)(Math.random()*ileWolnychKafelkow);
                KomX =listKafelkiX.get(m);
                KomY =listKafelkiY.get(m);
            }
                Image o = new Image("file:///C:\\Users\\Kret\\Desktop\\o_icon.png");
                ImageView image_o = new ImageView(o);
                image_o.setFitWidth(40);
                image_o.setFitHeight(40);
                board[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY].buttons[KomX][KomY].setGraphic(image_o);
                board[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY].buttons[KomX][KomY].setDisable(true);
                logic.setO(logic.NastępnaPlanszaX, logic.NastępnaPlanszaY, KomX, KomY);
                history.add(new Runda(NrRundy, 'O', logic.NastępnaPlanszaX, logic.NastępnaPlanszaY, KomX, KomY));

        }
        public static void AlgorytmKom() {

            //pionowo i poziomo
            for (int i = 0; i < 3; i++) {
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][0] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][1] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][2] == ' ') {
                    KomX =i;
                    KomY =2;
                    return;
                }
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][0] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][2] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][1] == ' ') {
                    KomX =i;
                    KomY =1;
                    return;
                }
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][2] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][1] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][0] == ' ') {
                    KomX =i;
                    KomY =0;
                    return;
                }
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][i] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][i] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][i] == ' ') {
                    KomX =2;
                    KomY =i;
                    return;
                }
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][i] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][i] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][i] == ' ') {
                    KomX =1;
                    KomY =i;
                    return;
                }
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][i] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][i] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][i] == ' ') {
                    KomX =0;
                    KomY =i;
                    return;
                }
            }
            //na ukos
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][0] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][2] == ' ') {
                KomX =2;
                KomY =2;
                return;
            }
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][0] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][2] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == ' ') {
                KomX =1;
                KomY =1;
                return;
            }
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][2] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][0] == ' ') {
                KomX =0;
                KomY =0;
                return;
            }
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][2] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][0] == ' ') {
                KomX =2;
                KomY =0;
                return;
            }
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][2] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][0] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == ' ') {
                KomX =1;
                KomY =1;
                return;
            }
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][0] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == 'O' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][2] == ' ') {
                KomX =0;
                KomY =2;
                return;
            }
            //poziomo i pionowo
            for (int i = 0; i < 3; i++) {
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][0] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][1] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][2] == ' ') {
                    KomX =i;
                    KomY =2;
                    return;
                }
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][0] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][2] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][1] == ' ') {
                    KomX =i;
                    KomY =1;
                    return;
                }
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][2] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][1] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][i][0] == ' ') {
                    KomX =i;
                    KomY =0;
                    return;
                }
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][i] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][i] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][i] == ' ') {
                    KomX =2;
                    KomY =1;
                    return;
                }
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][i] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][i] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][i] == ' ') {
                    KomX =1;
                    KomY =i;
                    return;
                }
                if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][i] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][i] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][i] == ' ') {
                    KomX =0;
                    KomY =1;
                    return;
                }
            }
            //na ukos
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][0] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][2] == ' ') {
                KomX =2;
                KomY =2;
                return;
            }
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][0] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][2] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == ' ') {
                KomX =1;
                KomY =1;
                return;
            }
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][2] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][0] == ' ') {
                KomX =0;
                KomY =0;
                return;
            }
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][2] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][0] == ' ') {
                KomX =2;
                KomY =0;
                return;
            }
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][2] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][0] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == ' ') {
                KomX =1;
                KomY =1;
                return;
            }
            if (logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][2][0] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][1][1] == 'X' && logic.Plansza[logic.NastępnaPlanszaX][logic.NastępnaPlanszaY][0][2] == ' ') {
                KomX =0;
                KomY =2;
                return;
            }
            //jesli nic nie pasuje, losowo
            else{
                KomX =4;
            }

        }

}