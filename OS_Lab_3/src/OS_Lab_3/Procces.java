package OS_Lab_3;

import java.awt.List;
import java.util.LinkedList;
import java.util.Random;

public class Procces {

    private LinkedList<Page> pageList = new LinkedList<>();

    private int requestsNum;

    private int ramSize;

    public Procces(int pageCount, int requestsNum, int ramSize) {
        for (int i = 0; i < pageCount; i++) {
            pageList.add(new Page());
        }
        this.requestsNum = requestsNum;
        this.ramSize = ramSize;
    }

    public void work() {
        Ram ram = new Ram(ramSize);
        MemoryManager memManager = new MemoryManager(pageList, ram);
        Random rnd = new Random();
        for (int i = 0; i < requestsNum; i++) {
            int id = rnd.nextInt(pageList.size());
            System.out.println("Процесс обращается к странице с id: " + id);
            if(!memManager.work(id)){
                System.out.print("\n*****На компьютере недостаточно памяти*****\n");
                return;
            }
        }
    }
}
