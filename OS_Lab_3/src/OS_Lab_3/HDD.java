package OS_Lab_3;

public class HDD {

    private int[] memory;

    public int[] getMemory() {
        return memory;
    }

    public HDD(int hddSize) {
        memory = new int[hddSize];
        for(int i=0;i<hddSize;i++){
            memory[i]=-1;
        }
    }

    public boolean insertPage(int index) {
        for (int i = 0; i < memory.length; i++) {
            if (memory[i] == -1) {
                memory[i] = index;
                return true;
            }
        }
        return false;
    }

    public boolean freeSpace(int index){
        for(int i=0;i<memory.length;i++){
            if(memory[i]==index){
                memory[i]=-1;
                return true;
            }
        }
        return false;
    }
}
