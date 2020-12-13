package OS_Lab_1;

import java.util.HashMap;
import java.util.Iterator;

public class Core {
    private final int sytemCallsCount = 5;
    private HashMap<Integer, SystemCall> patterns;
    private Stack stack;

    public Core(Stack stack1) {
        patterns = new HashMap<>();
        patterns.put(0, new SystemCall(0, new Object[]{1, 1}));
        patterns.put(1, new SystemCall(1, new Object[]{"string", 1.5, "string"}));
        //patterns.put(1, new Object[]{"string", 1.5, "string"});
        patterns.put(2, new SystemCall(2, new Object[]{"string", 1}));
        patterns.put(3, new SystemCall(3, new Object[]{"string", "string"}));
        patterns.put(4, new SystemCall(4, new Object[]{1, 1.5, "string"}));
        stack = stack1;
    }

    public void performSystemCall(int id) {
        if (stack.getSize() == 0) {
            System.out.println("Стэк пуст");
            return;
        }
        int stackSize = stack.getSize();
        Object[] args = new Object[stackSize];
        for (int i = 0; i < stackSize; i++) {
            args[i] = stack.pop();
        }
        SystemCall currentCall = new SystemCall(id, args);
        if (!patterns.containsKey(id)) {
            System.out.println("Error: Системного вызова с таким id не существует");
            return;
        }
        //SystemCall currentCall=patterns.get(id);
        Object[] argsArr = currentCall.getArgs();
        if (argsArr.length != patterns.get(id).getArgs().length) {
            System.out.println("Error: Системного вызова с таким количеством аргументов не существует");
            return;
        }
        for (int i = 0; i < argsArr.length; i++) {
            if (argsArr[i].getClass() != patterns.get(id).getArgs()[i].getClass()) {
                System.out.println("Error: Системного вызова с таким набором аргументов не существует");
                return;
            }
        }
        System.out.println("Выполняется системный вызов с id " + id);
    }

    public void printSystemCallsList() {
        for (int i = 0; i < sytemCallsCount; i++) {
            StringBuilder str = new StringBuilder("");
            str.append("Id: ");
            str.append(i);
            str.append(" Args: ");
            for (int j = 0; j < patterns.get(i).getArgs().length; j++) {
                str.append(patterns.get(i).getArgs()[j].getClass().getSimpleName());
                str.append(",");
            }
            str.append("\n");
            System.out.println(str.toString());
        }
    }
}
