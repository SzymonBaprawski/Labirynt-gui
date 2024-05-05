import java.io.File;
import java.io.IOException;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        
        if (args.length == 0) {
            System.out.println("Brak argumentów - koniec działania programu");
            return;
        }

        String fileName = args[0];
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (!fileType.equals("txt") && !fileType.equals("bin")) {
            System.out.println("Nieobsługiwany typ pliku - koniec działania programu");
            return;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("Plik nie istnieje - koniec działania programu");
            return;
        }

        int Columns = 0;
        int Rows = 0;
        int EnterX = 0;
        int EnterY = 0;
        int ExitX = 0;
        int ExitY = 0;

        File file2 = new File("Labirynt.png");
        file2.delete();
        
        

        if (fileType.equals("txt")) {
            try {
                Columns = txtFileReader.Columns(file);
                Rows = txtFileReader.Rows(file);
                int[][] Lab = txtFileReader.TxtToInt(file, Columns, Rows);
                int[] BeginEnd = txtFileReader.BeginningEnd(file, Columns, Rows);
                EnterX = BeginEnd[0];
                EnterY = BeginEnd[1];
                ExitX = BeginEnd[2];
                ExitY = BeginEnd[3];


                System.out.println("Columns: " + Columns + "\nRows: " + Rows + "\nEnterX: " + EnterX + "\nEnterY: " + EnterY + "\nExitX: " + ExitX + "\nExitY: " + ExitY);
                System.out.println("Labirynt:");
               /* for (int i = 0; i < Rows; i++) {
                    for (int j = 0; j < Columns; j++) {
                        System.out.print(Lab[j][i]);
                    }
                    System.out.println();
                }*/
                new GUI(Lab, Columns, Rows, EnterX, EnterY, ExitX, ExitY);


            } catch (Exception e) {
                System.err.println(e);
                System.out.println("Błąd odczytu pliku - koniec działania programu");
                return;
            }
        } else if (fileType.equals("bin")) {
            
            String inputFile = args[0];

            int[][] Lab = null;

            short[] Data = null;
            try {
                Data = binFileReader.Data(inputFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Columns = Data[0];

            Rows = Data[1];

            EnterX = Data[2];
            EnterY = Data[3];
            ExitX = Data[4];
            ExitY = Data[5];

            System.out.println("Columns: " + Columns);
            System.out.println("Rows: " + Rows);
            System.out.println("EnterX: " + EnterX);
            System.out.println("EnterY: " + EnterY);
            System.out.println("ExitX: " + ExitX);
            System.out.println("ExitY: " + ExitY);



            try {
                Lab = binFileReader.BinToInt(inputFile,Columns,Rows,EnterX,EnterY,ExitX,ExitY);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < Rows; i++) {
                for (int j = 0; j < Columns; j++) {
                    System.out.print(+Lab[j][i]);
                }
                System.out.println();
            }

            new GUI(Lab, Columns, Rows, EnterX, EnterY, ExitX, ExitY);


        }


   }
}
