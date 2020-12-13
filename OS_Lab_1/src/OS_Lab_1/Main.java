package OS_Lab_1;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Stack stack = new Stack();
        Core core = new Core(stack);
        core.printSystemCallsList();
        stack.push("0");
        stack.push(2.7);
        stack.push(1);
        core.performSystemCall(4);
        core.performSystemCall(0);

    }
}
