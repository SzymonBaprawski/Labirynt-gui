import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class txtFileReader {

    public static int Columns(File file) throws IOException {
        Scanner scanner = new Scanner(file);

        String line = scanner.nextLine();

        int Columns = line.length() - 1;//-2 for linux

        scanner.close();

        return Columns+1;
    }

    public static int Rows(File file) throws IOException {
        Scanner scanner = new Scanner(file);

        int Rows = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Rows++;
        }

        scanner.close();

        return Rows ;
    }

    public static int[][] TxtToInt(File file, int Columns, int Rows) throws IOException {
        int[][] Lab = new int[Columns][Rows];
        int y = 0;

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            char[] chars = line.toCharArray();

            for (int i = 0; i < Columns; i++) {
                if (chars[i] == 'X') {
                    Lab[y][i] = 1;
                } else if (chars[i] == ' ') {
                    Lab[y][i] = 0;
                } else if (chars[i] == 'P') {
                    Lab[y][i] = 2;
                } else if (chars[i] == 'K') {
                    Lab[y][i] = 3;
                }
            }
            y++;

        }
        scanner.close();

        return Lab;
    }

    public static int[] BeginningEnd(File file, int Columns, int Rows) throws IOException {
        int[] BeginningEnd = new int[4];
        int y = 0;

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char[] chars = line.toCharArray();

            for (int i = 0; i < Columns; i++) {
                if (chars[i] == 'P') {
                    BeginningEnd[0] = i;
                    BeginningEnd[1] = y;
                } else if (chars[i] == 'K') {
                    BeginningEnd[2] = i;
                    BeginningEnd[3] = y;
                }
            }
            y++;
        }
        scanner.close();

        return BeginningEnd;
    }

    /*public static void main(String[] args) {

        System.out.println("working");

        String FilePath = args[0];

        File file = new File(FilePath);


        int Columns = 0;
        try {
            Columns = Columns(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Columns: " + Columns);

        int Rows = 0;
        try {
            Rows = Rows(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Rows: " + Rows);

        int[][] Lab = null;

        try {
            Lab = TxtToInt(file,Columns,Rows);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                System.out.print(+Lab[i][j]);
            }
            System.out.println();
        }

        int[] BeginEnd= null;

        try {
            BeginEnd = BeginningEnd(file, Columns, Rows);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("BeginningEnd: " + BeginEnd[0]);
        System.out.println("BeginningEnd: " + BeginEnd[1]);
        System.out.println("BeginningEnd: " + BeginEnd[2]);
        System.out.println("BeginningEnd: " + BeginEnd[3]);

    }*/
}


