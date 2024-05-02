import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/*
* Dobra ogółe to zamiast zwracac tablice funkcja zwraca arreyliste x y x y itd
* czyli na karzedj parzystej jest x a na nieparzystej jest y*/


public class Follower extends Zalewacz {

    public static ArrayList<Integer> Follow (int[][] Lab, int Columns, int Rows, int EntryX, int EntryY, int ExitX, int ExitY) {
        ArrayList<Integer> coords = new ArrayList<Integer>();
        int x = EntryX;
        int y = EntryY;
        Lab[ExitX][ExitY] = 0;

        while (x != ExitX || y != ExitY) {
            if(Lab[x+1][y] == 0) {
                Lab[x][y] =2;
                x++;
                coords.add(x);
                coords.add(y);
            }else if(Lab[x][y+1] == 0){
                Lab[x][y] =2;
                y++;
                coords.add(x);
                coords.add(y);
            }else if(Lab[x-1][y] == 0){
                Lab[x][y] =2;
                x--;
                coords.add(x);
                coords.add(y);
            }else if(Lab[x][y-1] == 0) {
                Lab[x][y] = 2;
                y--;
                coords.add(x);
                coords.add(y);
            }
        }

        for(int i = 0; i <Rows; i++) {
            for(int j = 0; j < Columns; j++) {
                if(Lab[j][i] == 2) {
                    Lab[j][i] = 0;
                }
            }
        }

        Lab[ExitX][ExitY] = 3;
        Lab[EntryX][EntryY] = 2;

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
