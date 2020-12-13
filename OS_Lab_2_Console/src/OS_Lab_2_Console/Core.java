package OS_Lab_2_Console;

import java.util.ArrayList;
import java.util.Random;

public class Core {
    ArrayList<Process> arrProcess = new ArrayList<>();
    Random rand = new Random();
    private final int time = 8;

    public void printProcessList() {
        for (int i = 0; i < arrProcess.size(); i++) {
            System.out.println("Process " + arrProcess.get(i).getpID());
            for (int j = 0; j < arrProcess.get(i).getArrThread().size(); j++) {
                System.out.println("   Thread " + arrProcess.get(i).getArrThread().get(j).getID() + " time: " + arrProcess.get(i).getArrThread().get(j).getNecessaryTime());
            }
        }
        System.out.println("\n\n\n");
    }

    public void createProcess() {
        for (int i = 0; i < 3 + rand.nextInt(10); i++) {
            arrProcess.add(new Process(i));
            arrProcess.get(i).createThreads();
        }
    }

    public void planProcess() {
        while (!arrProcess.isEmpty()) {
            for (int i = 0; i < arrProcess.size(); i++) {

                for (int j = 0; j < arrProcess.get(i).getArrThread().size(); j++) {
                    int threadId = arrProcess.get(i).getArrThread().get(j).getID();
                    int processId = arrProcess.get(i).getpID();
                    int threadTime = arrProcess.get(i).getArrThread().get(j).getNecessaryTime();
                    System.out.println("Поток " + threadId + " процесса " + processId + " начал выполнение");
                    if (threadTime - time > 0) {
                        arrProcess.get(i).getArrThread().get(j).changeNecessaryTime(time);
                        System.out.println("Поток " + threadId + " процесса " + processId + " прерван, требуемое время: "+threadTime +  " выделяемое время: "+time +" оставшееся время "+ (threadTime - time));
                    }
                    if (threadTime - time <= 0) {
                        arrProcess.get(i).getArrThread().get(j).threadDone();
                        arrProcess.get(i).getArrThread().remove(j);
                        j--;
                    }
                }
                if (arrProcess.get(i).getArrThread().isEmpty()) {
                    arrProcess.get(i).processDone();
                    arrProcess.remove(i);
                    i--;
                }
            }
        }
    }

    public void startProgram() {

        createProcess();
        printProcessList();
        planProcess();
    }
}
//указываем начало выполнение процесса, заканчиваем выполнение, только когда кончатся все объекты массива процессов