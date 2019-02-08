package rock.ast;

import rock.util.IndentationPrinter;
import rock.util.Logger;

import java.util.Arrays;
import java.util.Iterator;

public class Program extends ASTList {

    public static final ASTListFactory FACTORY = (elements) -> new Program(elements);

    public Program(ASTree... children) {
        super(children);
    }

    @Override
    public ASTree simplify() {
        for (int i = 0; i < childCount(); i++) {
            children.set(i, children.get(i).simplify());
        }
        Iterator<ASTree> itr = iterator();
        while (itr.hasNext()) {
            ASTree ast = itr.next();
            if (!ast.isLeaf() && ast.childCount() == 0) {
                itr.remove();
            }
        }
        return this;
    }

    @Override
    public String toString() {
        Logger.log("Program.write");
        IndentationPrinter printer = new IndentationPrinter();
        write(printer);
        return printer.toString();
    }

    @Override
    public void write(IndentationPrinter printer) {
        Iterator<ASTree> itr = children.iterator();
        if (itr.hasNext()) {
            printer.ln();
            itr.next().write(printer);
            printer.print(";");
        }
        while (itr.hasNext()) {
            printer.ln();
            itr.next().write(printer);
            printer.print(";");
        }
    }
}
