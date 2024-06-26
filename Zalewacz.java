import java.io.File;
import java.io.IOException;

public class Zalewacz  extends txtFileReader{

    public static int[][] Zalanie(int[][] Lab, int Columns, int Rows, int EnterX, int EnterY, int ExitX, int ExitY, int backValue, int newValue){
        int CounterZmian = 1;

        int suma = 0;

        hardBorder(Lab, Columns, Rows);

        while(CounterZmian != 0){
            CounterZmian = 0;
            
            for(int i = 1; i < Rows-1; i++){
                for(int j = 1; j < Columns-1; j++){
                    if (Lab[j][i] == backValue){
                        if (Lab[j-1][i] == 1 || Lab[j-1][i] == 5){
                            suma++;
                        }
                        if (Lab[j+1][i] == 1 || Lab[j+1][i] == 5){
                            suma++;
                        }
                        if (Lab[j][i-1] == 1 || Lab[j][i-1] == 5){
                            suma++;
                        }
                        if (Lab[j][i+1] == 1 || Lab[j][i+1] == 5){
                            suma++;
                        }
                        if (suma >= 3){
                            Lab[j][i] = newValue;
                            CounterZmian++;
                        }
                        suma = 0;
                    }
                }
            }
        }

        return Lab;
    }

    public static int[][] ZalanieRaz (int[][] Lab, int Columns, int Rows, int EnterX, int EnterY, int ExitX, int ExitY)
    {
        int CounterZmian = 0;
        int suma = 0;

        hardBorder(Lab, Columns, Rows);

        for(int i = 1; i < Rows-1; i++){
            for(int j = 1; j < Columns-1; j++){
                if (Lab[j][i] == 0){
                    if (Lab[j-1][i] == 1){
                        suma++;
                    }
                    if (Lab[j+1][i] == 1){
                        suma++;
                    }
                    if (Lab[j][i-1] == 1){
                        suma++;
                    }
                    if (Lab[j][i+1] == 1){
                        suma++;
                    }
                    if (suma >= 3){
                        Lab[j][i] = 1;
                        CounterZmian++;
                    }
                    suma = 0;
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

    public static void hardBorder(int[][] Lab, int Columns, int Rows){
        for(int i = 0; i < Rows; i++){
            if (Lab[0][i] == 0)
                Lab[0][i] = 1;
            if (Lab[Columns-1][i] == 0)
                Lab[Columns-1][i] = 1;
        }
        for(int i = 0; i < Columns; i++){
            if (Lab[i][0] == 0)
                Lab[i][0] = 1;
            if (Lab[i][Rows-1] == 0)
                Lab[i][Rows-1] = 1;
        }
    }

    public static boolean czyZalewalne(int[][] Lab, int Columns, int Rows){
        int suma = 0;
        for(int i = 1; i < Rows-1; i++){
            for(int j = 1; j < Columns-1; j++){
                if (Lab[j][i] == 0){
                    if (Lab[j-1][i] == 1){
                        suma++;
                    }
                    if (Lab[j+1][i] == 1){
                        suma++;
                    }
                    if (Lab[j][i-1] == 1){
                        suma++;
                    }
                    if (Lab[j][i+1] == 1){
                        suma++;
                    }
                    if (suma >= 3){
                        return true;
                    }
                    suma = 0;
                }
            }
        }
        return false;
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
