package rock.ast;

import rock.data.*;
import rock.exception.RockException;
import rock.exception.UnsupportedOperationException;
import rock.token.Token;
import rock.util.IndentationPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {

    public static final ASTListFactory FACTORY = (elements) -> new ASTList(elements);

    protected List<ASTree> children = new ArrayList<>();

    protected Iterator<ASTree> iterator() {
        return children.iterator();
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public Token token() {
        throw null;
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
    public Rock eval(Environment env, Rock base) throws RockException {
        Rock result = null;
        for (ASTree ast : children) {
            result = ast.eval(env, base);
        }
        return result;
    }

    @Override
    public Rock set(Environment env, Rock base, Rock value) throws RockException {
        throw new UnsupportedOperationException("set", this.toString());
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

    @Override
    public void write(IndentationPrinter printer) {
        Iterator<ASTree> itr = children.iterator();
        while (itr.hasNext()) {
            itr.next().write(printer);
            if (itr.hasNext()) {
                printer.print(" ");
            }
        }
    }
}
