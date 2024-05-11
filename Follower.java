import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
* Dobra ogółe to zamiast zwracac tablice funkcja zwraca arreyliste x y x y itd
* czyli na karzedj parzystej jest x a na nieparzystej jest y*/


public class Follower extends Zalewacz {

    public static ArrayList<Integer> Follow (int[][] Lab, int Columns, int Rows, int EntryX, int EntryY, int ExitX, int ExitY) {
        ArrayList<Integer> coords = new ArrayList<Integer>();
        
        char kierunek = 'N';
        int x = EntryX;
        int y = EntryY;

        coords.add(x);
        coords.add(y);


        if (x == 0){
            kierunek = 'E';
        } else if (y == 0){
            kierunek = 'S';
        } else if (x == Columns-1){
            kierunek = 'W';
        } else if (y == Rows-1){
            kierunek = 'N';
        } else{
            //sprawdzenie z której strony jest wolne pole
            if (Lab[x+1][y] != 1){
                kierunek = 'E';
            } else if (Lab[x-1][y] != 1){
                kierunek = 'W';
            } else if (Lab[x][y+1] != 1){
                kierunek = 'S';
            } else if (Lab[x][y-1] != 1){
                kierunek = 'N';
            } else {
                System.out.println("Ups, coś poszło nie tak\n");
            }
        }

        while (x != ExitX || y != ExitY){
            if (kierunek == 'E'){
                if (y > 0 && Lab[x][y-1] != 1){
                    kierunek = 'N';
                    y--;
                    coords.add(x);
                    coords.add(y);
                } else if (x < Columns && Lab[x+1][y] != 1){
                    x++;
                    coords.add(x);
                    coords.add(y);
                } else if (y < Rows && Lab[x][y+1] != 1){
                    kierunek = 'S';
                    y++;
                    coords.add(x);
                    coords.add(y);
                } else if (x > 0 && Lab[x-1][y] != 1){
                    kierunek = 'W';
                    x--;
                    coords.add(x);
                    coords.add(y);
                }
            }
    
            else if (kierunek == 'W'){
                if (y > 0 && Lab[x][y+1] != 1){
                    kierunek = 'S';
                    y++;
                    coords.add(x);
                    coords.add(y);
                } else if (x > 0 && Lab[x-1][y] != 1){
                    x--;
                    coords.add(x);
                    coords.add(y);
                } else if (y > 0 && Lab[x][y-1] != 1){
                    kierunek = 'N';
                    y--;
                    coords.add(x);
                    coords.add(y);
                } else if (x < Columns && Lab[x+1][y] != 1){
                    kierunek = 'E';
                    x++;
                    coords.add(x);
                    coords.add(y);
                }
            }
    
            else if (kierunek == 'N'){
                if (x > 0 && Lab[x-1][y] != 1){
                    kierunek = 'W';
                    x--;
                    coords.add(x);
                    coords.add(y);
                } else if (y > 0 && Lab[x][y-1] != 1){
                    y--;
                    coords.add(x);
                    coords.add(y);
                } else if (x < Columns && Lab[x+1][y] != 1){
                    kierunek = 'E';
                    x++;
                    coords.add(x);
                    coords.add(y);
                } else if (y > 0 && Lab[x][y+1] != 1){
                    kierunek = 'S';
                    y++;
                    coords.add(x);
                    coords.add(y);
                }
            }
    
    
            else if (kierunek == 'S'){
                if (x < Columns && Lab[x+1][y] != 1){
                    kierunek = 'E';
                    x++;
                    coords.add(x);
                    coords.add(y);
                } else if (y < Rows && Lab[x][y+1] != 1){
                    y++;
                    coords.add(x);
                    coords.add(y);
                } else if (x > 0 && Lab[x-1][y] != 1){
                    kierunek = 'W';
                    x--;
                    coords.add(x);
                    coords.add(y);
                } else if (y < Rows && Lab[x][y-1] != 1){
                    kierunek = 'N';
                    y--;
                    coords.add(x);
                    coords.add(y);
                }
            }
        }
        

        return coords;
    }

    

    /*public static void main(String[] args) throws IOException {
        String FilePath = args[0];

        File file = new File(FilePath);

        int[] BeginEnd= null;

        int Columns = Columns(file);
        int Rows = Rows(file);

        BeginEnd = BeginningEnd(file,Columns,Rows);

        int EntryX = BeginEnd[0];
        System.out.println("EntryX: " + EntryX);
        int EntryY = BeginEnd[1];
        System.out.println("EntryY: " + EntryY);
        int ExitX = BeginEnd[2];
        System.out.println("ExitX: " + ExitX);
        int ExitY = BeginEnd[3];
        System.out.println("ExitY: " + ExitY);

        int [][] Lab = null;

        Lab = TxtToInt(file, Columns, Rows);

        Zalanie(Lab, Columns, Rows, EntryX, EntryY, ExitX, ExitY);

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                System.out.print(+Lab[j][i]);
            }
            System.out.println();
        }

        ArrayList<Integer> coords = Follow(Lab,Columns,Rows,EntryX,EntryY,ExitX,ExitY);

        for (int i = 0; i < coords.size(); i++) {
            System.out.println("X: "+coords.get(i)+ " Y: "+coords.get(i+1));
            i++;
        }

        }*/
    }
