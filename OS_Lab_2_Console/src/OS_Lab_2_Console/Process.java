package OS_Lab_2_Console;

import java.util.ArrayList;
import java.util.Random;

public class Process {
    private int pID;
    private int sumTime = 0;

    private ArrayList<Thread> arrThread = new ArrayList<>();

    private Random rand = new Random();

    public Process(int pID) {
        this.pID = pID;
    }

    public int getpID() {
        return pID;
    }

    public ArrayList<Thread> getArrThread(){
        return arrThread;
    }

    public void createThreads() {
        for (int i = 0; i < 1 + rand.nextInt(10); i++) {
            int nesTime = 1 + rand.nextInt(10);
            arrThread.add(new Thread(i, nesTime));
            sumTime+=nesTime;
        }
    }

    public void processDone(){
        System.out.println("Процесс "+ pID + " выполнился успешно за время "+ sumTime);
    }
}
//ядро сразу планирует потоки, а не процессы