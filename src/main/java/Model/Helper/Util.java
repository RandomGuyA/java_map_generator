package Model.Helper;


import java.util.Random;

public final class Util {

    public static void printArray(int[][] array){

        for(int a=0; a<array.length; a++){
            System.out.println();
            for(int b=0; b<array[a].length; b++){

                System.out.print(array[a][b]+"\t");
            }
        }
        System.out.println();
    }
    public static void printArray(float[][] array){

        for(int a=0; a<array.length; a++){
            System.out.println();
            for(int b=0; b<array[a].length; b++){

                System.out.print(array[a][b]+"\t");
            }
        }
        System.out.println();
    }

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
