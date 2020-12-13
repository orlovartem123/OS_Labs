package OS_Lab_1;

import java.util.LinkedList;

public class Stack {
    private LinkedList<Object> list;
    private int size = 0;

    public Stack() {
        list = new LinkedList<>();
    }

    public void push(Object arg) {
        list.addFirst(arg);
        size++;
    }

    public Object pop() {
        Object result = list.getFirst();
        list.removeFirst();
        size--;
        return result;
    }

    public int getSize() {
        return size;
    }
}
