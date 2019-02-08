package rock.ast;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.data.internal.RockString;
import rock.exception.RockException;
import rock.util.IndentationPrinter;

import java.util.Iterator;

public class Block extends ASTList {

    public static final ASTListFactory FACTORY = (elements) -> new Block(elements);

    public Block(ASTree... children) {
        super(children);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        Rock result = null;
        for (Iterator<ASTree> it = iterator(); it.hasNext(); ) {
            result = it.next().eval(env, base);
        }
        return result;
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
    public void write(IndentationPrinter printer) {
        printer.print("{");
        printer.levelUp();
        Iterator<ASTree> itr = children.iterator();
        while (itr.hasNext()) {
            printer.ln();
            itr.next().write(printer);
            printer.print(";");
        }
        printer.leveldown();
        printer.println('}');
    }


}
