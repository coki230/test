package antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyMathListener extends MathBaseListener {
    private Stack<Integer> numStack = new Stack<>();
    private Queue<String> symbolQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void enterExpress(MathParser.ExpressContext ctx) {
        super.enterExpress(ctx);
    }

    @Override
    public void exitExpress(MathParser.ExpressContext ctx) {
        super.exitExpress(ctx);
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        super.enterEveryRule(ctx);
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        super.exitEveryRule(ctx);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        super.visitTerminal(node);
        String text = node.getText();
        if (AntlrUtil.isCalSymbol(text)) {
            symbolQueue.add(text);
        } else {
            AntlrUtil.cal(text, numStack, symbolQueue);
        }
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
    }

    public int result() {
        return numStack.pop();
    }
}
