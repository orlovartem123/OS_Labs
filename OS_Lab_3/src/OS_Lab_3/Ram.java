package OS_Lab_3;

public class Ram {

    private int[] memory;

    public int[] getMemory() {
        return memory;
    }

    private boolean isFull = false;

    public boolean getFull() {
        return isFull;
    }

    public Ram(int size) {
        memory = new int[size];
        for (int i = 0; i < memory.length; i++) {
            memory[i] = -1;
        }
    }

    public void insertPage(int index, int pageId) {
        memory[index] = pageId;
    }

    public void checkIsFull() {
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] == -1) {
                isFull = false;
                return;
            }
        }
        isFull = true;
    }
}
