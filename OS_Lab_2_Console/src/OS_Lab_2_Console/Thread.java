package OS_Lab_2_Console;

public class Thread {
    private int tID;
    private int necessaryTime;

    public Thread (int tID,int necessaryTime){
        this.tID=tID;
        this.necessaryTime=necessaryTime;
    }

    public int getID() {
        return tID;
    }

    public int getNecessaryTime() {
        return necessaryTime;
    }

    public void changeNecessaryTime(int time){
        necessaryTime-=time;
    }

    public void threadDone() {
        System.out.println("поток "+ tID + " выполнился успешно за время "+ necessaryTime);
    }
}
