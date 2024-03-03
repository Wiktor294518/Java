import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {


                ArrayList<Object> Objects = new ArrayList<>();
                ArrayList<Object> Objects_test = new ArrayList<>();
                final int a = Integer.parseInt(args[2]);
                String key1 = args[3];
                String key2 = args[4];

                //wczytywanie z pliku wartosci i tworzenie na ic podstawie "przedmiotu", nastepie dodanie przedmiotu do listy przedmiotow treningowych
                try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
                    String str;
                    while ((str = in.readLine()) != null) {
                        String[] tokens = str.split(";");
                        Object p = new Object();
                        for (int i = 0; i < tokens.length; i++) {
                            if (i + 1 < tokens.length) {
                                p.addAtr(Double.parseDouble(tokens[i]));
                            } else {
                                p.setName(tokens[i]);
                            }
                        }
                        Objects.add(p);
                    }
                }
                //wczytywanie z pliku wartosci i tworzenie na ic podstawie "przedmiotu", nastepie dodanie przedmiotu do listy przedmiotow testowych
                BufferedReader test = new BufferedReader(new FileReader(args[1]));
                String str;
                while ((str = test.readLine()) != null) {
                    String[] tokens = str.split(";");
                    Object p = new Object();
                    for (int i = 0; i < tokens.length; i++) {
                        if (i + 1 < tokens.length) {
                            p.addAtr(Double.parseDouble(tokens[i]));
                        } else {
                            p.setName(tokens[i]);
                        }
                    }
                    Objects_test.add(p);
                }
                double acc = 0;
                int r;
                int y;
                //tworzennie wag
                double[] W = new double[Objects.get(0).atr.size()];
                double t = (int) ((Math.random() * 10) - 5);
                for (int i = 0; i < W.length; i++) {
                    W[i] = (int) ((Math.random() * 10) - 5);

                }

                for (int j = 0; j < Objects.size(); j++) {
                    int net = 0;
                    for (int i = 0; i < W.length; i++) {
                        net += Objects.get(j).atr.get(i) * W[i];
                    }
                    net -= t;
                    //
                    if (Objects.get(j).name.equals(key(1,key1,key2)))
                        r = 1;
                    else
                        r = 0;
                    if (net >= 0)
                        y = 1;
                    else
                        y = 0;
                    //obliczanie wag na nowo
                    for (int i = 0; i < W.length; i++) {
                        W[i] = W[i] + (((r - y) * a) * Objects.get(j).atr.get(i));
                    }
                    t = t - (r - y) * a;
                }

                for (int j = 0; j < Objects_test.size(); j++) {
                    int net = 0;
                    for (int i = 0; i < W.length; i++) {
                        net += Objects_test.get(j).atr.get(i) * W[i];
                    }
                    net -= t;
                    if (Objects_test.get(j).name.equals(key(1,key1,key2)))
                        r = 1;
                    else
                        r = 0;
                    if (net >= 0)
                        y = 1;
                    else
                        y = 0;
                    System.out.println(" odp prawidlowa: " + key(r,key1,key2) + " odp wyliczona: " + key(y,key1,key2));
                    if (r == y) {
                        acc++;
                    }
                }

                acc = acc / Objects_test.size();
                System.out.println(acc);


                Scanner scan = new java.util.Scanner(System.in);
                System.out.println("Tryb wpisywania ręcznego");
                while (true) {
                    double[] attributes = new double[Objects.get(0).atr.size()];
                    for (int i = 0; i < attributes.length; i++){
                        System.out.println("podaj atrybut " + i + " w postaci: (liczba , liczba)");
                        boolean error = true;
                        do{ if(scan.hasNextDouble()){
                            attributes[i] = scan.nextDouble();
                            error= false;
                        }
                        else {
                            System.out.println("zly format liczby podaj liczbe jeszcze raz");}
                            scan.nextLine();
                        }while(error);
                    //scaner
                }
                int net = 0;
                for (int i = 0; i < W.length; i++) {
                    net += attributes[i] * W[i];
                }
                net -= t;
                if (net >= 0)
                    y = 1;
                else
                    y = 0;
                System.out.println(" odp wyliczona: " + key(y,key1,key2));
            }

            }

    public static String key(int k, String key1, String key2){
                if (k==1)
                    return  key2;//setosa
                else
                    return key1;//versicolor
    }

}
