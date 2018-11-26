package rock.ast;

import rock.Enviroument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {

    private List<ASTree> children = new ArrayList<>();

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
    public Object eval(Enviroument env) {
        Object result = null;
        for (ASTree ast : children) {
            result = ast.eval(env);
        }
        return result;
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
