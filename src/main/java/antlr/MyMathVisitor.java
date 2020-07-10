package antlr;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyMathVisitor extends MathBaseVisitor<Integer> {
    private Stack<Integer> numStack = new Stack<>();
    private Queue<String> symbolQueue = new ConcurrentLinkedQueue<>();
    @Override
    public Integer visitTerminal(TerminalNode terminalNode) {
        String text = terminalNode.getText();
        if (AntlrUtil.isCalSymbol(text)) {
            symbolQueue.add(text);
        } else {
            AntlrUtil.cal(text, numStack, symbolQueue);
        }
        return numStack.peek();
    }
}
