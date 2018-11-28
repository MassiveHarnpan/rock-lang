package rock.ast;

import rock.Environment;
import rock.RockException;

import java.util.Iterator;

public class Block extends ASTList {

    public Block(ASTree... children) {
        super(children);
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
        if (childCount() == 1) {
            return asl.child(0);
        }
        return asl;
    }
}
