package antlr;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class AntlrMain {
    public static void main(String[] args) {
        String hello = "1 p 2 m 3 s 1";
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(hello);
        MathLexer mathLexer = new MathLexer(antlrInputStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(mathLexer);

        MathParser mathParser = new MathParser(commonTokenStream);

        // listener
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        MyMathListener myMathListener = new MyMathListener();
        parseTreeWalker.walk(myMathListener, mathParser.express());
        System.out.println(myMathListener.result());

        // visitor
        MyMathVisitor myMathVisitor = new MyMathVisitor();
        Integer result = myMathVisitor.visit(mathParser.express());
        System.out.println(result);
    }


}
