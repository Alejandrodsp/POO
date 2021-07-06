package jogo;

import java.util.Random;

public abstract class Dado {

    private static Random random = new Random();

    public static int dado(int max){
        return Dado.random.nextInt(max);
    }

    public static void alimenta(long semente) {
        random.setSeed(semente);
    }

}
