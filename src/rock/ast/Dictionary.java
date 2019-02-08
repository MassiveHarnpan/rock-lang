package rock.ast;

import rock.data.Environment;
import rock.data.Rock;
import rock.data.internal.RockArray;
import rock.data.internal.RockDictionary;
import rock.exception.RockException;

import java.util.Iterator;

public class Dictionary extends ASTList {
    public Dictionary(ASTree... children) {
        super(children);
    }

    @Override
    public Rock eval(Environment env, Rock base) throws RockException {
        RockDictionary dict = new RockDictionary();

        for (int i = 0; i < childCount(); i += 2) {
            String key = ((Name) child(i)).name();
            Rock value = child(i + 1).eval(env, base);
            dict.set(key, value);
        }

        return dict;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        Iterator<ASTree> itr = iterator();
        while (itr.hasNext()) {
            sb.append(itr.next()).append(":");
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append("}").toString();
    }
}
