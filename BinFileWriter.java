import java.io.*;
import static java.math.BigInteger.valueOf;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class BinFileWriter extends Follower  {

    public static void WriteLab(String fileName, int[][] Lab, int Columns, int Rows, int EnterX, int EnterY, int ExitX, int ExitY) throws IOException {
        File file = new File(fileName);

        FileOutputStream fileOut = new FileOutputStream(file);
        RandomAccessFile raf = new RandomAccessFile(file, "rws");

        int FILEID = 0x43425252;
        byte[] fileid = valueOf(FILEID).toByteArray();
        fileOut.write(fileid, 0, 4);
        byte ESC = 27;
        fileOut.write(ESC);
        //hardcode

        byte[] byteArray = new byte[2];

        byteArray[0] = (byte) (Columns & 0xFF);
        byteArray[1] = (byte) ((Columns >> 8) & 0xFF);
        fileOut.write(byteArray, 0, 2);

        byteArray[0] = (byte) (Rows & 0xFF);
        byteArray[1] = (byte) ((Rows >> 8) & 0xFF);
        fileOut.write(byteArray, 0, 2);

        byteArray[0] = (byte) (EnterX+1 & 0xFF);
        byteArray[1] = (byte) ((EnterX+1 >> 8) & 0xFF);
        fileOut.write(byteArray, 0, 2);

        byteArray[0] = (byte) (EnterY+1 & 0xFF);
        byteArray[1] = (byte) ((EnterY+1 >> 8) & 0xFF);
        fileOut.write(byteArray, 0, 2);

        byteArray[0] = (byte) (ExitX+1 & 0xFF);
        byteArray[1] = (byte) ((ExitX+1 >> 8) & 0xFF);
        fileOut.write(byteArray, 0, 2);

        byteArray[0] = (byte) (ExitY+1 & 0xFF);
        byteArray[1] = (byte) ((ExitY+1 >> 8) & 0xFF);
        fileOut.write(byteArray, 0, 2);

        int REZ = 0xff000000;
        byte[] reze = valueOf(REZ).toByteArray();
        for (int i = 0; i < 5; i++) {
            fileOut.write(reze, 0, 4);
        }


        byte Separator = 35;
        fileOut.write(Separator);
        byte Wall = 88;
        fileOut.write(Wall);
        byte Path = 32;
        fileOut.write(Path);

        int counter = -1;
        int words = 0;
        int sign = Lab[0][0];

        Lab[EnterX+1][EnterY+1] = 0;
        Lab[ExitX][ExitY] = 0;

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                if (Lab[j][i] == sign) {
                    if (counter == 255) {
                        if (sign == 0) {
                            fileOut.write(Separator);
                            fileOut.write(Path);
                            fileOut.write(counter);
                            words++;
                        } else {
                            fileOut.write(Separator);
                            fileOut.write(Wall);
                            fileOut.write(counter);
                            words++;
                        }
                        counter = -1;
                    }
                    counter++;
                } else if (Lab[j][i] != sign) {
                    if (sign == 0) {
                        fileOut.write(Separator);
                        fileOut.write(Path);
                        fileOut.write(counter);
                        words++;
                    } else {
                        fileOut.write(Separator);
                        fileOut.write(Wall);
                        fileOut.write(counter);
                        words++;
                    }
                    sign = Lab[j][i];
                    counter = 0;
                }
            }
        }

        fileOut.write(Separator);
        fileOut.write(Wall);
        fileOut.write(counter);
        words++;
        words = words;

        raf.seek(0);
        raf.skipBytes(29);
        byte[] bytecount = new byte[4];
        bytecount[3] = (byte)(words >>> 24);
        bytecount[2] = (byte)(words >>> 16);
        bytecount[1] = (byte)(words >>> 8);
        bytecount[0] = (byte)words;

        raf.write(bytecount[0]);
        raf.write(bytecount[1]);
        raf.write(bytecount[2]);
        raf.write(bytecount[3]);



        Lab[EnterX][EnterY] = 2;
        Lab[ExitX][ExitY] = 3;

        fileOut.close();
        raf.close();
    }

    public static void WriteSolve(String fileName, ArrayList<Integer> coords, int EnterX, int EnterY) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rws");

        long size = raf.length();
        int siz = (int) size;
        raf.seek(33);
        raf.writeInt(siz);

        raf.seek(size);
        raf.writeInt(0x43425252); //hardcode
        raf.writeByte(0x00);

        int x = EnterX;
        int y = EnterY;
        byte Direction = 69;
        int steps = -1;
        int count = -1;

        if (x < coords.get(0)) {
            Direction = 69; //E
        } else if (y < coords.get(1)) {
            Direction = 83; //S
        } else if (x > coords.get(0)) {
            Direction = 87; //W
        } else {
            Direction = 78; //N
        }


        for (int i = 0; i < coords.size(); i++) {
            byte tmp;
            if (x < coords.get(i)) {
                tmp = 69; //E
            } else if (y < coords.get(i + 1)) {
                tmp = 83; //S
            } else if (x > coords.get(i)) {
                tmp = 87; //W
            } else {
                tmp = 78; //N
            }

            if (Direction == tmp) {
                steps++;
                if (steps == 255) {
                    raf.writeByte(steps);
                    raf.writeByte(Direction);
                    steps = -1;
                    count++;
                }
            } else {
                raf.writeByte(Direction);
                raf.writeByte(steps);
                Direction = tmp;
                steps = 0;
                count++;
            }

            x = coords.get(i);
            y = coords.get(i + 1);

            i++;
        }
        raf.writeByte(Direction);
        raf.writeByte(steps);
        count++;

        raf.seek(size + 4);
        raf.writeByte(count);

        raf.close();
    }


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


        try {
            WriteLab("proba.bin", Lab, Columns, Rows, EnterX-1, EnterY-1, ExitX-1, ExitY-1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("tu");

        try {
            Data = Data("proba.bin");
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

        System.out.println("tu");

        try {
            Lab = BinToInt("proba.bin",Columns,Rows,EnterX,EnterY,ExitX,ExitY);
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
