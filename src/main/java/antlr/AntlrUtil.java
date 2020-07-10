package antlr;

import java.util.Queue;
import java.util.Stack;

public class AntlrUtil {

    public static boolean isCalSymbol(String text){
        return text.equals("p") || text.equals("s") || text.equals("m") || text.equals("d");
    }

    public static void cal(String text, Stack<Integer> numStack, Queue<String> symbolQueue) {
        if (text != null && !text.trim().equals("")) {
            if (symbolQueue.isEmpty()) {
                numStack.push(Integer.parseInt(text));
            } else {
                while (!symbolQueue.isEmpty()) {
                    String symbol = symbolQueue.poll();
                    switch (symbol) {
                        case "p":
                            numStack.push(numStack.pop() + Integer.parseInt(text));
                            break;
                        case "s":
                            numStack.push(numStack.pop() - Integer.parseInt(text));
                            break;
                        case "m":
                            numStack.push(numStack.pop() * Integer.parseInt(text));
                            break;
                        case "d":
                            numStack.push(numStack.pop() / Integer.parseInt(text));
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + symbol);
                    }
                }
            }

        }
    }
}
