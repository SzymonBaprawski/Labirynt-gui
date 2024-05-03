import java.io.File;
import java.io.IOException;

public class Zalewacz  extends txtFileReader{

    public static int[][] Zalanie(int[][] Lab, int Columns, int Rows, int EnterX, int EnterY, int ExitX, int ExitY){
        int CounterZmian = -1;

        Lab[ExitX][ExitY] = 10;
        Lab[EnterX][EnterY] = 0;

        while(CounterZmian != 0){
            CounterZmian = 0;

            for(int i = 1; i < Rows-1; i++){
                for(int j = 1; j < Columns-1; j++){
                    int suma = Lab[j-1][i] + Lab[j][i-1] + Lab[j+1][i] + Lab[j][i+1];
                    if(suma == 3 && Lab[j][i] == 0)
                    {
                        CounterZmian++;
                        Lab[j][i] = 1;
                    }
                }
            }
        }

        Lab[ExitX][ExitY] = 3;
        Lab[EnterX][EnterY] = 2;

        return Lab;
    }

    public static int[][] ZalanieRaz (int[][] Lab, int Columns, int Rows, int EnterX, int EnterY, int ExitX, int ExitY)
    {
        int CounterZmian = 0;

        Lab[ExitX][ExitY] = 10;
        Lab[EnterX][EnterY] = 0;

        for(int i = 1; i < Rows-1; i++){
            for(int j = 1; j < Columns-1; j++){
                int suma = Lab[j-1][i] + Lab[j][i-1] + Lab[j+1][i] + Lab[j][i+1];
                if(suma == 3 && Lab[j][i] == 0)
                {
                    CounterZmian++;
                    Lab[j][i] = 1;
                }
            }
        }

        if(CounterZmian == 0){
            Lab[ExitX][ExitY] = 3;
            Lab[EnterX][EnterY] = 2;
            System.out.println("Zalany labirynt brak zmian");
            return null;
        }

        Lab[ExitX][ExitY] = 3;
        Lab[EnterX][EnterY] = 2;

        return Lab;
    }

    /* Funkcja tetstujaca ZalanieRaz
    public static void main(String[] args) throws IOException {


        String FilePath = args[0];

        File file = new File(FilePath);

        int[] BeginEnd = null;

        int Columns = Columns(file);
        int Rows = Rows(file);

        BeginEnd = BeginningEnd(file, Columns, Rows);

        int EntryX = BeginEnd[0];
        System.out.println("EntryX: " + EntryX);
        int EntryY = BeginEnd[1];
        System.out.println("EntryY: " + EntryY);
        int ExitX = BeginEnd[2];
        System.out.println("ExitX: " + ExitX);
        int ExitY = BeginEnd[3];
        System.out.println("ExitY: " + ExitY);

        int[][] Lab = null;

        Lab = TxtToInt(file, Columns, Rows);

        Zalanie(Lab, Columns, Rows, EntryX, EntryY, ExitX, ExitY);

        ZalanieRaz(Lab, Columns, Rows, EntryX, EntryY, ExitX, ExitY);


        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                System.out.print(+Lab[j][i]);
            }
            System.out.println();
        }
    }*/

    /* testuje Zalanie
    public static void main(String[] args) throws IOException {

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
    }*/
}
