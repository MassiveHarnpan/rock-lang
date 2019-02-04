package rock.ast;

import rock.util.Logger;

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
        Logger.log("Program.toString");
        StringBuffer sb = new StringBuffer();
        Iterator<ASTree> itr = children.iterator();
        while (itr.hasNext()) {
            sb.append(itr.next().toString(0, "    "));
            if (itr.hasNext()) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }
}
