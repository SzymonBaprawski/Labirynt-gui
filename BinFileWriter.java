import java.io.*;
import static java.math.BigInteger.valueOf;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BinFileWriter extends Follower{

    public static void WriteLab (String fileName, int[][] Lab, int Columns, int Rows, int EnterX, int EnterY, int ExitX, int ExitY) throws IOException {
        File file = new File(fileName);

        FileOutputStream fileOut = new FileOutputStream(file);
        RandomAccessFile raf = new RandomAccessFile(file, "rws");

        int FILEID = 0x43425252;
        byte[] fileid = valueOf(FILEID).toByteArray();
        fileOut.write(fileid, 0,4);
        byte ESC = 27;
        fileOut.write(ESC);
        //hardcode

        byte[] byteArray = new byte[2];

        byteArray[0] = (byte) (Columns & 0xFF);
        byteArray[1] = (byte) ((Columns >> 8) & 0xFF);
        fileOut.write(byteArray,0 ,2);

        byteArray[0] = (byte) (Rows & 0xFF);
        byteArray[1] = (byte) ((Rows >> 8) & 0xFF);
        fileOut.write(byteArray, 0,2);

        byteArray[0] = (byte) (EnterX & 0xFF);
        byteArray[1] = (byte) ((EnterX >> 8) & 0xFF);
        fileOut.write(byteArray, 0,2);

        byteArray[0] = (byte) (EnterY & 0xFF);
        byteArray[1] = (byte) ((EnterY >> 8) & 0xFF);
        fileOut.write(byteArray, 0,2);

        byteArray[0] = (byte) (ExitX & 0xFF);
        byteArray[1] = (byte) ((ExitX >> 8) & 0xFF);
        fileOut.write(byteArray, 0,2);

        byteArray[0] = (byte) (ExitY & 0xFF);
        byteArray[1] = (byte) ((ExitY >> 8) & 0xFF);
        fileOut.write(byteArray, 0,2);

        int REZ= 0xff000000;
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
        int words = 0 ;
        int sign = Lab[0][0];

        Lab[EnterX][EnterY] = 0;
        Lab[ExitX][ExitY] = 0;

        for (int i = 0; i < Rows; i++) {
            for (int j = 0; j < Columns; j++) {
                if (Lab[j][i] == sign) {
                    if(counter == 255){
                        if(sign == 0){
                            fileOut.write(Separator);
                            fileOut.write(Path);
                            fileOut.write(counter);
                            words++;
                        }else{
                            fileOut.write(Separator);
                            fileOut.write(Wall);
                            fileOut.write(counter);
                            words++;
                        }
                        counter = 0;
                    }
                    counter++;
                }else if(Lab[j][i] != sign){
                    if(sign == 0){
                        fileOut.write(Separator);
                        fileOut.write(Path);
                        fileOut.write(counter);
                        words++;
                    }else{
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

        raf.seek(0);
        raf.skipBytes(29);
        raf.writeInt(words);

        Lab[EnterX][EnterY] = 2;
        Lab[ExitX][ExitY] = 3;

        fileOut.close();
        raf.close();
    }

    public static void WriteSolve (String fileName, ArrayList<Integer> coords, int EnterX, int EnterY) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(fileName, "rws");

        long size = raf.length();
        int siz = (int)size;
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

        if(x<coords.get(0)){
            Direction = 69; //E
        }else if(y <coords.get(1)){
            Direction = 83; //S
        }else if(x > coords.get(0)) {
            Direction = 87; //W
        }else{
            Direction = 78; //N
        }


        for(int i = 0; i < coords.size();i++){
            byte tmp;
            if(x<coords.get(i)){
                tmp = 69; //E
            }else if(y <coords.get(i+1)){
                tmp = 83; //S
            }else if(x > coords.get(i))
            {
                tmp = 87; //W
            }else{
                tmp = 78; //N
            }

            if(Direction == tmp){
                steps++;
                if(steps == 255){
                    raf.writeByte(steps);
                    raf.writeByte(Direction);
                    steps = -1;
                    count++;
                }
            }else{
                raf.writeByte(Direction);
                raf.writeByte(steps);
                Direction = tmp;
                steps = 0;
                count++;
            }

            x = coords.get(i);
            y = coords.get(i+1);

            i++;
        }
        raf.writeByte(Direction);
        raf.writeByte(steps);
        count++;

        raf.seek(size+4);
        raf.writeByte(count);

        raf.close();
    }


    /*public static void main (String [] args) throws IOException {
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

        Zalanie(Lab, Columns, Rows, EntryX, EntryY, ExitX, ExitY);

        ArrayList<Integer> coords = Follow(Lab, Columns, Rows, EntryX, EntryY, ExitX, ExitY);

        WriteLab("proba.bin", Lab, Columns, Rows, EntryX, EntryY, ExitX, ExitY);
        WriteSolve("proba.bin", coords, EntryX,EntryY);
    }*/
}
