package rock.ast;

import java.util.Iterator;

public class Program extends ASTList {
    public Program(ASTree... children) {
        super(children);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator<ASTree> itr = children.iterator();
        while (itr.hasNext()) {
            sb.append(itr.next().toString());
            sb.append('\n');
        }
        return sb.toString();
    }
}
