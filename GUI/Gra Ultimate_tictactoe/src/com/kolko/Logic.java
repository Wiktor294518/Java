package com.kolko;

public class Logic {


    static final int DIMENSIONS = 3;

    public final char[][][][] Plansza;
    public final char[][] planszawygranych;
    public int NastępnaPlanszaX ;
    public int NastępnaPlanszaY ;
    public Logic() {
        Plansza = new char[DIMENSIONS][DIMENSIONS][DIMENSIONS][DIMENSIONS];
        planszawygranych = new char[DIMENSIONS][DIMENSIONS];
        NastępnaPlanszaX = DIMENSIONS;
        NastępnaPlanszaY = DIMENSIONS;
       for (int i=0;i<DIMENSIONS;i++){
           for (int j=0;j<DIMENSIONS;j++){
               for (int p=0;p<DIMENSIONS;p++){
                   for (int k=0;k<DIMENSIONS;k++){
                       Plansza[i][j][p][k]=' ';
                   }
               }
           }
       }
        for (int i=0;i<DIMENSIONS;i++){
            for (int j=0;j<DIMENSIONS;j++){
                planszawygranych[i][j]=' ';
            }
        }
    }
    //Stawianie X
    public void setX(int PlanszaX, int PlanszaY, int kafelekX, int kafelekY) {
        Plansza[PlanszaX][PlanszaY][kafelekX][kafelekY] = 'X';
    }
    //Stawianie O
    public void setO(int PlanszaX, int PlanszaY, int kafelekX, int kafelekY) {
        Plansza[PlanszaX][PlanszaY][kafelekX][kafelekY] = 'O';
    }
    //Sprawdza czy daną planszę wygrał X
    public boolean CzyzwyciestwoPlanszyX(int PlanszaX, int PlanszaY) {
        for (int row = 0; row < DIMENSIONS; row++) {
            if ((Plansza[PlanszaX][PlanszaY][row][0] == 'X' )&& Plansza[PlanszaX][PlanszaY][row][0] == Plansza[PlanszaX][PlanszaY][row][1] && Plansza[PlanszaX][PlanszaY][row][0] == Plansza[PlanszaX][PlanszaY][row][2]) {
                return true;
            }
        }
        for (int col = 0; col < DIMENSIONS; col++) {
            if ((Plansza[PlanszaX][PlanszaY][0][col] == 'X' ) && Plansza[PlanszaX][PlanszaY][0][col] == Plansza[PlanszaX][PlanszaY][1][col] && Plansza[PlanszaX][PlanszaY][0][col] == Plansza[PlanszaX][PlanszaY][2][col]) {
                return true;
            }
        }
        return ((Plansza[PlanszaX][PlanszaY][0][0] == 'X' ) && 'X' == Plansza[PlanszaX][PlanszaY][1][1] && 'X' == Plansza[PlanszaX][PlanszaY][2][2])
                || ((Plansza[PlanszaX][PlanszaY][0][2] == 'X' ) && 'X' == Plansza[PlanszaX][PlanszaY][1][1] && 'X' == Plansza[PlanszaX][PlanszaY][2][0]);
    }
    //Sprawdza czy daną planszę wygrał O
    public boolean CzyzwyciestwoPlanszyO(int PlanszaX, int PlanszaY) {
        for (int row = 0; row < DIMENSIONS; row++) {
            if ((Plansza[PlanszaX][PlanszaY][row][0] == 'O')&& Plansza[PlanszaX][PlanszaY][row][0] == Plansza[PlanszaX][PlanszaY][row][1] && Plansza[PlanszaX][PlanszaY][row][0] == Plansza[PlanszaX][PlanszaY][row][2]) {
                return true;
            }
        }
        for (int col = 0; col < DIMENSIONS; col++) {
            if ((Plansza[PlanszaX][PlanszaY][0][col] == 'O') && Plansza[PlanszaX][PlanszaY][0][col] == Plansza[PlanszaX][PlanszaY][1][col] && Plansza[PlanszaX][PlanszaY][0][col] == Plansza[PlanszaX][PlanszaY][2][col]) {
                return true;
            }
        }
        return ((Plansza[PlanszaX][PlanszaY][0][0] == 'O') && 'O' == Plansza[PlanszaX][PlanszaY][1][1] && 'O' == Plansza[PlanszaX][PlanszaY][2][2])
                || ( Plansza[PlanszaX][PlanszaY][0][2] == 'O' && 'O' == Plansza[PlanszaX][PlanszaY][1][1] && 'O' == Plansza[PlanszaX][PlanszaY][2][0]);
    }
    //Sprawdzanie czy X wygrał całą grę
    public boolean CzyWygranaGryX(){
        for (int row = 0; row < DIMENSIONS; row++) {
            if ((planszawygranych[row][0] == 'X' )&& planszawygranych[row][0] == planszawygranych[row][1] && planszawygranych[row][0] == planszawygranych[row][2]) {
                return true;
            }
        }
        for (int col = 0; col < DIMENSIONS; col++) {
            if ((planszawygranych[0][col] == 'X' ) && planszawygranych[0][col] == planszawygranych[1][col] && planszawygranych[0][col] == planszawygranych[2][col]) {
                return true;
            }
        }
        return ((planszawygranych[0][0] == 'X' ) && planszawygranych[0][0] == planszawygranych[1][1] && planszawygranych[0][0] == planszawygranych[2][2])
                || ((planszawygranych[0][2] == 'X' ) && planszawygranych[0][2] == planszawygranych[1][1] && planszawygranych[0][2] == planszawygranych[2][0]);
    }
    //Sprawdzanie czy O wygrał całą grę
    public boolean CzyWygranaGryO(){
        for (int row = 0; row < DIMENSIONS; row++) {
            if ((planszawygranych[row][0] == 'O')&& planszawygranych[row][0] == planszawygranych[row][1] && planszawygranych[row][0] == planszawygranych[row][2]) {
                return true;
            }
        }
        for (int col = 0; col < DIMENSIONS; col++) {
            if ((planszawygranych[0][col] == 'O') && planszawygranych[0][col] == planszawygranych[1][col] && planszawygranych[0][col] == planszawygranych[2][col]) {
                return true;
            }
        }
        return ((planszawygranych[0][0] == 'O') && planszawygranych[0][0] == planszawygranych[1][1] && planszawygranych[0][0] == planszawygranych[2][2])
                || ((planszawygranych[0][2] == 'O') && planszawygranych[0][2] == planszawygranych[1][1] && planszawygranych[0][2] == planszawygranych[2][0]);
    }
    //Ustawienie jaka plansza jest następna
    public void UstawNastepnaPlansza(int KafelekX, int KafelekY){
        NastępnaPlanszaX = KafelekX;
        NastępnaPlanszaY = KafelekY;
        if(CzyPelnaPlansza(KafelekX,KafelekY)){
            NastępnaPlanszaX = DIMENSIONS;
            NastępnaPlanszaY = DIMENSIONS;
        }
    }
    //Sprawdzenie czy ruch jest poprawny
    public boolean CzyDobryRuch(int PlanszaX, int PlanszaY){
        if((NastępnaPlanszaY == PlanszaY && NastępnaPlanszaX == PlanszaX) || NastępnaPlanszaY==DIMENSIONS){

            return true;
        }
        else
            return false;
    }
    //ustawia daną planszę jako wygraną dla X
    public void UstawWygranaX(int PlanszaX, int PlanszaY){
        for(int i=0;i<DIMENSIONS;i++){
            for(int j=0;j<DIMENSIONS;j++){
            Plansza[PlanszaX][PlanszaY][j][i]='X';
            }
        }
        planszawygranych[PlanszaX][PlanszaY]='X';
    }
    //Ustawia daną planszę jako wygraną dla O
    public void UstawWygranaO(int PlanszaX, int PlanszaY){
        for(int i=0;i<DIMENSIONS;i++){
            for(int j=0;j<DIMENSIONS;j++){
                Plansza[PlanszaX][PlanszaY][j][i]='O';
            }
        }
        planszawygranych[PlanszaX][PlanszaY]='O';
    }
    //Sprawdza czy dana plansza nie jest zapełoniona
    public boolean CzyPelnaPlansza(int PlanszaX, int PlanszaY) {
        for (int spaceRow = 0; spaceRow < DIMENSIONS; spaceRow++) {
            for (int spaceCol = 0; spaceCol < DIMENSIONS; spaceCol++) {
                if (Plansza[PlanszaX][PlanszaY][spaceRow][spaceCol] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
