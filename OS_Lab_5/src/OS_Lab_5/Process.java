package OS_Lab_5;

public class Process {
    private int pID;

    private int sumTime = 0;

    private int currentTime = 0;

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int time) {
        currentTime -= time;
    }

    private boolean blocked = false;

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }

    private boolean needIODevice = false;

    public void setNeedIODevice(boolean value) {
        needIODevice = value;
    }

    public boolean getNeedIODevice() {
        return needIODevice;
    }

    public Process(int pID, boolean needIODevice, int time) {
        this.pID = pID;
        this.needIODevice = needIODevice;
        sumTime = time;
        currentTime = time;
    }

    public int getPID() {
        return pID;
    }

    public void processDone() {
        System.out.println("Процесс " + pID + " выполнился успешно за время " + sumTime);
    }
}
