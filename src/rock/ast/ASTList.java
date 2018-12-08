package rock.ast;

import rock.Environment;
import rock.exception.RockException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {

    protected List<ASTree> children = new ArrayList<>();

    protected Iterator<ASTree> iterator() {
        return children.iterator();
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public ASTree child(int i) {
        return children.get(i);
    }

    @Override
    public int childCount() {
        return children.size();
    }

    @Override
    public Object eval(Environment env) throws RockException {
        Object result = null;
        for (ASTree ast : children) {
            result = ast.eval(env);
        }
        return result;
    }

    @Override
    public Object eval(Environment env, Object base) throws RockException {
        return eval(env);
    }

    @Override
    public ASTree simplify() {
        for (int i = 0; i < childCount(); i++) {
            children.set(i, children.get(i).simplify());
        }
        if (lift && childCount() == 1) {
            return child(0);
        }
        return this;
    }

    protected boolean lift = false;

    protected boolean liftSingleElement() {
        return lift;
    }

    protected boolean liftSingleElement(boolean lift) {
        return this.lift = lift;
    }







    public ASTList(ASTree... children) {
        this.children.addAll(Arrays.asList(children));
    }

    public ASTList() {

    }

    public ASTList append(ASTree... children) {
        this.children.addAll(Arrays.asList(children));
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append('(');
        Iterator<ASTree> itr = children.iterator();
        while (itr.hasNext()) {
            sb.append(itr.next().toString());
            if (itr.hasNext()) {
                sb.append(' ');
            }
        }
        return sb.append(')').toString();
    }
}
