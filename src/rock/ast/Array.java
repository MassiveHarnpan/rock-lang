package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockArray;
import rock.exception.RockException;

import java.util.Iterator;

public class Array extends ASTList {
    public Array(ASTree... children) {
        super(children);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        Iterator<ASTree> itr = iterator();
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append("]").toString();
    }
}
