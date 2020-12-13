package OS_Lab_5;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;

public class Core {

    private int timeWithBlocking = 0;

    private int timeWithOutBlocking = 0;

    private int ioWorkTime = 6;

    private Deque<Process> blocked = new ArrayDeque<>();

    private ArrayList<Process> arrProcess = new ArrayList<>();

    private ArrayList<Process> arrProcessBlocking = new ArrayList<>();

    private Random rand = new Random();

    private final int time = 8;

    public void printProcessList() {
        for (int i = 0; i < arrProcess.size(); i++) {
            System.out.println("Process | " + arrProcess.get(i).getPID() + " | with time | " + arrProcess.get(i).getCurrentTime() + " | need IO Device: | " + arrProcess.get(i).getNeedIODevice() + " |");
        }
        System.out.println("\n\n\n");
    }

    public void createProcess() {
        int size = 3 + rand.nextInt(15);
        for (int i = 0; i < size; i++) {
            if (rand.nextInt(10) < 6) {
                arrProcess.add(new Process(i, true, rand.nextInt(time * 2) + 1));
                timeWithOutBlocking += ioWorkTime;
            } else {
                arrProcess.add(new Process(i, false, rand.nextInt(time * 2) + 1));
            }
            timeWithOutBlocking += arrProcess.get(i).getCurrentTime();
            arrProcessBlocking.add(new Process(i, arrProcess.get(i).getNeedIODevice(), arrProcess.get(i).getCurrentTime()));
        }
    }

    private boolean unBlock() {
        if (blocked.isEmpty()) {
            return false;
        }
        var process = blocked.pop();
        process.setBlocked(false);
        process.setNeedIODevice(false);
        System.out.println("Процесс " + process.getPID() + " переведен в состояние готовности");
        if (blocked.isEmpty()) {
            return false;
        }
        System.out.println("Началась работа с устройством ввода/вывода");
        return true;
    }

    private void planProcessWithBlocking() {
        int eTime = 0;
        boolean isIOWorking = false;
        int timeIOWorkLeft = ioWorkTime;
        int timeToProcess = 0;
        while (!arrProcessBlocking.isEmpty()) {
            timeToProcess = time;
            if (blocked.size() == arrProcessBlocking.size()) {
                if (isIOWorking) {
                    if ((timeIOWorkLeft - time) > 0) {
                        timeIOWorkLeft -= time;
                        eTime += time;
                    } else {
                        timeToProcess = (time - timeIOWorkLeft);
                        System.out.println("Завершение работы с устройством ввода/вывода");
                        isIOWorking = unBlock();
                        timeIOWorkLeft = ioWorkTime;
                    }
                }
            }
            for (int i = 0; i < arrProcessBlocking.size(); i++) {
                if (arrProcessBlocking.get(i).isBlocked()) {
                    continue;
                }
                System.out.println("Процесс " + arrProcessBlocking.get(i).getPID() + " начал выполнение");
                if (arrProcessBlocking.get(i).getNeedIODevice()) {
                    arrProcessBlocking.get(i).setBlocked(true);
                    blocked.addLast(arrProcessBlocking.get(i));
                    System.out.println("Процесс " + arrProcessBlocking.get(i).getPID() + " заблокирован");
                    if (!isIOWorking) {
                        System.out.println("Началась работа с устройством ввода/вывода");
                        isIOWorking = true;
                        timeIOWorkLeft = ioWorkTime;
                    }
                    continue;
                }
                int timeSpend = 0;
                if ((timeToProcess - arrProcessBlocking.get(i).getCurrentTime()) < 0) {
                    System.out.println("Один из потоков процесса " + arrProcessBlocking.get(i).getPID()
                            + " прерван,требуемое время: " + arrProcessBlocking.get(i).getCurrentTime()
                            + " выделяемое время " + timeToProcess + " оставшееся время " + (arrProcessBlocking.get(i).getCurrentTime() - timeToProcess));
                    arrProcessBlocking.get(i).setCurrentTime(timeToProcess);
                    eTime += timeToProcess;
                    timeSpend = timeToProcess;
                } else {
                    timeSpend = arrProcessBlocking.get(i).getCurrentTime();
                    eTime += arrProcessBlocking.get(i).getCurrentTime();
                    arrProcessBlocking.get(i).processDone();
                    arrProcessBlocking.remove(i);
                    i--;
                }
                if (isIOWorking) {
                    if ((timeIOWorkLeft - timeSpend) > 0) {
                        timeIOWorkLeft -= timeSpend;
                    } else {
                        System.out.println("Завершение работы с устройством ввода/вывода");
                        isIOWorking = unBlock();
                        timeIOWorkLeft = ioWorkTime;
                    }
                }
            }
        }
        timeWithBlocking = eTime;
    }

    private void planProcessWithoutBlocking() {
        while (!arrProcess.isEmpty()) {
            for (int i = 0; i < arrProcess.size(); i++) {
                int timeLeft = time;
                System.out.println("Процесс " + arrProcess.get(i).getPID() + " начал выполнение");
                if (arrProcess.get(i).getNeedIODevice()) {
                    System.out.println("Остановка работы планировщика процессов, идет работа с устройством ввода/вывода");
                    if ((ioWorkTime - time) > 0) {
                        timeLeft = time * 2 - ioWorkTime;
                    } else {
                        timeLeft -= ioWorkTime;
                    }
                    System.out.println("Завершение работы с устройством ввода/вывода");
                    System.out.println("Продолжение выполнение процесса " + arrProcess.get(i).getPID());
                }
                if ((timeLeft - arrProcess.get(i).getCurrentTime()) < 0) {
                    System.out.println("Один из потоков процесса " + arrProcess.get(i).getPID()
                            + " прерван,требуемое время: " + arrProcess.get(i).getCurrentTime()
                            + " выделяемое время " + timeLeft + " оставшееся время " + (arrProcess.get(i).getCurrentTime() - timeLeft));
                    arrProcess.get(i).setCurrentTime(timeLeft);
                } else {
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
        System.out.println("*********************C блокировками************************");
        planProcessWithBlocking();
        System.out.println("\n\n\n");
        System.out.println("*********************Без блокировок************************");
        planProcessWithoutBlocking();
        System.out.println("\n\n*****C блокировками затрачено времени: " + timeWithBlocking);
        System.out.println("*****Без блокировок затрачено времени: " + timeWithOutBlocking);
    }
}