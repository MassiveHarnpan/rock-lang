package rock.ast;

import rock.Environment;
import rock.exception.RockException;

import java.util.Iterator;

public class Block extends ASTList {

    public Block(ASTree... children) {
        super(children);
        liftSingleElement(true);
    }

    @Override
    public Object eval(Environment env) throws RockException {
        Object result = null;
        for (Iterator<ASTree> it = iterator(); it.hasNext(); ) {
            result = it.next().eval(env);
        }
        return result;
    }

    @Override
    public ASTree simplify() {
        ASTree tree = super.simplify();
        if (tree.isLeaf()) {
            return tree;
        }
        ASTList asl = (ASTList) tree;
        Iterator<ASTree> itr = asl.iterator();
        while (itr.hasNext()) {
            ASTree ast = itr.next();
            if (!ast.isLeaf() && ast.childCount() == 0) {
                itr.remove();
            }
        }
        if (asl.childCount() == 1) {
            return asl.child(0);
        }
        return asl;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append('{').append("\n");
        Iterator<ASTree> itr = children.iterator();
        while (itr.hasNext()) {
            sb.append(itr.next().toString());
            sb.append('\n');
        }
        return sb.append('}').toString();
    }
}
