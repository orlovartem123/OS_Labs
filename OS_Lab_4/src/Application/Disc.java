package Application;

public class Disc {

    public static int[] memory;

    public static int drawSize;

    public static void setMemory(int size, int newDrawSize) {
        memory = new int[size];
        for (int i = 0; i < size; i++) {
            memory[i] = -1;
        }
        drawSize = newDrawSize;
    }

}
