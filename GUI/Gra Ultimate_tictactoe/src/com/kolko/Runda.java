package com.kolko;

public class Runda {
    private char player;
    private int mainRow;
    private int mainCol;
    private int row;
    private int col;
    private int runda;
    public Runda(int runda, char player,  int mainCol, int mainRow, int col,int row) {
        this.runda = runda;
        this.player = player;
        this.mainRow = mainRow;
        this.mainCol = mainCol;
        this.row = row;
        this.col = col;
    }

    public String jakiePole(int row, int col){
        if(col==0 && row==0){
            return "Lewy górny";
        }else if(col==1 && row==0){
            return "Srodkowy górny";
        }else if(col==2 && row==0){
            return "Prawy górny";
        }else if(col==0 && row==1){
            return "Lewy srodkowy";
        }else if(col==1 && row==1){
            return "centralnie srodkowy";
        }else if(col==2 && row==1){
            return "Prawy srodkowy";
        }else if(col==0 && row==2){
            return "Lewy dolny";
        } else if(col==1 && row==2){
            return "Srodkowy dolny";
        }else if(col==2 && row==2){
            return "Prawy dolny";
        }
        return "nic";
    }

    @Override
    public String toString() {
        return "Runda: "+ runda + " Gracz: " + player + ", Plansza: (" + jakiePole(mainRow,mainCol) + "), Pole: (" + jakiePole(row,col)+ ")";
    }
}

