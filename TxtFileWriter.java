import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class TxtFileWriter extends Zalewacz {
    public static void WriteToFile(String fileName, int[][] Lab, int Columns, int Rows, int EnterX, int EnterY, int ExitX, int ExitY) throws IOException {
        FileWriter writer = new FileWriter(fileName);

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                if(Lab[j][i]==0){
                    writer.write(" ");
                }else if(Lab[j][i]==1){
                    writer.write("X");
                }else if(j == EnterX || j == EnterY){
                    writer.write("P");
                }else if(j == ExitX || j == ExitY){
                    writer.write("K");
                }
            }
            writer.write("\n");
        }
        writer.close();
    }
    /*public static void main(String[] args) throws IOException {

        String FilePath = args[0];

        File file = new File(FilePath);

        int[] BeginEnd= null;

        int Columns = 0;
        Columns = Columns(file);

        int Rows = 0;

        Rows = Rows(file);

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

        WriteToFile("nowy_lab.txt", Lab, Columns, Rows, EntryX, EntryY, ExitX, ExitY);

        Zalanie(Lab, Columns, Rows, EntryX, EntryY, ExitX, ExitY);


        WriteToFile("zalanie.txt", Lab, Columns, Rows, EntryX, EntryY, ExitX, ExitY);

    }*/
}
