import java.io.IOException;
import java.io.*;
import java.nio.ByteBuffer;


public class binFileReader  {

    public static short[] Data(String file) throws IOException {
        short[] Data = new short[7];

        byte[] tmp = new byte[2];

        InputStream InputStream = new FileInputStream(file);

        InputStream.skip(5);

        for(int i = 0; i < 6; i++) {
            InputStream.readNBytes(tmp, 0, 2);

            byte tmp2 = tmp[0];
            tmp[0] = tmp[1];
            tmp[1] = tmp2;

            ByteBuffer buffer = ByteBuffer.allocate(2);
            buffer.put(tmp);
            buffer.rewind();
            Data[i] = buffer.getShort();
        }

        InputStream.close();

        return Data;
    }

    public static int[][] BinToInt(String file, int Columns, int Rows, int EntryX, int EntryY, int ExitX, int ExitY ) throws IOException  {
        int[][] Lab = new int[Columns][Rows];

        byte[] counterTmp = new byte[4];
        byte[] goodOrder = new byte[4];

        InputStream InputStream = new FileInputStream(file);

        InputStream.skip(29);

        InputStream.readNBytes(counterTmp, 0, 4);
        goodOrder[3]= counterTmp[0];
        goodOrder[2]= counterTmp[1];
        goodOrder[1]= counterTmp[2];
        goodOrder[0]= counterTmp[3];

        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(goodOrder);
        buffer.rewind();
        int Counter = buffer.getInt();

        System.out.println("Counter " +Counter);

        InputStream.skip(5);

        int Wall = InputStream.read();
        int Path = InputStream.read();
        int index2= 0;
        int index1= 0;

        for(int i = 0; i < Counter; i++) {
            InputStream.skip(1);
            int tmp = InputStream.read();
            int ile = InputStream.read();

            if (tmp == Wall) {
                for (int x = 0; x < ile + 1; x++) {
                    Lab[index1][index2] = 1;
                    index1++;
                    if(index1 == Columns)
                    {
                        index2 ++;
                        index1= 0;
                    }
                }
            } else {
                for (int x = 0; x < ile + 1; x++) {
                    Lab[index1][index2] = 0;
                    index1++;
                    if(index1 == Columns)
                    {
                        index2 ++;
                        index1= 0;
                    }
                }
            }
        }

        Lab[EntryX-1][EntryY-1]= 2;
        Lab[ExitX-1][ExitY-1]= 3;

        InputStream.close();

        return Lab;
    }


    /*public static void main(String[] args){
        String inputFile = args[0];

        short[] Data = null;
        try {
            Data = Data(inputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i =0 ; i < 6;i++){
            System.out.print(" " +Data[i]);
        }
    }*/


    /*public static void main(String[] args) {
        String inputFile = args[0];

        int[][] Lab = null;

        short[] Data = null;
        try {
            Data = Data(inputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int Columns = Data[0];

        int Rows = Data[1];

        int EnterX = Data[2];
        int EnterY = Data[3];
        int ExitX = Data[4];
        int ExitY = Data[5];

        System.out.println("Columns: " + Columns);
        System.out.println("Rows: " + Rows);
        System.out.println("EnterX: " + EnterX);
        System.out.println("EnterY: " + EnterY);
        System.out.println("ExitX: " + ExitX);
        System.out.println("ExitY: " + ExitY);



        try {
            Lab = BinToInt(inputFile,Columns,Rows,EnterX,EnterY,ExitX,ExitY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                System.out.print(+Lab[j][i]);
            }
            System.out.println();
        }
    }*/
}