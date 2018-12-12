package rock.ast;

import rock.data.Environment;
import rock.data.NestedEnvironment;
import rock.data.Rock;
import rock.exception.RockException;

import java.util.Iterator;

public class Block extends ASTList {

    public Block(ASTree... children) {
        super(children);
    }

    @Override
    public Rock eval(Environment env) throws RockException {
        Rock result = null;
        for (Iterator<ASTree> it = iterator(); it.hasNext(); ) {
            result = it.next().eval(env);
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
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append('{').append("\n");
        Iterator<ASTree> itr = children.iterator();
        while (itr.hasNext()) {
            sb.append(itr.next().toString(1, "    "));
            sb.append('\n');
        }
        return sb.append('}').toString();
    }
}
